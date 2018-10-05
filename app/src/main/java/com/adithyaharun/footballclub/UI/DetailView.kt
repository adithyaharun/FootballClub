package com.adithyaharun.footballclub.UI

import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Team

interface DetailView {
    fun showEvent(data: Event)
    fun showTeam(data: Team, type: String)
}