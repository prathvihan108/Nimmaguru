package com.nimmaguru.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nimmaguru.app.databinding.ActivityGuruProfileSetupBinding
import com.nimmaguru.app.models.Guru
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GuruProfileSetupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuruProfileSetupBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuruProfileSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val subjects = listOf("Math", "Science", "English", "Kannada", "Carpentry", "Agriculture")
        subjects.forEach { subject ->
            val chip = Chip(this)
            chip.text = subject
            chip.isCheckable = true
            binding.cgSubjects.addView(chip)
        }

        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }
    }

    private fun saveProfile() {
        val uid = auth.currentUser?.uid ?: return
        val name = binding.etFullName.text.toString()
        val village = binding.etVillage.text.toString()
        val bio = binding.etBio.text.toString()
        
        val selectedSkills = mutableListOf<String>()
        for (i in 0 until binding.cgSubjects.childCount) {
            val chip = binding.cgSubjects.getChildAt(i) as Chip
            if (chip.isChecked) selectedSkills.add(chip.text.toString())
        }

        val guru = Guru(
            id = uid,
            name = name,
            phone = auth.currentUser?.phoneNumber ?: "",
            village = village,
            bio = bio,
            skills = selectedSkills
        )

        db.collection("gurus").document(uid).set(guru).addOnSuccessListener {
            startActivity(Intent(this, GuruDashboardActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to save profile", Toast.LENGTH_SHORT).show()
        }
    }
}
