package com.nimmaguru.app.models

data class Guru(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val photoUrl: String = "",
    val skills: List<String> = listOf(),
    val freeHours: Map<String, String> = mapOf(),
    val village: String = "",
    val pincode: String = "",
    val bio: String = "",
    val thankYouCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)
