package com.adithyaharun.footballclub.Model

import com.google.gson.annotations.SerializedName

data class EventResponse(

	@field:SerializedName("events")
	val events: List<Event?>? = null
)