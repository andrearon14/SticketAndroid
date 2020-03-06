package com.reinventiva.sticket.ui.newticketsuper

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.reinventiva.sticket.ui.newticketnumber.NewTicketNumberActivity
import com.reinventiva.sticket.R
import com.reinventiva.sticket.model.PlaceData

class NewTicketSuperListRecyclerAdapter(private val context: Context, private val list: List<PlaceData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView = LayoutInflater.from(context).inflate(R.layout.new_ticket_super_list_tab_item, parent, false)
        return MyViewHolder(
            rootView
        ).listen { _, _ ->
            val intent = Intent(context, NewTicketNumberActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list.get(position)
        if (holder is MyViewHolder) {
            holder.textViewName.text = data.Name
            holder.textViewDistance.text = "${data.Distance}m"
        }
    }

    private inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewDistance: TextView = itemView.findViewById(R.id.textViewDistance)

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, NewTicketSuperActivity::class.java)
                context.startActivity(intent)
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