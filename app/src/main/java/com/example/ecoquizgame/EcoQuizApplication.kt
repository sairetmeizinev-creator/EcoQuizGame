package com.example.ecoquizgame

import android.app.Application
import com.example.ecoquizgame.data.local.AppDatabase
import com.example.ecoquizgame.data.repository.AuthRepository
import com.example.ecoquizgame.data.repository.GameRepository
import com.example.ecoquizgame.data.repository.LeaderboardRepository

class EcoQuizApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val authRepository by lazy { AuthRepository(database.userDao()) }
    val gameRepository by lazy { GameRepository() }
    val leaderboardRepository by lazy { LeaderboardRepository(database.scoreDao()) }
}
