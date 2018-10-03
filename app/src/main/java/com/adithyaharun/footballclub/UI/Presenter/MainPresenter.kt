package com.adithyaharun.footballclub.UI.Presenter

import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.Model.TeamResponse
import com.adithyaharun.footballclub.NetworkService.ApiRepository
import com.adithyaharun.footballclub.NetworkService.TheSportsDBApi
import com.adithyaharun.footballclub.UI.MainView
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {
    fun getTeamList(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDBApi.getTeams(league)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamList(data.teams as List<Team>)
            }
        }
    }
}