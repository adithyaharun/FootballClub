package com.adithyaharun.footballclub.UI

import com.adithyaharun.footballclub.Model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
}