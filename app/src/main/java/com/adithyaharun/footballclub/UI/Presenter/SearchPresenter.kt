package com.adithyaharun.footballclub.UI.Presenter

import android.util.Log
import com.adithyaharun.footballclub.Model.EventResponse
import com.adithyaharun.footballclub.Model.TeamResponse
import com.adithyaharun.footballclub.NetworkService.DataRepository
import com.adithyaharun.footballclub.SearchActivity
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchPresenter(
        private val activity: SearchActivity,
        private val apiRepository: DataRepository
) {
    fun search(query: String, state: String) {
        activity.showLoading()

        if (state == "match") {
            apiRepository.searchEvents(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result: EventResponse ->
                                Log.d("EVENTS", Gson().toJson(result.event))

                                activity.hideLoading()
                                activity.showResults(result.event)
                            },
                            {
                                activity.hideLoading()
                            }
                    )
        } else if (state == "team") {
            apiRepository.searchTeams(query)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result: TeamResponse ->
                                Log.d("TEAMS", Gson().toJson(result.teams))

                                activity.hideLoading()
                                activity.showResults(result.teams)
                            },
                            {
                                activity.hideLoading()
                            }
                    )
        }
    }
}