package com.example.ecoquizgame.data.repository

import com.example.ecoquizgame.data.local.dao.UserDao
import com.example.ecoquizgame.data.local.entity.UserEntity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepository(private val userDao: UserDao) {
    private val auth = Firebase.auth

    suspend fun signUp(username: String, password: String): Result<Long> = runCatching {
        val existing = userDao.getUserByUsername(username)
        require(existing == null) { "Username already exists" }
        val userId = userDao.insertUser(UserEntity(username = username, password = password))

        // Placeholder sync with Firebase Auth using synthetic email from username.
        auth.createUserWithEmailAndPassword("$username@ecoquiz.local", password)
        userId
    }

    suspend fun login(username: String, password: String): Result<UserEntity> = runCatching {
        val localUser = userDao.getUserByUsername(username)
            ?: error("User not found")
        require(localUser.password == password) { "Invalid password" }

        // Placeholder login to Firebase for multiplayer and online leaderboard.
        auth.signInWithEmailAndPassword("$username@ecoquiz.local", password)
        localUser
    }
}
