package com.example.andersenrickandmortiapiapp.utils

object UrlParser {
    fun getIdFromUrl(url: String): String {
        return url.split("/").last()
    }
}