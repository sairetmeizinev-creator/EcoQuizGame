package com.example.ecoquizgame.data.repository

import com.example.ecoquizgame.data.local.dao.ScoreDao
import com.example.ecoquizgame.data.local.entity.ScoreEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

class LeaderboardRepository(private val scoreDao: ScoreDao) {
    private val firestore = Firebase.firestore

    fun topScores(): Flow<List<ScoreEntity>> = scoreDao.getTopScores()

    fun userScores(userId: Long): Flow<List<ScoreEntity>> = scoreDao.getScoresForUser(userId)

    suspend fun saveScore(score: ScoreEntity) {
        scoreDao.insertScore(score)

        // Firebase sync placeholder: production should handle failures and retries.
        firestore.collection("scores").add(score)
    }

    suspend fun clearProgress() {
        scoreDao.clearScores()
    }
}
