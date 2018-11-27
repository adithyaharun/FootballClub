package com.adithyaharun.footballclub.Misc

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun toHumanDate(string: String?, format: String = "EEEE, dd MMMM yyyy"): String {
        val sqlFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val humanFormatter = SimpleDateFormat(format, Locale.US)
        val toDateObject = sqlFormatter.parse(string)

        return humanFormatter.format(toDateObject)
    }
}