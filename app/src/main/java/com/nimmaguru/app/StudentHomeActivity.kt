package com.nimmaguru.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nimmaguru.app.databinding.ActivityStudentHomeBinding
import com.nimmaguru.app.models.Guru
import com.google.firebase.firestore.FirebaseFirestore

class StudentHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentHomeBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvGurus.layoutManager = LinearLayoutManager(this)
        
        loadGurus()
    }

    private fun loadGurus() {
        db.collection("gurus").addSnapshotListener { snapshot, _ ->
            val gurus = snapshot?.toObjects(Guru::class.java) ?: listOf()
            // Set adapter here
        }
    }
}
