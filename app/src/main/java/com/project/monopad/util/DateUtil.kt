package com.project.monopad.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil{
    private val simpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)

    fun getDayDifference(releaseDate: String): String {
        val date = simpleDateFormat.parse(releaseDate)
        val today = Calendar.getInstance()
        val diffDate = (today.time.time - date.time) / (60 * 60 * 24 * 1000)
        return when {
            diffDate > 0 -> {
                "+$diffDate"
            }
            diffDate < 0 -> {
                diffDate.toString()
            }
            else -> {
                "-Day"
            }
        }
    }

    fun convertDateToString(date : Date) : String
        = simpleDateFormat.format(date)
}