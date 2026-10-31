package com.samuel.soundin.ui.models

import kotlin.time.Duration

data class Song(
    val id: Int,
    val title: String,
    val artist: String,
    val durationSeconds: Int,
    val playlistId: Int
)
