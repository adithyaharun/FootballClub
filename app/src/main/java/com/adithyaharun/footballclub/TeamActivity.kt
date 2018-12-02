package com.adithyaharun.footballclub

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.UI.Fragment.TeamDetailFragment
import com.adithyaharun.footballclub.UI.Fragment.TeamPlayersFragment
import com.adithyaharun.footballclub.UI.Presenter.TeamPresenter
import kotlinx.android.synthetic.main.activity_team.*

class TeamActivity : AppCompatActivity() {
    private val teamDetailFragment = TeamDetailFragment()
    private val teamPlayersFragment = TeamPlayersFragment()
    var teamId: String? = null

    lateinit var team: Team
    lateinit var presenter: TeamPresenter

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_team_details -> {
                showFragment(teamDetailFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_team_players -> {
                showFragment(teamPlayersFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        team = intent.getParcelableExtra("team")
        teamId = team.idTeam

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = team.strTeam

        showFragment(teamDetailFragment)
    }

    private fun showFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, fragment!!)
                .addToBackStack(null)
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
