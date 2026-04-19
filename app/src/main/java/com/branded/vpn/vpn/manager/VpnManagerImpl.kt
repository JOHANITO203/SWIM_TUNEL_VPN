package com.branded.vpn.vpn.manager

import android.content.Context
import android.content.Intent
import com.branded.vpn.core.domain.model.VpnNode
import com.branded.vpn.core.domain.model.VpnStatus
import com.branded.vpn.vpn.service.VpnTunnelService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VpnManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsDataStore: com.branded.vpn.data.local.SettingsDataStore,
    private val vpnRepository: com.branded.vpn.core.domain.repository.VpnRepository
) : VpnManager {

    private val managerScope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO + kotlinx.coroutines.SupervisorJob())

    override val vpnStatus: StateFlow<VpnStatus> = VpnTunnelService.vpnStatus

    override fun connect(node: VpnNode) {
        val intent = Intent(context, VpnTunnelService::class.java).apply {
            action = VpnTunnelService.ACTION_CONNECT
            putExtra("EXTRA_HOST", node.host)
        }
        context.startService(intent)
    }

    override fun disconnect() {
        val intent = Intent(context, VpnTunnelService::class.java).apply {
            action = VpnTunnelService.ACTION_DISCONNECT
        }
        context.startService(intent)
    }

    override fun reconnectLast() {
        managerScope.launch {
            val lastId = settingsDataStore.selectedNodeId.first()
            if (lastId != null) {
                val nodes = vpnRepository.getNodes().first()
                val target = nodes.find { it.id == lastId }
                target?.let { connect(it) }
            }
        }
    }
}

