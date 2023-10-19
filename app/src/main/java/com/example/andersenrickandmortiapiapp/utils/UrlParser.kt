package com.example.andersenrickandmortiapiapp.utils

object UrlParser {
    fun getIdFromUrl(url: String): Int {
        return url.split("/").last().toInt()
    }
}