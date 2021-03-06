package com.adithyaharun.footballclub.UI.Presenter

import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.NetworkService.DataRepository
import com.adithyaharun.footballclub.UI.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync

class MainPresenter(private val view: MainView,
                    private val apiRepository: DataRepository) {

    fun getLastMatches(leagueId: String) {
        view.showLoading()
        doAsync {
            apiRepository.getPrevEvent(leagueId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                view.hideLoading()
                                view.showMatchList(result.events as List<Event>?)
                            },
                            {
                                view.hideLoading()
                            }
                    )

        }
    }

    fun getNextMatches(leagueId: String) {
        view.showLoading()
        doAsync {
            apiRepository.getNextEvent(leagueId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                view.hideLoading()
                                view.showMatchList(result.events as List<Event>)
                            },
                            {
                                view.hideLoading()
                            }
                    )
        }
    }
}