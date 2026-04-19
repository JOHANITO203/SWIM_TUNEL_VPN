package com.branded.vpn.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class SettingsDataStore @Inject constructor(private val context: Context) {

    private val KEY_SELECTED_NODE_ID = stringPreferencesKey("selected_node_id")
    private val KEY_AUTO_CONNECT = booleanPreferencesKey("auto_connect")
    private val KEY_PROTOCOL = stringPreferencesKey("vpn_protocol")
    private val KEY_THEME = stringPreferencesKey("app_theme")

    val selectedNodeId: Flow<String?> = context.dataStore.data.map { it[KEY_SELECTED_NODE_ID] }
    val autoConnect: Flow<Boolean> = context.dataStore.data.map { it[KEY_AUTO_CONNECT] ?: false }
    val vpnProtocol: Flow<String> = context.dataStore.data.map { it[KEY_PROTOCOL] ?: "VLESS" }
    val appTheme: Flow<String> = context.dataStore.data.map { it[KEY_THEME] ?: "SYSTEM" }

    suspend fun setSelectedNode(nodeId: String) {
        context.dataStore.edit { it[KEY_SELECTED_NODE_ID] = nodeId }
    }

    suspend fun setAutoConnect(enabled: Boolean) {
        context.dataStore.edit { it[KEY_AUTO_CONNECT] = enabled }
    }

    suspend fun setProtocol(protocol: String) {
        context.dataStore.edit { it[KEY_PROTOCOL] = protocol }
    }

    suspend fun setTheme(theme: String) {
        context.dataStore.edit { it[KEY_THEME] = theme }
    }
}
