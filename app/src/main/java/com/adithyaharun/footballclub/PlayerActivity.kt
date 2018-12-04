package com.adithyaharun.footballclub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.adithyaharun.footballclub.Model.Player
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        player = intent.getParcelableExtra("player")

        bindData()
    }

    private fun bindData() {
        supportActionBar?.title = player.strPlayer

        Log.d("ID", player.idPlayer)

        Picasso.get()
                .load(player.strFanart1 ?: player.strThumb)
                .into(image)

        nationality.text = player.strNationality
        gender.text = player.strGender
        position.text = player.strPosition
        height.text = player.strHeight
        weight.text = player.strWeight
        description.text = player.strDescriptionEN
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
}
