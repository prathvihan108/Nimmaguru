package com.nimmaguru.app.models

data class ThankYouNote(
    val id: String = "",
    val fromStudentId: String = "",
    val toGuruId: String = "",
    val studentName: String = "",
    val message: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
