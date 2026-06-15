package com.example.ecoquizgame.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ecoquizgame.R
import com.example.ecoquizgame.databinding.ItemAnswerBinding

class AnswerAdapter(
    private val onAnswerClick: (Int) -> Unit
) : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    private val answers = mutableListOf<String>()
    private var wrongAnswersIndices = setOf<Int>()

    fun submitData(items: List<String>) {
        answers.clear()
        answers.addAll(items)
        wrongAnswersIndices = emptySet()
        notifyDataSetChanged()
    }

    fun showWrongAnswers(indices: Set<Int>) {
        wrongAnswersIndices = indices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnswerViewHolder(binding)
    }

    override fun getItemCount(): Int = answers.size

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(answers[position], wrongAnswersIndices.contains(position))
    }

    inner class AnswerViewHolder(
        private val binding: ItemAnswerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text: String, isWrong: Boolean) {
            binding.answerText.text = text
            val context = binding.root.context
            
            val backgroundColor = if (isWrong) {
                ContextCompat.getColor(context, R.color.wrong_red)
            } else {
                ContextCompat.getColor(context, R.color.answer_default)
            }
            
            val strokeColor = if (isWrong) {
                ContextCompat.getColor(context, android.R.color.holo_red_dark)
            } else {
                ContextCompat.getColor(context, R.color.primary_green)
            }

            binding.card.setCardBackgroundColor(backgroundColor)
            binding.card.strokeColor = strokeColor
            
            binding.root.setOnClickListener {
                it.animate()
                    .scaleX(0.95f)
                    .scaleY(0.95f)
                    .setDuration(100)
                    .withEndAction {
                        it.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                        onAnswerClick(bindingAdapterPosition)
                    }
                    .start()
            }
        }
    }
}
