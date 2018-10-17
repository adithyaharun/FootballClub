package com.adithyaharun.footballclub.Misc

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun toHumanDate(string: String): String {
        val sqlFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val humanFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.US)
        val toDateObject = sqlFormatter.parse(string)

        return humanFormatter.format(toDateObject)
    }
}