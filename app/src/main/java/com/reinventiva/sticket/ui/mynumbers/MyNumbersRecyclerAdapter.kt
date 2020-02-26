package com.reinventiva.sticket.ui.mynumbers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reinventiva.sticket.R

class MyNumbersRecyclerAdapter(private val context: Context, val list: List<MyNumbersData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedPositions = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(context).inflate(R.layout.my_numbers_item, parent, false)
        return MyViewHolder(rootView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list.get(position)
        if (holder is MyViewHolder) {
            holder.textViewSection.text = data.SectionName
            holder.textViewTicket.text = data.TicketNumber
            holder.textViewCurrent.text = data.CurrentNumber
        }
        holder.itemView.isSelected = selectedPositions.contains(position)
    }

    private inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewSection: TextView = itemView.findViewById(R.id.textViewSection)
        val textViewTicket: TextView = itemView.findViewById(R.id.textViewTicket)
        val textViewCurrent: TextView = itemView.findViewById(R.id.textViewCurrent)

        init {
            itemView.setOnClickListener {
                if (selectedPositions.contains(layoutPosition))
                    selectedPositions.remove(layoutPosition)
                else
                    selectedPositions.add(layoutPosition)
                notifyItemChanged(layoutPosition)
            }
        }
    }
}