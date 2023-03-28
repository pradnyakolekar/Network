package com.example.network

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class DisplayAdapter(private val context: Context, private val dataModelArrayList: List<DataModel>) : BaseAdapter() {

        private var layoutInflater: LayoutInflater? = null
        private lateinit var title: TextView
        private lateinit var desc: TextView

        override fun getCount(): Int {
            return dataModelArrayList.size
        }

        override fun getItem(position: Int): Any? {
            return null
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            var convertView = convertView
            if (layoutInflater == null) {
                layoutInflater =
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            }
            if (convertView == null) {
                convertView = layoutInflater!!.inflate(R.layout.layout_griditem, null)
            }

            title = convertView!!.findViewById(R.id.title)
            desc = convertView!!.findViewById(R.id.desc)

            title.setText(dataModelArrayList.get(position).gettitle())
            desc.setText(dataModelArrayList.get(position).getdesc())

            return convertView
        }
}

