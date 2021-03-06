package com.reinventiva.sticket.ui.mynumbers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reinventiva.sticket.R
import com.reinventiva.sticket.Utils
import com.reinventiva.sticket.model.NumberData

class MyNumbersRecyclerAdapter(
    private val context: Context,
    private val list: List<NumberData>,
    private val onHasSectionsChange: (hasSections: Boolean) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var selectedPositions = ArrayList<Int>()

    val selectedPlaceSections get() = list
        .filterIndexed { index, _ -> selectedPositions.contains(index) }
        .groupBy({ it.PlaceId }, { it.Section })

    init {
        onHasSectionsChange(selectedPlaceSections.isNotEmpty())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(context).inflate(R.layout.my_numbers_item, parent, false)
        return MyViewHolder(rootView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list.get(position)
        if (holder is MyViewHolder) {
            holder.textViewSection.text = data.Section
            holder.textViewTicket.text = Utils.SerialNumber(data.TicketNumber)
            holder.textViewCurrent.text = Utils.SerialNumber(data.CurrentNumber)
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
                onHasSectionsChange(selectedPlaceSections.isNotEmpty())
                notifyItemChanged(layoutPosition)
            }
        }
    }
}