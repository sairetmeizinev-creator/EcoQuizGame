package com.example.ecoquizgame.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ecoquizgame.data.local.entity.ScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert
    suspend fun insertScore(score: ScoreEntity)

    @Query("SELECT * FROM scores WHERE userId = :userId ORDER BY value DESC")
    fun getScoresForUser(userId: Long): Flow<List<ScoreEntity>>

    @Query("SELECT * FROM scores ORDER BY value DESC LIMIT 100")
    fun getTopScores(): Flow<List<ScoreEntity>>

    @Query("DELETE FROM scores")
    suspend fun clearScores()
}
