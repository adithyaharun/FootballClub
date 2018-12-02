package com.adithyaharun.footballclub.UI.Presenter

import android.util.Log
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Player
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.NetworkService.DataRepository
import com.adithyaharun.footballclub.UI.MainView
import com.adithyaharun.footballclub.UI.TeamView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync

class TeamPresenter(private val view: TeamView,
                    private val apiRepository: DataRepository) {

    fun getTeams(leagueId: String) {
        view.showLoading()
        doAsync {
            apiRepository.getTeams(leagueId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                view.hideLoading()
                                view.showTeams(result.teams as List<Team>?)
                            },
                            {
                                view.hideLoading()
                            }
                    )

        }
    }
}