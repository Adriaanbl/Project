package es.abd.project.ChatUtils

import java.util.Date

data class Message(
    val name: String,
    val text: String,
    val time: Long = Date().time
)
