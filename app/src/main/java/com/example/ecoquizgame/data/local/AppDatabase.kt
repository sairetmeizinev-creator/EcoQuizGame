package com.example.ecoquizgame.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecoquizgame.data.local.dao.ScoreDao
import com.example.ecoquizgame.data.local.dao.UserDao
import com.example.ecoquizgame.data.local.entity.ScoreEntity
import com.example.ecoquizgame.data.local.entity.UserEntity

@Database(entities = [UserEntity::class, ScoreEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "eco_quiz_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
