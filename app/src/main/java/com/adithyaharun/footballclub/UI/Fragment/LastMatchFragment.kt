package com.adithyaharun.footballclub.UI.Fragment

import android.R
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.R.attr.layoutManager
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.adithyaharun.footballclub.DetailActivity
import com.adithyaharun.footballclub.Misc.invisible
import com.adithyaharun.footballclub.Misc.visible
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.Model.TeamResponse
import com.adithyaharun.footballclub.R.color.colorAccent
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import com.adithyaharun.footballclub.NetworkService.ApiRepository
import com.adithyaharun.footballclub.NetworkService.TheSportsDBApi
import com.adithyaharun.footballclub.R.array.league
import com.adithyaharun.footballclub.UI.Adapter.MainAdapter
import com.adithyaharun.footballclub.UI.MainView
import com.adithyaharun.footballclub.UI.Presenter.MainPresenter
import com.google.gson.Gson
import org.jetbrains.anko.support.v4.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BlankFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LastMatchFragment : Fragment(), MainView {
    private var events: MutableList<Event> = mutableListOf()

    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                lparams (width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout{
                        lparams (width = matchParent, height = wrapContent)

                        listEvent = recyclerView {
                            lparams (width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }
                    }
                }
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MainAdapter(context, events) {
            startActivity(intentFor<DetailActivity>("id" to it.idEvent))
        }

        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)

        swipeRefresh.onRefresh {
            presenter.getLastMatches()
        }

        swipeRefresh.isRefreshing = true
        presenter.getLastMatches()
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showMatchList(data: List<Event>) {
        swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
