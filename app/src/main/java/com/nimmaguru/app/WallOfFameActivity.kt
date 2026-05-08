package com.nimmaguru.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nimmaguru.app.databinding.ActivityWallOfFameBinding
import com.nimmaguru.app.models.Guru
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class WallOfFameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWallOfFameBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallOfFameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvWallOfFame.layoutManager = LinearLayoutManager(this)

        db.collection("gurus")
            .orderBy("thankYouCount", Query.Direction.DESCENDING)
            .limit(20)
            .get().addOnSuccessListener { snapshot ->
                val topGurus = snapshot.toObjects(Guru::class.java)
                // Set adapter with ranking
            }
    }
}
