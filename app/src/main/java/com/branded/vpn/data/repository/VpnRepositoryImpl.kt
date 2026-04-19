package com.branded.vpn.data.repository

import com.branded.vpn.core.domain.model.*
import com.branded.vpn.core.domain.repository.VpnRepository
import com.branded.vpn.data.local.dao.VpnDao
import com.branded.vpn.data.local.entity.NodeEntity
import com.branded.vpn.data.local.entity.UserEntity
import com.branded.vpn.data.remote.VpnApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VpnRepositoryImpl @Inject constructor(
    private val vpnApi: VpnApi,
    private val vpnDao: VpnDao
) : VpnRepository {

    override fun getNodes(): Flow<List<VpnNode>> = vpnDao.getAllNodes().map { entities ->
        entities.map { it.toDomain() }
    }

    override fun getSubscription(): Flow<Subscription?> = vpnDao.getUserProfile().map { it?.toSubscription() }

    override suspend fun fetchNodes(): Result<List<VpnNode>> = try {
        val response = vpnApi.getNodes()
        if (response.isSuccessful) {
            val list = response.body() ?: emptyList()
            vpnDao.insertNodes(list.map { it.toEntity() })
            Result.success(list.map { it.toDomain() })
        } else Result.failure(Exception("API Error: ${response.code()}"))
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun refreshSubscription(): Result<Subscription> = try {
        val response = vpnApi.getSubscription()
        if (response.isSuccessful) {
            val sub = response.body() ?: throw Exception("Empty response")
            // Update user profile in DB with latest sub info
            Result.success(sub.toDomain())
        } else Result.failure(Exception("API Error"))
    } catch (e: Exception) { Result.failure(e) }

    // Extension functions for mapping
    private fun NodeEntity.toDomain() = VpnNode(id, name, countryCode, host, port, protocol, load, ping)
    private fun com.branded.vpn.data.remote.dto.NodeResponse.toEntity() = NodeEntity(id, name, cc, ip, port, Protocol.VLESS, 0, 0)
    private fun com.branded.vpn.data.remote.dto.NodeResponse.toDomain() = VpnNode(id, name, cc, ip, port, Protocol.VLESS, 0, 0)
    private fun UserEntity.toSubscription() = Subscription("p1", planName, expiryDate, isActive, subscriptionUrl)
    private fun com.branded.vpn.data.remote.dto.SubscriptionResponse.toDomain() = Subscription("p1", plan, expires, active, subUrl)
}
