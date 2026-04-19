package com.branded.vpn.ui.screens.servers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branded.vpn.core.domain.model.VpnNode
import com.branded.vpn.core.domain.repository.VpnRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ServersViewModel @Inject constructor(
    private val vpnRepository: VpnRepository
) : ViewModel() {

    val nodes: Flow<List<VpnNode>> = vpnRepository.getNodes()

    fun selectNode(node: VpnNode) {
        // Implementation for selecting node
    }
}
