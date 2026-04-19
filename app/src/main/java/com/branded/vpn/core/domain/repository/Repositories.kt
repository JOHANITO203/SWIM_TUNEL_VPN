package com.branded.vpn.core.domain.repository

import com.branded.vpn.core.domain.model.Subscription
import com.branded.vpn.core.domain.model.User
import com.branded.vpn.core.domain.model.VpnNode
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getUser(): Flow<User?>
    suspend fun login(email: String, psw: String): Result<Unit>
    suspend fun logout()
    suspend fun restoreSession(): Result<Unit>
}

interface VpnRepository {
    fun getNodes(): Flow<List<VpnNode>>
    fun getSubscription(): Flow<Subscription?>
    suspend fun refreshSubscription(): Result<Unit>
}
