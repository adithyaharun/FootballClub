package com.adithyaharun.footballclub.UI

import com.adithyaharun.footballclub.Model.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeams(data: List<Team>?)
}