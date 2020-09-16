package com.example.errandstracking.data.remote.models

data class PostErrandResponse(
    val completed: Boolean,
    val id: Int?,
    val title: String,
    val userId: Int
)