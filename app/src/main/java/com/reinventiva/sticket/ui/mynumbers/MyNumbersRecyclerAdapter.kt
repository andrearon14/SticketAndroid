package com.reinventiva.sticket.ui.mynumbers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reinventiva.sticket.R

class MyNumbersRecyclerAdapter(private val context: Context, private val list: List<MyNumbersData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(context).inflate(R.layout.my_numbers_item, parent, false)
        return MyViewHolder(rootView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list.get(position)
        if (holder is MyViewHolder) {
            holder.textViewSectionNumber.text = "${data.sectionName}   ${data.ticketNumber}"
            holder.textViewSuper.text = data.superName
        }
    }

    private class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewSectionNumber: TextView = itemView.findViewById(R.id.textViewSectionNumber)
        val textViewSuper: TextView = itemView.findViewById(R.id.textViewSuper)
    }
}