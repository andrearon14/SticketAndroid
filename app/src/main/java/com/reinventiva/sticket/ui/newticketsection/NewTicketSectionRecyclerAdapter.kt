package com.reinventiva.sticket.ui.newticketsection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.SectionData

class NewTicketSectionRecyclerAdapter(
    private val context: Context,
    private val list: List<SectionData>,
    private val onHasSectionsChange: (hasSections: Boolean) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedPositions = ArrayList<Int>()

    val selectedSections get() = list
        .filterIndexed { index, _ -> selectedPositions.contains(index) }
        .map { it.Section }

    init {
        onHasSectionsChange(selectedPositions.isNotEmpty())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView =
            LayoutInflater.from(context).inflate(R.layout.new_ticket_section_item, parent, false)
        return MyViewHolder(rootView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list.get(position)
        if (holder is MyViewHolder) {
            holder.textViewSection.text = data.Section
        }
        holder.itemView.isSelected = selectedPositions.contains(position)
    }

    private inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSection: TextView = itemView.findViewById(R.id.textViewSection)

        init {
            itemView.setOnClickListener {
                if (selectedPositions.contains(layoutPosition))
                    selectedPositions.remove(layoutPosition)
                else
                    selectedPositions.add(layoutPosition)
                onHasSectionsChange(selectedPositions.isNotEmpty())
                notifyItemChanged(layoutPosition)
            }
        }
    }
}