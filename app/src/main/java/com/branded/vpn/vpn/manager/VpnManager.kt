package com.branded.vpn.vpn.manager

import android.content.Context
import android.content.Intent
import com.branded.vpn.core.domain.model.VpnNode
import com.branded.vpn.core.domain.model.VpnStatus
import com.branded.vpn.vpn.service.VpnTunnelService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VpnManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val vpnStatus: StateFlow<VpnStatus> = VpnTunnelService.vpnStatus

    fun connect(node: VpnNode) {
        val intent = Intent(context, VpnTunnelService::class.java).apply {
            action = "ACTION_CONNECT"
            putExtra("EXTRA_NODE_ID", node.id)
        }
        context.startService(intent)
    }

    fun disconnect() {
        val intent = Intent(context, VpnTunnelService::class.java).apply {
            action = "ACTION_DISCONNECT"
        }
        context.startService(intent)
    }
}
