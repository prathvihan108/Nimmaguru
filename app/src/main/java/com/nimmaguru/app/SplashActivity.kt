package com.nimmaguru.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nimmaguru.app.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIAmGuru.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                putExtra("USER_TYPE", "GURU")
            })
        }

        binding.btnIAmStudent.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                putExtra("USER_TYPE", "STUDENT")
            })
        }

        // Language toggle logic would go here
    }
}
