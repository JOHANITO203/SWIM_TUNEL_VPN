package com.branded.vpn.vpn.service

import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import android.util.Log
import com.branded.vpn.core.domain.model.VpnStatus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class VpnTunnelService : VpnService() {

    companion object {
        private const val TAG = "VpnTunnelService"
        private val _vpnStatus = MutableStateFlow<VpnStatus>(VpnStatus.Disconnected)
        val vpnStatus: StateFlow<VpnStatus> = _vpnStatus
    }

    private var vpnInterface: ParcelFileDescriptor? = null
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "ACTION_CONNECT" -> startVpn()
            "ACTION_DISCONNECT" -> stopVpn()
        }
        return START_STICKY
    }

    private fun startVpn() {
        if (_vpnStatus.value is VpnStatus.Connected) return

        _vpnStatus.value = VpnStatus.Connecting
        
        serviceScope.launch {
            try {
                // In a real implementation with Xray, we would:
                // 1. Prepare Xray config
                // 2. Start Xray-core via JNI
                // 3. Establish TUN interface

                establishInterface()
                _vpnStatus.value = VpnStatus.Connected
                Log.d(TAG, "VPN Connected")
                
                // Keep-alive or monitoring loop
                while (isActive) {
                    delay(1000)
                    // Periodic checks
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to start VPN", e)
                _vpnStatus.value = VpnStatus.Error(e.message ?: "Unknown error")
                stopVpn()
            }
        }
    }

    private fun establishInterface() {
        val builder = Builder()
        builder.setSession("BrandedVPN")
        builder.addAddress("10.0.0.2", 32)
        builder.addRoute("0.0.0.0", 0)
        builder.setMtu(1500)
        
        vpnInterface = builder.establish()
    }

    private fun stopVpn() {
        _vpnStatus.value = VpnStatus.Disconnecting
        serviceScope.coroutineContext.cancelChildren()
        
        try {
            vpnInterface?.close()
            vpnInterface = null
        } catch (e: Exception) {
            Log.e(TAG, "Error closing interface", e)
        }
        
        _vpnStatus.value = VpnStatus.Disconnected
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
