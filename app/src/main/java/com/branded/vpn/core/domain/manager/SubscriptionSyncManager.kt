package com.branded.vpn.core.domain.manager

import com.branded.vpn.core.domain.repository.VpnRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionSyncManager @Inject constructor(
    private val vpnRepository: VpnRepository
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun startSync() {
        scope.launch {
            while (isActive) {
                vpnRepository.refreshSubscription()
                delay(3600000) // Sync every hour
            }
        }
    }

    fun stopSync() {
        scope.cancel()
    }
}
