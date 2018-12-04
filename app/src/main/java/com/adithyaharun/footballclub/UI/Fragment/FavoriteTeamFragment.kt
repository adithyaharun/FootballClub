package com.adithyaharun.footballclub.UI.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.adithyaharun.footballclub.Misc.database
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.R
import com.adithyaharun.footballclub.TeamActivity
import com.adithyaharun.footballclub.UI.Adapter.TeamAdapter
import com.adithyaharun.footballclub.UI.Presenter.FavoriteTeamPresenter
import com.adithyaharun.footballclub.UI.TeamView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BlankFragment.OnFragmentInteractionListener] interface
 * to handle interaction teams.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FavoriteTeamFragment : Fragment(), TeamView {
    private var teams: MutableList<Team> = mutableListOf()

    private lateinit var list: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var presenter: FavoriteTeamPresenter
    private lateinit var adapter: TeamAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        list = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }
                    }
                }
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TeamAdapter(context, teams) {
            startActivity(intentFor<TeamActivity>("team" to it))
        }

        list.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getFavoriteTeams()
        }

        presenter = FavoriteTeamPresenter(this, context?.database)
        presenter.getFavoriteTeams()
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
