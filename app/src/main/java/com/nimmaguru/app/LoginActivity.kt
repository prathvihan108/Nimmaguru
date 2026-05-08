package com.nimmaguru.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nimmaguru.app.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var verificationId: String? = null
    private lateinit var userType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userType = intent.getStringExtra("USER_TYPE") ?: "STUDENT"

        binding.btnGetOtp.setOnClickListener {
            val phone = binding.etPhoneNumber.text.toString()
            if (phone.length == 10) {
                sendOtp("+91$phone")
            } else {
                Toast.makeText(this, "Enter valid 10-digit number", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnVerifyOtp.setOnClickListener {
            val code = binding.etOtp.text.toString()
            verificationId?.let { verifyOtp(it, code) }
        }
    }

    private fun sendOtp(phone: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential)
                }
                override fun onVerificationFailed(e: FirebaseException) {
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
                }
                override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                    verificationId = id
                    binding.layoutOtp.visibility = View.VISIBLE
                    binding.btnVerifyOtp.visibility = View.VISIBLE
                    binding.btnGetOtp.visibility = View.GONE
                }
            }).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyOtp(verificationId: String, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                checkUserStatus()
            } else {
                Toast.makeText(this, "Verification Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUserStatus() {
        val uid = auth.currentUser?.uid ?: return
        val collection = if (userType == "GURU") "gurus" else "students"
        
        db.collection(collection).document(uid).get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                val nextActivity = if (userType == "GURU") GuruDashboardActivity::class.java else StudentHomeActivity::class.java
                startActivity(Intent(this, nextActivity))
                finish()
            } else {
                if (userType == "GURU") {
                    startActivity(Intent(this, GuruProfileSetupActivity::class.java))
                } else {
                    // Redirect to student setup if needed, or home
                    startActivity(Intent(this, StudentHomeActivity::class.java))
                }
                finish()
            }
        }
    }
}
