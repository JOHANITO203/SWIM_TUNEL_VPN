package com.branded.vpn.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branded.vpn.core.domain.model.*
import com.branded.vpn.core.domain.repository.VpnRepository
import com.branded.vpn.vpn.manager.VpnManager
import com.branded.vpn.vpn.monitor.TrafficMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val vpnStatus: VpnStatus = VpnStatus.Disconnected,
    val selectedNode: VpnNode? = null,
    val trafficStats: TrafficStats = TrafficStats(0, 0, "0 KB/s", "0 KB/s"),
    val subscription: Subscription? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val vpnManager: VpnManager,
    private val vpnRepository: VpnRepository,
    private val trafficMonitor: TrafficMonitor
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // Listen to VPN status
        vpnManager.vpnStatus.onEach { status ->
            _uiState.update { it.copy(vpnStatus = status) }
        }.launchIn(viewModelScope)

        // Listen to traffic stats
        trafficMonitor.getTrafficFlow().onEach { stats ->
            _uiState.update { it.copy(trafficStats = stats) }
        }.launchIn(viewModelScope)

        // Listen to selected node & sub (simplified)
        vpnRepository.getNodes().onEach { nodes ->
            _uiState.update { it.copy(selectedNode = nodes.firstOrNull()) }
        }.launchIn(viewModelScope)
        
        vpnRepository.getSubscription().onEach { sub ->
            _uiState.update { it.copy(subscription = sub) }
        }.launchIn(viewModelScope)
    }

    fun toggleVpn() {
        if (_uiState.value.vpnStatus is VpnStatus.Disconnected || _uiState.value.vpnStatus is VpnStatus.Error) {
            _uiState.value.selectedNode?.let { node ->
                vpnManager.connect(node)
            }
        } else {
            vpnManager.disconnect()
        }
    }
}
