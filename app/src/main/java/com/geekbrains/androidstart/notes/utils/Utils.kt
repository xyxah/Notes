package com.geekbrains.androidstart.notes.utils

import java.text.SimpleDateFormat
import java.util.*

open class Utils {
    open fun dateToString(date: Date, format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }
}