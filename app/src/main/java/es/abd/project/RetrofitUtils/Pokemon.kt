package es.abd.project.RetrofitUtils

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("pokemon")
data class Pokemon(
    @PrimaryKey val name: String,
    val url: String
)