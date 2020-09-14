package com.example.errandstracking.utils

import com.example.errandstracking.data.remote.models.GetAllErrandsResponse
import com.google.gson.Gson
import java.io.File

const val getAllErrandsResponsePath = "get_all_errands_response.json"
const val getAllErrandsEmptyResponsePath = "get_all_errands_empty_response.json"

object ErrandsServiceTestUtils {
    fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }

    fun getAllErrandsResponseTestObject() : GetAllErrandsResponse {
        val gson = Gson()
        return gson.fromJson(getJson(getAllErrandsResponsePath), GetAllErrandsResponse::class.java)
    }
}