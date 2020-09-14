package com.example.errandstracking.data.remote.models

data class GetAllErrandsResponseItem(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)