package com.example.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.project.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weight = intent.getFloatExtra("weight", 0f)

        when {
            weight > 3 -> binding.resultTextView.text = "틀렸습니다."
            weight > 2 -> binding.resultTextView.text = "틀렸습니다."
            weight > 1 -> binding.resultTextView.text = "정답입니다."
            else -> binding.resultTextView.text = "틀렸습니다."
        }

        when {
            weight > 2 -> binding.imageView.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
            weight > 1 -> binding.imageView.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
            else -> binding.imageView.setImageResource(R.drawable.ic_baseline_sentiment_dissatisfied_24)
        }

        Toast.makeText(this,"저에 대해 알아주셔서 감사합니다.",Toast.LENGTH_SHORT).show()
    }
}