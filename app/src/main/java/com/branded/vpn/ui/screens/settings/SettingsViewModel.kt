package com.branded.vpn.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branded.vpn.data.local.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : ViewModel() {

    val autoConnect = settingsDataStore.autoConnect
    val protocol = settingsDataStore.vpnProtocol

    fun toggleAutoConnect(enabled: Boolean) {
        viewModelScope.launch {
            settingsDataStore.setAutoConnect(enabled)
        }
    }

    fun setProtocol(protocol: String) {
        viewModelScope.launch {
            settingsDataStore.setProtocol(protocol)
        }
    }
}
