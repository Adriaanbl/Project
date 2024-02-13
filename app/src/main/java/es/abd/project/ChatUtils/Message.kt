package es.abd.project.ChatUtils

import java.sql.Timestamp

data class Message(
    val name: String,
    val text: String,
    val time: com.google.firebase.Timestamp
)
