package com.adithyaharun.footballclub.UI.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adithyaharun.footballclub.Model.Team

import com.adithyaharun.footballclub.R
import com.adithyaharun.footballclub.TeamActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_team_detail.*

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamDetailFragment : Fragment() {
    private lateinit var parentActivity: TeamActivity
    private lateinit var team: Team

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentActivity = activity as TeamActivity
        team = parentActivity.team

        team_description.text = team.strDescriptionEN
        Picasso.get().load(team.strTeamBadge).into(team_badge)
    }
}
