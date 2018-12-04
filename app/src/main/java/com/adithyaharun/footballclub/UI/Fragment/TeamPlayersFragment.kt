package com.adithyaharun.footballclub.UI.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adithyaharun.footballclub.Model.Player
import com.adithyaharun.footballclub.NetworkService.DataRepository
import com.adithyaharun.footballclub.PlayerActivity
import com.adithyaharun.footballclub.R
import com.adithyaharun.footballclub.TeamActivity
import com.adithyaharun.footballclub.UI.Adapter.PlayerAdapter
import com.adithyaharun.footballclub.UI.PlayerView
import com.adithyaharun.footballclub.UI.Presenter.PlayerPresenter
import com.google.gson.Gson
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity

class TeamPlayersFragment : Fragment(), PlayerView {

    private var players: MutableList<Player> = mutableListOf()

    private lateinit var teamId: String
    private lateinit var playersRv: RecyclerView
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_players, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val parent = activity as TeamActivity
        teamId = parent.teamId!!

        adapter = PlayerAdapter(context, players) {
            startActivity<PlayerActivity>("player" to it)
        }

        playersRv = view.find(R.id.list)
        playersRv.adapter = adapter
        playersRv.layoutManager = LinearLayoutManager(context)

        val request = DataRepository.create()
        presenter = PlayerPresenter(this, request)

        presenter.getPlayers(teamId)
    }

    override fun showLoading() {
        //
    }

    override fun hideLoading() {
        //
    }

    override fun showPlayers(data: List<Player>?) {
        players.clear()

        if (data != null) {
            players.addAll(data)
        }

        adapter.notifyDataSetChanged()
    }
}
