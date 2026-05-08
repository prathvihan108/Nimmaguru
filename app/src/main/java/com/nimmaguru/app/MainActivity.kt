package com.nimmaguru.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, SplashActivity::class.java))
        } else {
            // Check if user is Guru or Student in Firestore
            db.collection("gurus").document(currentUser.uid).get().addOnSuccessListener { doc ->
                if (doc.exists()) {
                    startActivity(Intent(this, GuruDashboardActivity::class.java))
                } else {
                    startActivity(Intent(this, StudentHomeActivity::class.java))
                }
                finish()
            }
        }
        finish()
    }
}
