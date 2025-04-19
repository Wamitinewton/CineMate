package com.newton.core.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

fun formatDateString(dateString: String): String {
    return try {
        val parsedDate: Date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Date.from(java.time.Instant.parse(dateString))
        } else {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }.parse(dateString)!!
        }

        val now = Date()
        val diffMillis = now.time - parsedDate.time
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
        val days = TimeUnit.MILLISECONDS.toDays(diffMillis)

        when {
            minutes < 1 -> "Just now"
            minutes < 60 -> "$minutes minute${if (minutes != 1L) "s" else ""} ago"
            hours < 24 -> "$hours hour${if (hours != 1L) "s" else ""} ago"
            days < 7 -> "$days day${if (days != 1L) "s" else ""} ago"
            days < 365 -> {
                val formatter = SimpleDateFormat("MMM d", Locale.getDefault())
                formatter.format(parsedDate)
            }
            else -> {
                val formatter = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
                formatter.format(parsedDate)
            }
        }
    } catch (e: Exception) {
        dateString.substringBefore("T")
    }
}
