package com.example.todo

import android.graphics.Paint
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class Utils{

    companion object{
        fun isTimePastCurrentTime(time: String): Boolean {
            val calendar = Calendar.getInstance()
            val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
            val currentMinute = calendar.get(Calendar.MINUTE)

            val formatter = SimpleDateFormat("hh : mm a", Locale.getDefault())
            val providedTime = formatter.parse(time)

            val providedCalendar = Calendar.getInstance()
            providedCalendar.time = providedTime

            val hour = providedCalendar.get(Calendar.HOUR_OF_DAY)
            val minute = providedCalendar.get(Calendar.MINUTE)

            return if (hour < currentHour) {
                true
            } else if (hour == currentHour) {
                minute < currentMinute
            } else {
                false
            }
        }


    }
}

fun TextView.strikeThrough(){
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.removeStrikeThrough(){
    this.paintFlags = this.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

}