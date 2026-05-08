package com.nimmaguru.app.models

data class Session(
    val id: String = "",
    val guruId: String = "",
    val guruName: String = "",
    val subject: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "Samudaya Bhavana",
    val maxStudents: Int = 10,
    val attendees: List<String> = listOf(),
    val village: String = "",
    val pincode: String = "",
    val notes: String = ""
)
