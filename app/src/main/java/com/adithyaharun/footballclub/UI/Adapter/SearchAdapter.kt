package com.adithyaharun.footballclub.UI.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.adithyaharun.footballclub.Model.Event
import com.adithyaharun.footballclub.Model.Team
import org.jetbrains.anko.find

class SearchAdapter(val context: Context, val data: List<Any>, private val listener: (Any) -> Unit):
        RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, view, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(data: Any, listener: (Any) -> Unit) {
            val textView: TextView = view.find(android.R.id.text1)

            textView.setOnClickListener {
                listener(data)
            }

            try {
                val event = data as Event
                textView.text = "${event.strHomeTeam} vs ${event.strAwayTeam}"
            } catch (e: Exception) {
                Log.d("EXCEPTION", e.message)

                val team = data as Team
                textView.text = "${team.strTeam}"
            }
        }
    }
}
