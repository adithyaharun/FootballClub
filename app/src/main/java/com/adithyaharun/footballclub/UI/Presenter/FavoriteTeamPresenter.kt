package com.adithyaharun.footballclub.UI.Presenter

import android.util.Log
import com.adithyaharun.footballclub.Misc.DatabaseOpenHelper
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.UI.MainView
import com.adithyaharun.footballclub.UI.TeamView
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FavoriteTeamPresenter(private val view: TeamView,
                            private val database: DatabaseOpenHelper?) {

    fun getFavoriteTeams() {
        view.showLoading()
        doAsync {
            var data: List<Team>? = null

            val parser = rowParser { id: Int,
                                     idTeam: String,
                                     strTeam: String,
                                     strTeamBadge: String,
                                     strStadium: String,
                                     strStadiumLocation: String ->
                Team(
                        id = id,
                        idTeam = idTeam,
                        strTeam = strTeam,
                        strTeamBadge = strTeamBadge,
                        strStadium = strStadium,
                        strStadiumLocation = strStadiumLocation
                )
            }

            database?.use {
                val result = select("FAVORITE_TEAMS")
                data = result.parseList(parser)
            }

            uiThread {
                view.hideLoading()

                if (data != null) {
                    view.showTeams(data!!)
                }
            }
        }
    }
}