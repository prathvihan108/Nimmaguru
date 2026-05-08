package com.nimmaguru.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nimmaguru.app.databinding.ActivityCalendarBinding
import com.google.firebase.firestore.FirebaseFirestore

class CalendarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalendarBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvDaySessions.layoutManager = LinearLayoutManager(this)

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val date = "$dayOfMonth/${month + 1}/$year"
            loadSessionsForDate(date)
        }
    }

    private fun loadSessionsForDate(date: String) {
        db.collection("sessions")
            .whereEqualTo("date", date)
            .get().addOnSuccessListener { snapshot ->
                // Update list
            }
    }
}
