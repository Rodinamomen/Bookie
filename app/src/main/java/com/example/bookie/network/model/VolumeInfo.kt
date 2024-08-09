package com.example.bookie.network.model

import androidx.room.Embedded

data class VolumeInfo(
    val authors: List<String>,
    val categories: List<String>,
    val description: String,
    @Embedded val imageLinks: ImageLinks,
    val language: String,
    val pageCount: Int,
    val publisher: String,
    val subtitle: String,
    val title: String
)