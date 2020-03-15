package com.reinventiva.sticket.ui.newticketsuper

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.util.TimeUtils
import androidx.recyclerview.widget.RecyclerView
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import com.reinventiva.sticket.R
import com.reinventiva.sticket.Utils
import com.reinventiva.sticket.model.PlaceData
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity.Companion.EXTRA_PLACE
import java.lang.StringBuilder

class NewTicketSuperListRecyclerAdapter(private val context: Context, private val list: List<PlaceData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(context).inflate(R.layout.new_ticket_super_list_tab_item, parent, false)
        return MyViewHolder(rootView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list.get(position)
        if (holder is MyViewHolder) {
            holder.place = data
            holder.textViewName.text = data.Name

            var distanceText = if (data.GoogleDistance != null && data.GoogleTravelTime != null)
                if (data.GoogleTravelTime!! < 5)
                    "En el lugar"
                else
                    "${data.GoogleDistance}, ${Utils.durationToString(data.GoogleTravelTime!!)} en auto"
            else
                "${data.Distance}m"
            if (data.Waiting > 0)
                distanceText += ", ticket ${Utils.durationToString(data.Waiting)}"
            holder.textViewDistance.text = distanceText
        }
    }

    private inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewDistance: TextView = itemView.findViewById(R.id.textViewDistance)
        lateinit var place: PlaceData

        init {
            itemView.setOnClickListener {
                if (context is NewTicketSuperActivity)
                    context.showNewTicketActivity(place)
            }
        }
    }
}

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
    itemView.setOnClickListener {
        event.invoke(adapterPosition, itemViewType)
    }
    return this
}