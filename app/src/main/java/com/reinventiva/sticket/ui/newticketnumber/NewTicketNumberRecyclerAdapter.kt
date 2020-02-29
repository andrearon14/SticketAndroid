package com.reinventiva.sticket.ui.newticketnumber

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reinventiva.sticket.R


class NewTicketNumberRecyclerAdapter(private val context: Context, val list: List<NewTicketNumberData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedPositions = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(context).inflate(R.layout.new_ticket_number_item, parent, false)
        return MyViewHolder(rootView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list.get(position)
        if (holder is MyViewHolder) {
            holder.textViewSection.text = data.Section
            holder.checkBoxHasNumber.isChecked = data.HasNumber
            holder.textViewWaiting.text = data.Waiting.toString()
        }
        holder.itemView.isSelected = selectedPositions.contains(position)
    }

    private inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewSection: TextView = itemView.findViewById(R.id.textViewSection)
        val checkBoxHasNumber: CheckBox = itemView.findViewById(R.id.checkBoxHasNumber)
        val textViewWaiting: TextView = itemView.findViewById(R.id.textViewWaiting)

        init {
            val select = View.OnClickListener {
                if (selectedPositions.contains(layoutPosition))
                    selectedPositions.remove(layoutPosition)
                else
                    selectedPositions.add(layoutPosition)
                notifyItemChanged(layoutPosition)
            }
            checkBoxHasNumber.setOnClickListener {
                checkBoxHasNumber.isChecked = !checkBoxHasNumber.isChecked  // Deshago el cambio
                checkBoxHasNumber.jumpDrawablesToCurrentState()
                select.onClick(it)
            }
            itemView.setOnClickListener(select)
        }
    }
}