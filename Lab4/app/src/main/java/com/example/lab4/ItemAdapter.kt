package com.example.lab4

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lab4.databinding.RvItemBinding
import kotlinx.android.synthetic.main.rv_item.view.*
import name.ank.lab4.BibDatabase
import name.ank.lab4.Keys
import name.ank.lab4.Types.*
import java.io.InputStream
import java.io.InputStreamReader

class ItemAdapter(private val isInfinite: Boolean, inputStream: InputStream) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private var db: BibDatabase

    init {
        InputStreamReader(inputStream).use {
            db = BibDatabase(it)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context))
        return ItemViewHolder(binding.root)
    }

    override fun getItemCount(): Int = if (isInfinite) Int.MAX_VALUE else db.size()

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val index = if (isInfinite) position % db.size() else position
        val entry = db.getEntry(index)
        val type = entry.type
        val color = when (type) {
            ARTICLE -> R.color.color1
            BOOK -> R.color.color2
            BOOKLET -> R.color.color2
            CONFERENCE -> R.color.color3
            INBOOK -> R.color.color4
            INCOLLECTION -> R.color.color5
            INPROCEEDINGS -> R.color.color6
            MANUAL -> R.color.color7
            MASTERSTHESIS -> R.color.color8
            MISC -> R.color.color9
            PHDTHESIS -> R.color.color10
            PROCEEDINGS -> R.color.color11
            TECHREPORT -> R.color.color12
            UNPUBLISHED -> R.color.color13
            else -> R.color.color14
        }
        val context = holder.itemView.rootView.context

        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, color))
        holder.itemView.rv_item__title.text = "${index + 1}. ${entry.getField(Keys.TITLE)}"
        holder.itemView.rv_item__author.text = entry.getField(Keys.AUTHOR)
        holder.itemView.rv_item__type.text = type.name
        holder.itemView.rv_item__year.text = entry.getField(Keys.YEAR)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
