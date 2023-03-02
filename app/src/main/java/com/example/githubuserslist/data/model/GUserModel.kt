package com.example.githubuserslist.data.model

import java.text.SimpleDateFormat
import java.util.*


data class GUserModel(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val contributions: Int,
    val company: String,
    val email: String,
    val followers: Int,
    val created_at: String,
    val location: String
) {
    fun getAccountCreation(): Date? {
        return this.created_at.getDate(
            "yyyy-MM-dd'T'HH:mm:ss"
        )
    }
}

fun String.getDate(inputFormat: String): Date? {
    return SimpleDateFormat(inputFormat, Locale.getDefault()).parse(this)
}