package com.adithyaharun.footballclub.Model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(

	@field:SerializedName("player")
	val player: List<Player?>? = null
)