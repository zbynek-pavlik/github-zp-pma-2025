package com.example.myapp014asharedtasklist

data class Task(
    var id: String = "",          // Firestore document id
    val title: String = "",
    val completed: Boolean = false
)