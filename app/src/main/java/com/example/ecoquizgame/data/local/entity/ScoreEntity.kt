package com.example.ecoquizgame.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: Long,
    val username: String,
    val value: Int,
    val mode: String,
    val timestamp: Long
)
