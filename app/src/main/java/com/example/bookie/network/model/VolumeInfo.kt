package com.example.bookie.network.model

data class VolumeInfo(
    val authors: List<String>,
    val categories: List<String>,
    val description: String,
    val imageLinks: ImageLinks,
    val language: String,
    val pageCount: Int,
    val publisher: String,
    val subtitle: String,
    val title: String
)