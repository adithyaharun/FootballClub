package com.adithyaharun.footballclub.UI.Presenter

import android.util.Log
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Player
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.NetworkService.DataRepository
import com.adithyaharun.footballclub.UI.MainView
import com.adithyaharun.footballclub.UI.PlayerView
import com.adithyaharun.footballclub.UI.TeamView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync

class PlayerPresenter(private val view: PlayerView,
                      private val apiRepository: DataRepository) {

    fun getPlayers(teamId: String) {
        view.showLoading()
        doAsync {
            apiRepository.getPlayers(teamId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                view.hideLoading()
                                view.showPlayers(result.player as List<Player>?)
                            },
                            {
                                view.hideLoading()
                            }
                    )

        }
    }
}