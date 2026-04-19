package com.branded.vpn.vpn.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.branded.vpn.data.local.SettingsDataStore
import com.branded.vpn.vpn.manager.VpnManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    
    @Inject lateinit var settingsDataStore: SettingsDataStore
    @Inject lateinit var vpnManager: VpnManager

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                val autoConnect = settingsDataStore.autoConnect.first()
                if (autoConnect) {
                    Log.d("BootReceiver", "Auto-connecting VPN after boot")
                    vpnManager.reconnectLast()
                }
            }
        }
    }
}
