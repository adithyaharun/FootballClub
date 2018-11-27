package com.adithyaharun.footballclub.UI.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adithyaharun.footballclub.HomeActivity
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.NetworkService.DataRepository
import com.adithyaharun.footballclub.R
import com.adithyaharun.footballclub.TeamActivity
import com.adithyaharun.footballclub.UI.Adapter.TeamAdapter
import com.adithyaharun.footballclub.UI.Presenter.TeamPresenter
import com.adithyaharun.footballclub.UI.TeamView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [TeamFragment.OnListFragmentInteractionListener] interface.
 */
class TeamFragment : Fragment(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var listTeam: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parentActivity = activity as HomeActivity
        val leagueId = parentActivity.leagueIds[parentActivity.selectedLeague]

        adapter = TeamAdapter(context, teams) {
            startActivity(intentFor<TeamActivity>("team" to it))
        }

        listTeam = view.find(R.id.list)
        listTeam.adapter = adapter

        val request = DataRepository.create()
        presenter = TeamPresenter(this, request)

        swipeRefresh = view.find(R.id.swipe_layout)
        swipeRefresh.onRefresh {
            presenter.getTeams(leagueId)
        }

        swipeRefresh.isRefreshing = true
        presenter.getTeams(leagueId)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_teams, container, false)
        return view
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showTeams(data: List<Team>?) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        data?.let { teams.addAll(it) }
        adapter.notifyDataSetChanged()
    }
}
