package com.reinventiva.sticket.ui.newticketnumber

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.NumberData


class NewTicketNumberRecyclerAdapter(
    private val context: Context,
    private val list: List<NumberData>,
    sections: Array<String>?,
    private val onHasSectionsChange: (hasSections: Boolean) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedPositions = ArrayList<Int>()

    val selectedSections get() = list
        .filterIndexed { index, data -> selectedPositions.contains(index) && !data.HasTicket }
        .map { d -> d.Section }

    init {
        if (sections != null) {
            for ((index, data) in list.withIndex()) {
                if (sections.contains(data.Section))
                    selectedPositions.add(index)
            }
        }
        onHasSectionsChange(selectedSections.isNotEmpty())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(context).inflate(R.layout.new_ticket_number_item, parent, false)
        return MyViewHolder(rootView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list.get(position)
        if (holder is MyViewHolder) {
            holder.textViewSection.text = data.Section
            holder.checkBoxHasNumber.isChecked = data.HasTicket
            holder.textViewWaiting.text = (data.LastNumber - data.CurrentNumber).toString()
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
                onHasSectionsChange(selectedSections.isNotEmpty())
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