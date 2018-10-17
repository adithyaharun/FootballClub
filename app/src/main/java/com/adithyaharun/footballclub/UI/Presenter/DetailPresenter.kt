package com.adithyaharun.footballclub.UI.Presenter

import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.NetworkService.DataRepository
import com.adithyaharun.footballclub.UI.DetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync

class DetailPresenter(private val view: DetailView,
                    private val apiRepository: DataRepository) {

    fun getTeam(id: String, type: String) {
        doAsync {
            apiRepository.getTeamDetail(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result ->
                                view.showTeam(result.teams?.get(0) as Team, type)
                            }
                    )
        }
    }
}