package com.branded.vpn.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.branded.vpn.core.domain.model.User
import com.branded.vpn.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val user: Flow<User?> = authRepository.getSession()
}
