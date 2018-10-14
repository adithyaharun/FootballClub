package com.adithyaharun.footballclub.UI.Presenter

import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.EventResponse
import com.adithyaharun.footballclub.NetworkService.ApiRepository
import com.adithyaharun.footballclub.NetworkService.TheSportsDBApi
import com.adithyaharun.footballclub.UI.MainView
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson) {

    fun getLastMatches() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDBApi.getLastMatch()),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchList(data.events as List<Event>)
            }
        }
    }

    fun getNextMatches() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDBApi.getNextMatch()),
                    EventResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showMatchList(data.events as List<Event>)
            }
        }
    }
}