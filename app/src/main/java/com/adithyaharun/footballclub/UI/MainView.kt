package com.adithyaharun.footballclub.UI

import com.adithyaharun.footballclub.Model.Event

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showMatchList(data: List<Event>?)
}