package com.example.network

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class DisplayAdapter(private val context: Context, private val dataModelArrayList: List<DataModel>) : RecyclerView.Adapter<DisplayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_griditem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = dataModelArrayList[position]

        holder.title.text = item.title
        holder.desc.text = item.desc
    }

    override fun getItemCount(): Int {
        return dataModelArrayList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val desc: TextView = itemView.findViewById(R.id.desc)
    }
}

