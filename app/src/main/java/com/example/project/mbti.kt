package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.example.project.databinding.ActivityMbtiBinding

class mbti : AppCompatActivity() {
    private val binding by lazy{
        ActivityMbtiBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadData()

        binding.resultButton.setOnClickListener {
            if (binding.weightEditText.text.isNotBlank()) {

                saveData(
                    binding.weightEditText.text.toString().toFloat()
                )

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("weight", binding.weightEditText.text.toString().toFloat())
                startActivity(intent)
            }
        }
    }
    private fun saveData(weight: Float){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putInt("KEY_WEIGHT", weight.toInt())
            .apply()
    }
    private fun loadData() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val weight = pref.getInt("KEY_WEIGHT", 0)

        if(weight != 0) {
            binding.weightEditText.setText(weight.toString())
        }
    }
}