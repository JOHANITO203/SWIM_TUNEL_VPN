package com.branded.vpn.vpn.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.core.app.NotificationCompat
import com.branded.vpn.MainActivity
import com.branded.vpn.R
import com.branded.vpn.core.domain.model.VpnStatus
import com.branded.vpn.vpn.engine.XrayTunnelEngine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@AndroidEntryPoint
class VpnTunnelService : VpnService() {

    @Inject lateinit var tunnelEngine: XrayTunnelEngine

    companion object {
        const val ACTION_CONNECT = "com.branded.vpn.ACTION_CONNECT"
        const val ACTION_DISCONNECT = "com.branded.vpn.ACTION_DISCONNECT"
        
        private val _vpnStatus = MutableStateFlow<VpnStatus>(VpnStatus.Disconnected)
        val vpnStatus = _vpnStatus.asStateFlow()
    }

    private var tunnelInterface: ParcelFileDescriptor? = null
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_CONNECT -> {
                val host = intent.getStringExtra("EXTRA_HOST") ?: "1.1.1.1"
                startVpn(host)
            }
            ACTION_DISCONNECT -> stopVpn()
        }
        return START_STICKY
    }

    private fun startVpn(host: String) {
        if (_vpnStatus.value is VpnStatus.Connected) return

        _vpnStatus.value = VpnStatus.Connecting
        startForeground(1, createNotification("Connecting to tunnel..."))

        serviceScope.launch {
            try {
                // Here we would configure Xray via JNI or executable
                tunnelEngine.prepare(host)
                
                tunnelInterface = establishInterface()
                
                if (tunnelInterface != null) {
                    tunnelEngine.start(tunnelInterface!!.fileDescriptor.fd)
                    _vpnStatus.value = VpnStatus.Connected
                    updateNotification("Protected via SWIMTUNELVPN")
                    Log.i("VPN", "Tunnel established successfully")
                } else {
                    throw Exception("Could not establish tunnel interface")
                }

            } catch (e: Exception) {
                Log.e("VPN", "Error starting VPN", e)
                _vpnStatus.value = VpnStatus.Error(e.message ?: "Unknown technical error")
                stopVpn()
            }
        }
    }

    private fun establishInterface(): ParcelFileDescriptor? {
        val builder = Builder()
        builder.setSession("SWIMTUNELVPN-Tunnel")
        builder.addAddress("10.0.0.1", 32)
        builder.addRoute("0.0.0.0", 0) // Route all IPv4 traffic
        builder.addDnsServer("1.1.1.1")
        builder.setMtu(1500)
        builder.setBlocking(false)
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            builder.setMetered(false)
        }
        
        return builder.establish()
    }

    private fun stopVpn() {
        _vpnStatus.value = VpnStatus.Disconnecting
        serviceScope.coroutineContext.cancelChildren()
        
        tunnelEngine.stop()
        
        try {
            tunnelInterface?.close()
            tunnelInterface = null
        } catch (e: Exception) {
            Log.e("VPN", "Failed to close interface", e)
        }
        
        _vpnStatus.value = VpnStatus.Disconnected
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun createNotification(content: String): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, "vpn_status_channel")
            .setContentTitle("SWIMTUNELVPN")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_lock_lock) // Placeholder icon
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
    }

    private fun updateNotification(content: String) {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(1, createNotification(content))
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
