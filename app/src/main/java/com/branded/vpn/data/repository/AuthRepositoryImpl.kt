package com.branded.vpn.data.repository

import com.branded.vpn.core.domain.model.Subscription
import com.branded.vpn.core.domain.model.User
import com.branded.vpn.core.domain.repository.AuthRepository
import com.branded.vpn.data.local.dao.VpnDao
import com.branded.vpn.data.local.entity.UserEntity
import com.branded.vpn.data.remote.VpnApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val vpnApi: VpnApi,
    private val vpnDao: VpnDao
) : AuthRepository {

    override fun getSession(): Flow<User?> = vpnDao.getUserProfile().map { it?.toDomain() }

    override suspend fun login(email: String, psw: String): Result<User> = try {
        val response = vpnApi.login(mapOf("email" to email, "password" to psw))
        if (response.isSuccessful && response.body() != null) {
            val dto = response.body()!!
            val userEntity = UserEntity(
                dto.userId, dto.email, dto.token, "Premium", 
                System.currentTimeMillis() + 30L * 24 * 3600 * 1000, true, "https://sub.url"
            )
            vpnDao.saveUserProfile(userEntity)
            Result.success(userEntity.toDomain())
        } else {
            Result.failure(Exception("Login failed: ${response.code()}"))
        }
    } catch (e: Exception) { Result.failure(e) }

    override suspend fun logout() {
        vpnDao.clearUserProfile()
    }

    override suspend fun restoreSession(): Result<User?> = try {
        // Here we could call a refresh token API
        Result.success(null) 
    } catch (e: Exception) { Result.failure(e) }

    private fun UserEntity.toDomain() = User(
        id, email, token, 
        Subscription("p1", planName, expiryDate, isActive, subscriptionUrl)
    )
}
