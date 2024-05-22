package com.unan.apianuncios

data class Noticias(
    val title: String,
    val creator: List<String>,
    val description: String,
    val pubDate: String,
    val image_url: String?,
    val source_id: String,
    val country: List<String>,
    val category: List<String>,
)

data class ApiResponse(
    val status: String,
    val totalResults: Int,
    val results: List<Noticias>,
)

lateinit var ListaNoticias: List<Noticias>