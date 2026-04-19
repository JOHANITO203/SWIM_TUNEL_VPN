package com.branded.vpn.ui.screens.servers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branded.vpn.core.domain.model.VpnNode
import com.branded.vpn.core.domain.repository.VpnRepository
import com.branded.vpn.data.local.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServersViewModel @Inject constructor(
    private val vpnRepository: VpnRepository,
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val nodes = vpnRepository.getNodes()
    
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            vpnRepository.fetchNodes()
            _isRefreshing.value = false
        }
    }

    fun selectNode(node: VpnNode) {
        viewModelScope.launch {
            settingsDataStore.setSelectedNode(node.id)
        }
    }
}
