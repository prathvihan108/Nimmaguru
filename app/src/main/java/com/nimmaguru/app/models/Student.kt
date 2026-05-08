package com.nimmaguru.app.models

data class Student(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val grade: String = "",
    val village: String = "",
    val pincode: String = "",
    val joinedSessions: List<String> = listOf(),
    val createdAt: Long = System.currentTimeMillis()
)
