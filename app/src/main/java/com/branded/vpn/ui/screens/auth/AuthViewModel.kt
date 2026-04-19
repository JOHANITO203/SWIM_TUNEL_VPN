package com.branded.vpn.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.branded.vpn.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun login(email: String, psw: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(1500) // Simulating network
            authRepository.login(email, psw)
            _isLoading.value = false
            onSuccess()
        }
    }
}
