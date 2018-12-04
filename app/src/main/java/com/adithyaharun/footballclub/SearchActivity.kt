package com.adithyaharun.footballclub

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Player
import com.adithyaharun.footballclub.Model.Team
import com.adithyaharun.footballclub.NetworkService.DataRepository
import com.adithyaharun.footballclub.UI.Adapter.SearchAdapter
import com.adithyaharun.footballclub.UI.Presenter.SearchPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity

class SearchActivity : AppCompatActivity() {
    lateinit var state: String
    lateinit var presenter: SearchPresenter
    var searchResults: MutableList<Any> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        state = intent.getStringExtra("state")

        val repository = DataRepository.create()
        presenter = SearchPresenter(this, repository)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = SearchAdapter(this, searchResults) {
            try {
                val row = it as Event
                startActivity<MatchActivity>("event" to row)
            } catch (e: Exception) {
                Log.d("EXCEPTION", e.message)

                val row = it as Team
                startActivity<TeamActivity>("team" to row)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the options menu from XML
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.menu_search).actionView as SearchView).apply {
            this.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            this.isIconified = false

            if (state == "match")
                this.queryHint = "Search matches..."
            else
                this.queryHint = "Search teams..."
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return false
    }

    override fun onNewIntent(intent: Intent?) {
        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent?.action) {
            Log.d("QUERY", intent.getStringExtra(SearchManager.QUERY))
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                presenter.search(query, state)
            }
        }
    }

    fun showLoading() {
        list.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        list.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun showResults(data: List<Any?>?) {
        Log.d("TEST", Gson().toJson(data))
        data?.forEach {
            searchResults.add(it!!)
        }
    }
}
