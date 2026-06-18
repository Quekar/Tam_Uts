package com.example.tam_uts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tam_uts.data.User
import com.example.tam_uts.repository.AuthRepository
import com.example.tam_uts.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: User) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

class AuthViewModel : ViewModel() {

    private val authRepository = AuthRepository()
    private val userRepository = UserRepository()

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        val firebaseUser = authRepository.currentUser
        if (firebaseUser != null) {
            loadUserProfile(firebaseUser.uid)
        }
    }

    fun isLoggedIn(): Boolean = authRepository.isLoggedIn

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Email dan password tidak boleh kosong.")
            return
        }
        if (password.length < 6) {
            _uiState.value = AuthUiState.Error("Password minimal 6 karakter.")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val authResult = authRepository.login(email, password)
            if (authResult.isFailure) {
                _uiState.value = AuthUiState.Error(
                    authResult.exceptionOrNull()?.message ?: "Login gagal."
                )
                return@launch
            }

            val uid = authResult.getOrNull()!!.uid
            loadUserProfile(uid)
        }
    }

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ) {
        if (firstName.isBlank() || lastName.isBlank()) {
            _uiState.value = AuthUiState.Error("Nama lengkap wajib diisi.")
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = AuthUiState.Error("Format email tidak valid.")
            return
        }
        if (phone.isBlank()) {
            _uiState.value = AuthUiState.Error("Nomor telepon wajib diisi.")
            return
        }
        if (password.length < 8) {
            _uiState.value = AuthUiState.Error("Password minimal 8 karakter.")
            return
        }
        if (password != confirmPassword) {
            _uiState.value = AuthUiState.Error("Konfirmasi password tidak cocok.")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val authResult = authRepository.register(email, password)
            if (authResult.isFailure) {
                _uiState.value = AuthUiState.Error(
                    authResult.exceptionOrNull()?.message ?: "Registrasi gagal."
                )
                return@launch
            }

            val uid = authResult.getOrNull()!!.uid
            val fullName = "$firstName $lastName"
            val profileResult = userRepository.createUserProfile(
                uid = uid,
                name = fullName,
                email = email,
                phone = phone
            )
            if (profileResult.isFailure) {
                _uiState.value = AuthUiState.Error(
                    profileResult.exceptionOrNull()?.message ?: "Gagal menyimpan profil."
                )
                return@launch
            }

            val newUser = User(
                name = fullName,
                email = email,
                phone = phone,
                bio = "",
                recipesUploaded = 0
            )
            _currentUser.value = newUser
            _uiState.value = AuthUiState.Success(newUser)
        }
    }

    fun logout() {
        authRepository.logout()
        _currentUser.value = null
        _uiState.value = AuthUiState.Idle
    }

    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }

    private fun loadUserProfile(uid: String) {
        viewModelScope.launch {
            val profileResult = userRepository.getUserProfile(uid)
            if (profileResult.isSuccess) {
                val user = profileResult.getOrNull()!!
                _currentUser.value = user
                _uiState.value = AuthUiState.Success(user)
            } else {
                _uiState.value = AuthUiState.Error(
                    profileResult.exceptionOrNull()?.message ?: "Gagal memuat profil."
                )
            }
        }
    }

    fun updateUserProfile(updatedUser: User) {
        val uid = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val result = userRepository.updateUserProfile(uid, updatedUser)
            if (result.isSuccess) {
                _currentUser.value = updatedUser
                _uiState.value = AuthUiState.Success(updatedUser)
            } else {
                _uiState.value = AuthUiState.Error(
                    result.exceptionOrNull()?.message ?: "Gagal memperbarui profil."
                )
            }
        }
    }
}