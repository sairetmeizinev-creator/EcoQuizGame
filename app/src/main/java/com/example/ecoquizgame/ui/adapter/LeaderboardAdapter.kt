package com.example.ecoquizgame.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoquizgame.data.local.entity.ScoreEntity
import com.example.ecoquizgame.databinding.ItemLeaderboardBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LeaderboardAdapter : RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {
    private val items = mutableListOf<ScoreEntity>()
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    fun submitData(scores: List<ScoreEntity>) {
        items.clear()
        items.addAll(scores.sortedByDescending { it.value })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val binding =
            ItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderboardViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        holder.bind(items[position], position + 1)
    }

    inner class LeaderboardViewHolder(private val binding: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(score: ScoreEntity, position: Int) {
            binding.rankText.text = position.toString()
            binding.usernameText.text = score.username
            binding.scoreText.text = score.value.toString()
            binding.modeText.text = score.mode
            binding.timeText.text = formatter.format(Date(score.timestamp))
        }
    }
}
