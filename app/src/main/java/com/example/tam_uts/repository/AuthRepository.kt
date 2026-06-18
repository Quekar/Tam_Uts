package com.example.tam_uts.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    val isLoggedIn: Boolean
        get() = auth.currentUser != null

    suspend fun login(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.failure(Exception("User tidak ditemukan"))
            Result.success(user)
        } catch (e: Exception) {
            val message = when {
                e.message?.contains("no user record") == true ||
                        e.message?.contains("INVALID_LOGIN_CREDENTIALS") == true ->
                    "Email atau password salah."
                e.message?.contains("badly formatted") == true ->
                    "Format email tidak valid."
                e.message?.contains("network") == true ->
                    "Tidak ada koneksi internet."
                else -> "Login gagal. Coba lagi."
            }
            Result.failure(Exception(message))
        }
    }

    suspend fun register(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.failure(Exception("Gagal membuat akun"))
            Result.success(user)
        } catch (e: Exception) {
            val message = when {
                e.message?.contains("email address is already in use") == true ->
                    "Email sudah terdaftar. Silakan login."
                e.message?.contains("badly formatted") == true ->
                    "Format email tidak valid."
                e.message?.contains("password is too weak") == true ->
                    "Password terlalu lemah. Minimal 6 karakter."
                e.message?.contains("network") == true ->
                    "Tidak ada koneksi internet."
                else -> "Registrasi gagal. Coba lagi."
            }
            Result.failure(Exception(message))
        }
    }

    fun logout() {
        auth.signOut()
    }
}