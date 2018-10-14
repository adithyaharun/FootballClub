package com.adithyaharun.footballclub.UI.Presenter

import android.util.Log
import com.adithyaharun.footballclub.Misc.DatabaseOpenHelper
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.UI.MainView
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FavoritePresenter(private val view: MainView,
                        private val database: DatabaseOpenHelper?) {

    fun getFavoriteEvents() {
        view.showLoading()
        doAsync {
            var data: List<Event>? = null

            val parser = rowParser { id: Int,
                                     idEvent: String?,
                                     dateEvent: String?,
                                     idHomeTeam: String?,
                                     idAwayTeam: String?,
                                     strHomeTeam: String?,
                                     strAwayTeam: String?,
                                     intHomeScore: Int?,
                                     intAwayScore: Int?,
                                     strHomeGoalDetails: String?,
                                     strAwayGoalDetails: String?,
                                     strHomeYellowCards: String?,
                                     strAwayYellowCards: String?,
                                     strHomeRedCards: String?,
                                     strAwayRedCards: String? ->

                Event(
                        id = id,
                        idEvent = idEvent,
                        dateEvent = dateEvent,
                        idHomeTeam = idHomeTeam,
                        idAwayTeam = idAwayTeam,
                        strHomeTeam = strHomeTeam,
                        strAwayTeam = strAwayTeam,
                        intHomeScore = intHomeScore,
                        intAwayScore = intAwayScore,
                        strHomeGoalDetails = strHomeGoalDetails,
                        strAwayGoalDetails = strAwayGoalDetails,
                        strHomeYellowCards = strHomeYellowCards,
                        strAwayYellowCards = strAwayYellowCards,
                        strHomeRedCards = strHomeRedCards,
                        strAwayRedCards = strAwayRedCards
                )
            }

            database?.use {
                val result = select("FAVORITE_EVENTS")
                data = result.parseList(parser)
            }

            Log.d("FOOTBALL", data.toString())

            uiThread {
                view.hideLoading()

                if (data != null) {
                    view.showMatchList(data!!)
                }
            }
        }
    }
}