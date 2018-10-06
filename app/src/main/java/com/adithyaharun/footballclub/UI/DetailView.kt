package com.adithyaharun.footballclub.UI

import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Team

interface DetailView {
    fun showTeam(data: Team, type: String)
}