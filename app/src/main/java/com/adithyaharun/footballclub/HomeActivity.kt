package com.adithyaharun.footballclub

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.adithyaharun.footballclub.UI.Fragment.FavoriteEventFragment
import com.adithyaharun.footballclub.UI.Fragment.LastMatchFragment
import com.adithyaharun.footballclub.UI.Fragment.NextMatchFragment
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.ArrayList

class HomeActivity : AppCompatActivity() {
    private val lastMatchFragment = LastMatchFragment()
    private val nextMatchFragment = NextMatchFragment()
    private val favoriteFragment = FavoriteEventFragment()
    private var currentFragment: Fragment? = null
    private var leagueDialog: AlertDialog? = null

    var leagues: Array<String> = arrayOf<String>()
    var leagueIds: Array<String> = arrayOf<String>()
    var selectedLeague = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_last_match -> {
                showFragment(lastMatchFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_next_match -> {
                showFragment(nextMatchFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite_match -> {
                showFragment(favoriteFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun showFragment(fragment: Fragment?) {
        currentFragment = fragment
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, fragment!!)
                .addToBackStack(null)
                .commit()
    }

    private fun refreshFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction()
                .detach(fragment!!)
                .attach(fragment!!)
                .addToBackStack(null)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        leagues = resources.getStringArray(R.array.league_name)
        leagueIds = resources.getStringArray(R.array.league_id)

        // Set subtitle.
        supportActionBar?.setSubtitle(leagues[selectedLeague])

        // Create league dialog.
        createDialog()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        showFragment(lastMatchFragment)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.home_activity, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.navigation_league) {
            leagueDialog?.show()
        } else if (item?.itemId == R.id.navigation_search) {
            toast("Search")
        }

        return super.onOptionsItemSelected(item)
    }

    private fun createDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.league_dialog, null)
        val listView = dialogView.find(R.id.league_list) as ListView

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_single_choice,
            resources.getStringArray(R.array.league_name)
        )

        dialogBuilder.setView(dialogView)
        dialogBuilder.setNegativeButton("Cancel", { dialog, whichButton ->
            dialog.cancel()
        })
        leagueDialog = dialogBuilder.create()

        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        listView.setItemChecked(0, true)
        listView.setOnItemClickListener { parent, view, position, id ->
            selectedLeague = position
            supportActionBar?.setSubtitle(leagues[selectedLeague])

            refreshFragment(currentFragment)

            leagueDialog?.dismiss()
        }
    }
}
