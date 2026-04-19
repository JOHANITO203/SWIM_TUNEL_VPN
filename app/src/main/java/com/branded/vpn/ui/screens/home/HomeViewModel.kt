package com.branded.vpn.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branded.vpn.core.domain.model.*
import com.branded.vpn.vpn.manager.VpnManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val vpnStatus: VpnStatus = VpnStatus.Disconnected,
    val selectedNode: VpnNode? = null,
    val trafficStats: TrafficStats = TrafficStats(0, 0, "0 KB/s", "0 KB/s")
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val vpnManager: VpnManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            vpnManager.vpnStatus.collect { status ->
                _uiState.update { it.copy(vpnStatus = status) }
            }
        }
        
        // Traffic simulation
        viewModelScope.launch {
            while (true) {
                if (_uiState.value.vpnStatus is VpnStatus.Connected) {
                    _uiState.update { 
                        it.copy(trafficStats = TrafficStats(0, 0, "${(10..200).random()} KB/s", "${(1..50).random()} KB/s"))
                    }
                }
                delay(1000)
            }
        }
    }

    fun toggleConnection() {
        val status = _uiState.value.vpnStatus
        if (status is VpnStatus.Disconnected) {
            _uiState.value.selectedNode?.let { vpnManager.connect(it) } ?: run {
                // Mock selection
                val mockNode = VpnNode("1", "US - New York", "US", "1.1.1.1", 443, Protocol.VLESS, 20, 50)
                vpnManager.connect(mockNode)
            }
        } else if (status is VpnStatus.Connected) {
            vpnManager.disconnect()
        }
    }
}
