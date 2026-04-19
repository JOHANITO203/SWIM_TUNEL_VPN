package com.branded.vpn.vpn.monitor

import android.net.TrafficStats
import com.branded.vpn.core.domain.model.TrafficStats as DomainTraffic
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrafficMonitor @Inject constructor() {

    fun getTrafficFlow() = flow {
        var prevRx = TrafficStats.getTotalRxBytes()
        var prevTx = TrafficStats.getTotalTxBytes()
        
        while (true) {
            delay(1000)
            val currRx = TrafficStats.getTotalRxBytes()
            val currTx = TrafficStats.getTotalTxBytes()
            
            val rxBytes = if (currRx > prevRx) currRx - prevRx else 0L
            val txBytes = if (currTx > prevTx) currTx - prevTx else 0L
            
            val rxSpeed = formatSpeed(rxBytes)
            val txSpeed = formatSpeed(txBytes)
            
            emit(DomainTraffic(currRx, currTx, rxSpeed, txSpeed))
            
            prevRx = currRx
            prevTx = currTx
        }
    }

    private fun formatSpeed(bytes: Long): String {
        val kb = bytes / 1024.0
        return if (kb > 1024) {
            String.format("%.1f MB/s", kb / 1024.0)
        } else {
            String.format("%.1f KB/s", kb)
        }
    }
}
