package com.branded.vpn.vpn.manager

import com.branded.vpn.core.domain.model.VpnNode
import com.branded.vpn.core.domain.model.VpnStatus
import kotlinx.coroutines.flow.StateFlow

interface VpnManager {
    val vpnStatus: StateFlow<VpnStatus>
    fun connect(node: VpnNode)
    fun disconnect()
    fun reconnectLast()
}
