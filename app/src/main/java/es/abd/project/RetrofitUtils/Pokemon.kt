package es.abd.project.RetrofitUtils

import androidx.room.Entity

@Entity("pokemon")
data class Pokemon(
    val name: String,
    val url: String
)