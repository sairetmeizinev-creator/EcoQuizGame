package com.example.ecoquizgame.data.model

data class User(val id: Long, val username: String, val password: String)

data class Question(
    val id: Int,
    val text: String,
    val answers: List<String>,
    val correctAnswers: List<Int>
)

data class Score(
    val userId: Long,
    val value: Int,
    val mode: String,
    val timestamp: Long
)

data class MultiplayerSession(
    val code: String = "",
    val players: List<String> = emptyList(),
    val state: String = "waiting",
    val questionIndex: Int = 0,
    val timerSeconds: Int = 60,
    val scores: Map<String, Int> = emptyMap()
)
