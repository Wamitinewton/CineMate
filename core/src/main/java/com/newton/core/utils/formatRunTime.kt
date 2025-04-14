package com.newton.core.utils

fun formatRuntime(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return if (hours > 0) {
        "${hours}h ${remainingMinutes}m"
    } else {
        "${remainingMinutes}m"
    }
}