package com.reinventiva.sticket.ui.newticketnumber

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reinventiva.sticket.R

class NewTicketNumberRecyclerAdapter(private val context: Context, private val list: List<NewTicketNumberData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(context).inflate(R.layout.new_ticket_number_item, parent, false)
        return MyViewHolder(rootView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list.get(position)
        if (holder is MyViewHolder) {
            holder.textViewSection.text = data.sectionName
            holder.textViewNumber.text = data.ticketNumber
            holder.textViewWait.text = data.wait.toString()
        }
    }

    private class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewSection: TextView = itemView.findViewById(R.id.textViewSection)
        val textViewNumber: TextView = itemView.findViewById(R.id.textViewNumber)
        val textViewWait: TextView = itemView.findViewById(R.id.textViewWait)
    }
}