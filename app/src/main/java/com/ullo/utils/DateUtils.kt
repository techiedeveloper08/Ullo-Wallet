package com.ullo.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getDateTime(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        val date = Date()
        return dateFormat.format(date)
    }
}