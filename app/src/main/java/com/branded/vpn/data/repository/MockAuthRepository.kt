package com.branded.vpn.data.repository

import com.branded.vpn.core.domain.model.User
import com.branded.vpn.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockAuthRepository @Inject constructor() : AuthRepository {
    private val _user = MutableStateFlow<User?>(null)
    override fun getUser() = _user.asStateFlow()

    override suspend fun login(email: String, psw: String): Result<Unit> {
        _user.value = User("1", email, null)
        return Result.success(Unit)
    }

    override suspend fun logout() {
        _user.value = null
    }

    override suspend fun restoreSession(): Result<Unit> {
        return Result.success(Unit)
    }
}
