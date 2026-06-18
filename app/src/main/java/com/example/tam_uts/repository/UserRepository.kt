package com.example.tam_uts.repository

import com.example.tam_uts.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    suspend fun createUserProfile(
        uid: String,
        name: String,
        email: String,
        phone: String
    ): Result<Unit> {
        return try {
            val data = hashMapOf(
                "name" to name,
                "email" to email,
                "phone" to phone,
                "bio" to "",
                "recipesUploaded" to 0
            )
            usersCollection.document(uid).set(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Gagal menyimpan profil: ${e.message}"))
        }
    }

    suspend fun getUserProfile(uid: String): Result<User> {
        return try {
            val snapshot = usersCollection.document(uid).get().await()
            if (!snapshot.exists()) {
                return Result.failure(Exception("Profil tidak ditemukan"))
            }
            val user = User(
                name = snapshot.getString("name") ?: "",
                email = snapshot.getString("email") ?: "",
                phone = snapshot.getString("phone") ?: "",
                bio = snapshot.getString("bio") ?: "",
                recipesUploaded = snapshot.getLong("recipesUploaded")?.toInt() ?: 0
            )
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(Exception("Gagal mengambil profil: ${e.message}"))
        }
    }

    suspend fun updateUserProfile(uid: String, updatedUser: User): Result<Unit> {
        return try {
            val data = mapOf(
                "name" to updatedUser.name,
                "email" to updatedUser.email,
                "phone" to updatedUser.phone,
                "bio" to updatedUser.bio
            )
            usersCollection.document(uid).update(data).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(Exception("Gagal memperbarui profil: ${e.message}"))
        }
    }
}