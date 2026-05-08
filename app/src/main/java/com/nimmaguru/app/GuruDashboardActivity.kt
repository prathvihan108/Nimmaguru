package com.nimmaguru.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nimmaguru.app.databinding.ActivityGuruDashboardBinding
import com.nimmaguru.app.models.Session
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GuruDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuruDashboardBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuruDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUpcomingSessions.layoutManager = LinearLayoutManager(this)
        
        loadGuruData()
        loadSessions()

        binding.fabAddSession.setOnClickListener {
            startActivity(Intent(this, SessionScheduleActivity::class.java))
        }
    }

    private fun loadGuruData() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("gurus").document(uid).addSnapshotListener { snapshot, _ ->
            snapshot?.let {
                val name = it.getString("name") ?: "Guru"
                binding.tvWelcome.text = "Namaskara, $name"
            }
        }
    }

    private fun loadSessions() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("sessions")
            .whereEqualTo("guruId", uid)
            .addSnapshotListener { snapshot, _ ->
                val sessions = snapshot?.toObjects(Session::class.java) ?: listOf()
                // Update RecyclerView adapter here
            }
    }
}
