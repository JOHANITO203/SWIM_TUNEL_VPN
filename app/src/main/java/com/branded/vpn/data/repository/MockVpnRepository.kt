package com.branded.vpn.data.repository

import com.branded.vpn.core.domain.model.*
import com.branded.vpn.core.domain.repository.VpnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockVpnRepository @Inject constructor() : VpnRepository {

    override fun getNodes(): Flow<List<VpnNode>> = flowOf(
        listOf(
            VpnNode("1", "US - New York", "US", "104.16.123.1", 443, Protocol.VLESS, 20, 45),
            VpnNode("2", "UK - London", "GB", "104.16.123.2", 443, Protocol.VLESS, 15, 80),
            VpnNode("3", "DE - Frankfurt", "DE", "104.16.123.3", 443, Protocol.VLESS, 40, 60),
            VpnNode("4", "JP - Tokyo", "JP", "104.16.123.4", 443, Protocol.VLESS, 60, 180)
        )
    )

    override fun getSubscription(): Flow<Subscription?> = flowOf(
        Subscription("p1", "Premium", System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000, true, "https://api.vpn.com/sub/123")
    )

    override suspend fun refreshSubscription(): Result<Unit> {
        return Result.success(Unit)
    }
}
