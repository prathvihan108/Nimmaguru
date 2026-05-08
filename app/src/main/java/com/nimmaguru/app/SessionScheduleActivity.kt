package com.nimmaguru.app

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nimmaguru.app.databinding.ActivitySessionScheduleBinding
import com.nimmaguru.app.models.Session
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class SessionScheduleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySessionScheduleBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var selectedDate = ""
    private var selectedTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val subjects = listOf("Math", "Science", "English", "Kannada", "Carpentry", "Agriculture")
        binding.spSubject.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, subjects)

        binding.btnPickDate.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "$d/${m+1}/$y"
                binding.btnPickDate.text = selectedDate
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.btnPickTime.setOnClickListener {
            val c = Calendar.getInstance()
            TimePickerDialog(this, { _, h, m ->
                selectedTime = String.format("%02d:%02d", h, m)
                binding.btnPickTime.text = selectedTime
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show()
        }

        binding.btnSchedule.setOnClickListener {
            saveSession()
        }
    }

    private fun saveSession() {
        val uid = auth.currentUser?.uid ?: return
        val subject = binding.spSubject.selectedItem.toString()
        val location = binding.etLocation.text.toString()
        val maxUsers = binding.npMaxStudents.value

        val session = Session(
            id = UUID.randomUUID().toString(),
            guruId = uid,
            subject = subject,
            date = selectedDate,
            time = selectedTime,
            location = location,
            maxStudents = maxUsers
        )

        db.collection("sessions").document(session.id).set(session).addOnSuccessListener {
            Toast.makeText(this, "Session Scheduled!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
