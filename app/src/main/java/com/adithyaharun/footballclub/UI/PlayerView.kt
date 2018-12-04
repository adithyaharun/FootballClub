package com.adithyaharun.footballclub.UI

import com.adithyaharun.footballclub.Model.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayers(data: List<Player>?)
}