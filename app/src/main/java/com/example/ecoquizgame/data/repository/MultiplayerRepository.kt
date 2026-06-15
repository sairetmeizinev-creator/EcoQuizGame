package com.example.ecoquizgame.data.repository

import com.example.ecoquizgame.data.model.MultiplayerSession
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MultiplayerRepository {
    private val firestore = Firebase.firestore

    fun createSession(session: MultiplayerSession) {
        firestore.collection("multiplayer_sessions")
            .document(session.code)
            .set(session)
    }

    fun joinSession(code: String, username: String) {
        val doc = firestore.collection("multiplayer_sessions").document(code)
        firestore.runTransaction { tx ->
            val current = tx.get(doc).toObject(MultiplayerSession::class.java) ?: MultiplayerSession(code = code)
            val players = (current.players + username).distinct()
            tx.set(doc, current.copy(players = players, state = if (players.size >= 2) "started" else "waiting"))
        }
    }
}
