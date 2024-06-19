package com.example.rs_app

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import generateShortUuid
import kotlinx.coroutines.tasks.await

data class UserModel(
    val email: String = "",
    val phone: String = "",
    val password: String? = null,
    val avatar: String = "",
    val username: String = "",
    val id: String = generateShortUuid(),
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        private const val COLLECTION_NAME = "users"
        private val db: FirebaseFirestore = Firebase.firestore

        suspend fun findAll(): List<UserModel> {
            return try {
                val querySnapshot = db.collection(COLLECTION_NAME).get().await()
                querySnapshot.toObjects(UserModel::class.java)
            } catch (e: Exception) {
                emptyList()
            }
        }

        suspend fun getUserByPhone(phone: String): UserModel? {
            return try {
                val querySnapshot = db.collection(COLLECTION_NAME)
                    .whereEqualTo("phone", phone)
                    .get()
                    .await()
                if (!querySnapshot.isEmpty) {
                    querySnapshot.documents[0].toObject(UserModel::class.java)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }

        suspend fun findByEmail(email: String): UserModel? {
            return try {
                val querySnapshot = db.collection(COLLECTION_NAME)
                    .whereEqualTo("email", email)
                    .get()
                    .await()
                if (!querySnapshot.isEmpty) {
                    querySnapshot.documents[0].toObject(UserModel::class.java)
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }

        suspend fun bulkInsert(users: List<UserModel>) {
            try {
                val batch = db.batch()
                for (user in users) {
                    val docRef = db.collection(COLLECTION_NAME).document(user.email)
                    batch.set(docRef, user)
                }
                batch.commit().await()
            } catch (e: Exception) {
                throw e
            }
        }

        suspend fun findById(id: String): UserModel? {
            return try {
                val documentSnapshot = db.collection(COLLECTION_NAME).document(id).get().await()
                documentSnapshot.toObject(UserModel::class.java)
            } catch (e: Exception) {
                null
            }
        }

        suspend fun update(key: String, payload: Map<String, Any>): UserModel? {
            return try {
                val userRef = db.collection(COLLECTION_NAME).document(key)
                val userSnapshot = userRef.get().await()
                if (!userSnapshot.exists()) throw Exception("User does not exist")

                userRef.update(payload).await()
                val updatedSnapshot = userRef.get().await()
                updatedSnapshot.toObject(UserModel::class.java)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun save(): UserModel? {
        return try {
            val existingUser = findByEmail(this.email)
            if (existingUser != null) throw Exception("User already exists")

            val userRef = db.collection(COLLECTION_NAME).document(this.id)
            userRef.set(this).await()
            this
        } catch (e: Exception) {
            throw e
        }
    }
}
