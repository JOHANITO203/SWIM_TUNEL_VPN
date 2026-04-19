package com.branded.vpn.core.domain.repository

import com.branded.vpn.core.domain.model.*
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getSession(): Flow<User?>
    suspend fun login(email: String, psw: String): Result<User>
    suspend fun logout()
    suspend fun restoreSession(): Result<User?>
}

interface VpnRepository {
    fun getNodes(): Flow<List<VpnNode>>
    fun getSubscription(): Flow<Subscription?>
    suspend fun fetchNodes(): Result<List<VpnNode>>
    suspend fun refreshSubscription(): Result<Subscription>
}
