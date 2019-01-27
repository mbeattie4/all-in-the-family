package com.example.adam.allinthefamily

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class NamesAdapter : Adapter<NameViewHolder>() {

    data class NameItem(val name: String, val count: Int)

    private val items: MutableList<NameItem> = ArrayList()

    fun setNames(names: List<NameItem>) {
        with(items) {
            clear()
            addAll(names)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): NameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.name_item_view, parent, false)
        return NameViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: NameViewHolder, position: Int) {
        items[position].run { viewHolder.bind(name = name, count = count) }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class NameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = view.findViewById(R.id.name_text)
    private val count: TextView = view.findViewById(R.id.name_count_text)

    @SuppressLint("SetTextI18n")
    fun bind(name: String, count: Int) {
        this.name.text = name
        this.count.text = if (count > 1) "($count)" else ""
    }
}