package com.adithyaharun.footballclub.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.adithyaharun.footballclub.Misc.Utils
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.R
import com.adithyaharun.footballclub.R.id.*
import org.jetbrains.anko.find

class MainAdapter(private val context: Context?, private val events: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(context).inflate(R.layout.item_matches, parent, false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount(): Int = events.size
}

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val tvMatchTime: TextView = view.find(match_time)
    private val tvTeamHome: TextView = view.find(team_home)
    private val tvScoreHome: TextView = view.find(score_home)
    private val tvTeamAway: TextView = view.find(team_away)
    private val tvScoreAway: TextView = view.find(score_away)
    private val layoutMatch: LinearLayout = view.find(match_layout)

    fun bindItem(event: Event, listener: (Event) -> Unit) {
        tvMatchTime.text = Utils.toHumanDate(event.dateEvent, "dd MMM")

        tvTeamHome.text = event.strHomeTeam
        tvTeamAway.text = event.strAwayTeam

        tvScoreHome.text = event.intHomeScore.toString()
        tvScoreAway.text = event.intAwayScore.toString()

        if (event.intHomeScore == null) {
            tvScoreHome.visibility = View.GONE
        }

        if (event.intAwayScore == null) {
            tvScoreAway.visibility = View.GONE
        }

        if (event.intHomeScore == null) {
            tvScoreHome.visibility = View.GONE
        }

        layoutMatch.setOnClickListener {
            listener(event)
        }
    }
}

