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
import com.adithyaharun.footballclub.MatchActivity
import com.adithyaharun.footballclub.Misc.database
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.R.color.colorAccent
import com.adithyaharun.footballclub.UI.Adapter.MainAdapter
import com.adithyaharun.footballclub.UI.MainView
import com.adithyaharun.footballclub.UI.Presenter.FavoritePresenter
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.relativeLayout
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.wrapContent

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BlankFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FavoriteEventFragment : Fragment(), MainView {
    private var events: MutableList<Event> = mutableListOf()

    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var presenter: FavoritePresenter
    private lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listEvent = recyclerView {
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

        adapter = MainAdapter(context, events) {
            startActivity(intentFor<MatchActivity>("event" to it))
        }

        listEvent.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getFavoriteEvents()
        }

        presenter = FavoritePresenter(this, context?.database)
        presenter.getFavoriteEvents()
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showMatchList(data: List<Event>?) {
        swipeRefresh.isRefreshing = false
        events.clear()
        data?.let { events.addAll(it) }
        adapter.notifyDataSetChanged()
    }
}
