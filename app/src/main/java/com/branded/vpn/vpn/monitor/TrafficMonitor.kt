package com.branded.vpn.vpn.monitor

import android.net.TrafficStats
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrafficMonitor @Inject constructor() {

    fun getTrafficFlow() = flow {
        var lastTx = TrafficStats.getTotalTxBytes()
        var lastRx = TrafficStats.getTotalRxBytes()
        
        while (true) {
            delay(1000)
            val currentTx = TrafficStats.getTotalTxBytes()
            val currentRx = TrafficStats.getTotalRxBytes()
            
            val txSpeed = (currentTx - lastTx) / 1024 // KB/s
            val rxSpeed = (currentRx - lastRx) / 1024 // KB/s
            
            emit(com.branded.vpn.core.domain.model.TrafficStats(currentRx, currentTx, "$rxSpeed KB/s", "$txSpeed KB/s"))
            
            lastTx = currentTx
            lastRx = currentRx
        }
    }
}
