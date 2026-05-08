package com.nimmaguru.app

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nimmaguru.app.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("NIMMA_GURU_PREFS", Context.MODE_PRIVATE)

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            // Redirect to Splash
        }

        binding.sliderFontSize.addOnChangeListener { _, value, _ ->
            prefs.edit().putFloat("FONT_SIZE", value).apply()
            // Apply font size globally logic
        }
    }
}
