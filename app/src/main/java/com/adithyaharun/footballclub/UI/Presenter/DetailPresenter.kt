package com.adithyaharun.footballclub.UI.Presenter

import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.EventResponse
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.Model.TeamResponse
import com.adithyaharun.footballclub.NetworkService.ApiRepository
import com.adithyaharun.footballclub.NetworkService.TheSportsDBApi
import com.adithyaharun.footballclub.UI.DetailView
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {

    fun getEvent(id: String) {
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDBApi.getEvent(id)),
                    EventResponse::class.java
            )

            uiThread {
                view.showEvent(data.events?.get(0) as Event)
            }
        }
    }

    fun getTeam(id: String?, type: String) {
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDBApi.getTeam(id)),
                    TeamResponse::class.java
            )

            uiThread {
                view.showTeam(data.teams?.get(0) as Team, type)
            }
        }
    }
}