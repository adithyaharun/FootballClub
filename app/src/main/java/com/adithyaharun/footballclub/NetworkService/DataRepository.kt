package com.adithyaharun.footballclub.NetworkService

import com.adithyaharun.footballclub.Model.EventResponse
import com.adithyaharun.footballclub.Model.PlayerResponse
import com.adithyaharun.footballclub.Model.TeamResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DataRepository {

    @GET("eventspastleague.php")
    fun getPrevEvent(@Query("id") id: String):Observable<EventResponse>

    @GET("eventsnextleague.php")
    fun getNextEvent(@Query("id") id: String):Observable<EventResponse>

    @GET("lookupevent.php")
    fun getEventDetail(@Query("id") id: String):Observable<EventResponse>

    @GET("lookup_all_teams.php")
    fun getTeams(@Query("id") id: String):Observable<TeamResponse>

    @GET("lookupteam.php")
    fun getTeamDetail(@Query("id") id: String):Observable<TeamResponse>

    @GET("lookup_all_players.php")
    fun getPlayers(@Query("id") id: String):Observable<PlayerResponse>

    @GET("searchevents.php")
    fun searchEvents(@Query("e") query: String):Observable<EventResponse>

    @GET("searchteams.php")
    fun searchTeams(@Query("t") query: String):Observable<TeamResponse>


    companion object {
        fun create(): DataRepository {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("https://www.thesportsdb.com/api/v1/json/1/")
                    .build()

            return retrofit.create(DataRepository::class.java)
        }
    }
}
