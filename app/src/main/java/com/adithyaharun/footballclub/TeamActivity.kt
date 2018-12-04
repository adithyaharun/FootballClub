package com.adithyaharun.footballclub

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.adithyaharun.footballclub.Misc.database
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.UI.Fragment.TeamDetailFragment
import com.adithyaharun.footballclub.UI.Fragment.TeamPlayersFragment
import com.adithyaharun.footballclub.UI.Presenter.TeamPresenter
import kotlinx.android.synthetic.main.activity_team.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.toast

class TeamActivity : AppCompatActivity() {
    private val teamDetailFragment = TeamDetailFragment()
    private val teamPlayersFragment = TeamPlayersFragment()

    private var addedToFavorites: Boolean = false
    private var detailMenu: Menu? = null

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
        setFavoriteState()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_event_detail_toolbar, menu)

        val menuFavorites = menu.findItem(R.id.navigation_add_to_favorites)

        if (addedToFavorites) {
            menuFavorites.setIcon(R.drawable.ic_star_black_24dp)
        } else {
            menuFavorites.setIcon(R.drawable.ic_star_border_black_24dp)
        }

        detailMenu = menu
        return super.onCreateOptionsMenu(menu)
    }

    private fun addToFavorites(): Boolean {
        try {
            database.use {
                insert(Team.TABLE_TEAM_FAVORITES,
                        Team.TEAM_ID to team.idTeam,
                        Team.TEAM_ID to team.idTeam,
                        Team.TEAM_NAME to team.strTeam,
                        Team.TEAM_BADGE to team.strTeamBadge,
                        Team.TEAM_STADIUM to team.strStadium,
                        Team.TEAM_STADIUM_LOCATION to team.strStadiumLocation
                )
            }

            toast(R.string.added_to_favorites)
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }

        return true
    }

    private fun removeFromFavorites(): Boolean {
        try {
            database.use {
                delete(Team.TABLE_TEAM_FAVORITES,
                        "TEAM_ID = {id}",
                        "id" to team.idTeam!!
                )
            }
            toast(R.string.removed_from_favorites)
        } catch (e: SQLiteConstraintException) {
            toast(R.string.removed_from_favorites)
        }
        return true
    }

    private fun setFavoriteState() {
        database.use {
            select("FAVORITE_TEAMS", "TEAM_ID")
                    .whereArgs("TEAM_ID = {id}", "id" to team.idTeam.toString())
                    .exec {
                        val parsed = parseOpt(StringParser)

                        addedToFavorites = (parsed != null)
                        onPrepareOptionsMenu(detailMenu)
                    }
        }
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

            R.id.navigation_add_to_favorites -> {
                if (addedToFavorites) {
                    item.setIcon(R.drawable.ic_star_border_black_24dp)
                    removeFromFavorites()
                } else {
                    item.setIcon(R.drawable.ic_star_black_24dp)
                    addToFavorites()
                }

                addedToFavorites = !addedToFavorites

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
