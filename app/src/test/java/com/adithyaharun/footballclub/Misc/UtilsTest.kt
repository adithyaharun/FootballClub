package com.adithyaharun.footballclub.Misc

import org.junit.Test

import org.junit.Assert.*

class UtilsTest {

    @Test
    fun testToHumanDate() {
        val sqlDate = "2018-01-01"
        val humanDate = Utils.toHumanDate(sqlDate)

        assertTrue(humanDate == "Monday, 01 January 2018")
    }
}