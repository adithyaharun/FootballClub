package com.adithyaharun.footballclub.UI.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.R
import com.adithyaharun.footballclub.R.id.*
import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.util.Locale

class MainAdapter(private val context: Context?, private val events: List<Event>, private val listener: (Event) -> Unit)
    : RecyclerView.Adapter<TeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(events[position], listener)
    }

    override fun getItemCount(): Int = events.size
}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                textView {
                    id = R.id.match_time
                    backgroundColor = Color.rgb(230, 230, 230)
                    verticalPadding = dip(4)
                    horizontalPadding = dip(16)
                }.lparams{
                    width = matchParent
                    height = wrapContent
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL
                    id = R.id.match_layout

                    linearLayout {
                        lparams(width = 0, height = wrapContent, weight = 3f)
                        orientation = LinearLayout.VERTICAL
                        padding = dip(16)

                        textView {
                            id = R.id.team_home
                            textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            textColor = Color.BLACK
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }

                        textView {
                            id = R.id.score_home
                            textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                            textColor = Color.BLACK
                            textSize = 18f
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }.setTypeface(null, Typeface.BOLD)
                    }

                    textView {
                        text = "vs"
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textColor = Color.BLACK
                        gravity = Gravity.CENTER_VERTICAL
                    }.lparams {
                        width = 0
                        height = matchParent
                        weight = 1f
                    }

                    linearLayout {
                        lparams(width = 0, height = wrapContent, weight = 3f)
                        orientation = LinearLayout.VERTICAL
                        padding = dip(16)

                        textView {
                            id = R.id.team_away
                            textColor = Color.BLACK
                        }.lparams{
                            width = matchParent
                            height = wrapContent
                        }

                        textView {
                            id = R.id.score_away
                            textSize = 18f
                            textColor = Color.BLACK
                        }.lparams{
                            width = matchParent
                            height = wrapContent
                        }.setTypeface(null, Typeface.BOLD)
                    }
                }
            }
        }
    }
}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val tvMatchTime: TextView = view.find(match_time)
    private val layoutMatch: LinearLayout = view.find(match_layout)
    private val tvTeamHome: TextView = view.find(team_home)
    private val tvScoreHome: TextView = view.find(score_home)
    private val tvTeamAway: TextView = view.find(team_away)
    private val tvScoreAway: TextView = view.find(score_away)
    private val sqlFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val humanFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.US)

    fun bindItem(event: Event, listener: (Event) -> Unit) {
        val matchDate = sqlFormatter.parse(event.dateEvent)
        tvMatchTime.text = humanFormatter.format(matchDate)

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

        layoutMatch.setOnClickListener {
            listener(event)
        }
    }
}

