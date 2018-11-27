package com.adithyaharun.footballclub.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.adithyaharun.footballclub.Misc.Utils
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.R
import com.adithyaharun.footballclub.R.id.*
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class TeamAdapter(private val context: Context?, private val teams: List<Team>, private val listener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_teams, parent, false))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

    override fun getItemCount(): Int = teams.size
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val layoutMatch: LinearLayout = view.find(team_layout)
    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)
    private val teamLocation: TextView = view.find(team_location)

    fun bindItem(team: Team, listener: (Team) -> Unit) {
        teamName.text = team.strTeam
        teamLocation.text = team.strStadiumLocation ?: "-"
        Picasso.get()
                .load(team.strTeamBadge)
                .into(teamBadge)

        layoutMatch.setOnClickListener {
            listener(team)
        }
    }
}

