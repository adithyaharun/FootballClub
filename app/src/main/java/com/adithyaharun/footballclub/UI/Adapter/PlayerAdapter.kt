package com.adithyaharun.footballclub.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.adithyaharun.footballclub.Model.Player
import com.adithyaharun.footballclub.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class PlayerAdapter(private val context: Context?, private val players: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_players, parent, false))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    override fun getItemCount(): Int = players.size
}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val layoutMatch: LinearLayout = view.find(R.id.layout)
    private val playerBadge: ImageView = view.find(R.id.image)
    private val playerName: TextView = view.find(R.id.name)
    private val playerPosition: TextView = view.find(R.id.position)

    fun bindItem(player: Player, listener: (Player) -> Unit) {
        playerName.text = player.strPlayer
        playerPosition.text = player.strPosition

        Picasso.get()
                .load(player.strCutout)
                .into(playerBadge)

        layoutMatch.setOnClickListener {
            listener(player)
        }
    }
}