package com.newton.core.utils

import java.util.*

fun formatCurrency(amount: Int?): String {
    if (amount == null) return "$0"

    return when {
        amount >= 1_000_000_000 -> String.format(Locale.US, "$%.1fB", amount / 1_000_000_000.0)
        amount >= 1_000_000 -> String.format(Locale.US, "$%.1fM", amount / 1_000_000.0)
        else -> String.format(Locale.US, "$%,d", amount)
    }
}
