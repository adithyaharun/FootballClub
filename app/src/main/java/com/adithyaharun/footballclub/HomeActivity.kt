package com.adithyaharun.footballclub

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.UI.Fragment.LastMatchFragment
import com.adithyaharun.footballclub.UI.Fragment.NextMatchFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val lastMatchFragment = LastMatchFragment()
    private val nextMatchFragment = NextMatchFragment()
    private val fragmentManager = supportFragmentManager

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
        }
        false
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_holder, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        showFragment(lastMatchFragment)
    }
}
