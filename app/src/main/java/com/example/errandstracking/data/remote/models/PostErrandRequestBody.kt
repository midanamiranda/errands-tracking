package com.example.errandstracking.data.remote.models

data class PostErrandRequestBody(
    val completed: Boolean,
    val title: String,
    val userId: Int
)