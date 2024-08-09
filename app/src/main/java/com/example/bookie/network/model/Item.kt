package com.example.bookie.network.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Item(
    @PrimaryKey
    val id: String="",
    @Embedded
    val volumeInfo: VolumeInfo
)