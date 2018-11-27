package com.adithyaharun.footballclub

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.UI.Fragment.TeamDetailFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.content_team.*
import kotlinx.android.synthetic.main.item_teams.*
import org.jetbrains.anko.find

class TeamActivity : AppCompatActivity() {
    private val teamDetailFragment = TeamDetailFragment()

    lateinit var team: Team

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_team_details -> {
                showFragment(teamDetailFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        team = intent.getParcelableExtra("team")

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
