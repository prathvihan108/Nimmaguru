package com.nimmaguru.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nimmaguru.app.databinding.ActivityGuruDetailBinding
import com.nimmaguru.app.models.Guru
import com.nimmaguru.app.models.Session
import com.google.firebase.firestore.FirebaseFirestore

class GuruDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuruDetailBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuruDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val guruId = intent.getStringExtra("GURU_ID") ?: return
        
        binding.rvGuruSessions.layoutManager = LinearLayoutManager(this)

        loadGuru(guruId)
        loadSessions(guruId)
    }

    private fun loadGuru(id: String) {
        db.collection("gurus").document(id).get().addOnSuccessListener { doc ->
            val guru = doc.toObject(Guru::class.java)
            guru?.let {
                binding.tvGuruName.text = it.name
                binding.tvGuruBio.text = it.bio
            }
        }
    }

    private fun loadSessions(id: String) {
        db.collection("sessions")
            .whereEqualTo("guruId", id)
            .get().addOnSuccessListener { snapshot ->
                val sessions = snapshot.toObjects(Session::class.java)
                // Set adapter
            }
    }
}
