package com.example.bookie.network.model

data class BooksResponse(
    val docs: List<Doc>,
    val numFound: Int,
    val numFoundExact: Boolean,
    val num_found: Int,
    val offset: Any,
    val q: String,
    val start: Int
)