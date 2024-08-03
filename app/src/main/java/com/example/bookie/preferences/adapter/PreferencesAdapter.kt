package com.example.bookie.preferences.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorLong
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.R

class PreferencesAdapter(val data: List<String>, val context:Context):RecyclerView.Adapter<PreferencesAdapter.MyHolder>() {
    val selectedItems = mutableSetOf<String>()
    class MyHolder(val row: View): RecyclerView.ViewHolder(row){
        var category= row.findViewById<TextView>(R.id.tv_category)
        var categoryCardView = row.findViewById<CardView>(R.id.cv_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)
        return MyHolder(row)

    }

    override fun getItemCount(): Int {
        return  data.size
    }
    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val item = data[position]
        val selectedColor = ContextCompat.getColor(context, R.color.md_theme_secondary)
        val undoSelectedColor= ContextCompat.getColor(context, androidx.cardview.R.color.cardview_light_background)
        holder.category.text= data[position]
        holder.categoryCardView.setOnClickListener {
            if(selectedItems.contains(item)){
                it.setBackgroundColor(undoSelectedColor)
                holder.category.setTextColor(selectedColor)
                selectedItems.remove(item)
            }else{
                selectedItems.add(item)
                it.setBackgroundColor(selectedColor)
                holder.category.setTextColor(undoSelectedColor)
            }
        }
    }
    fun getSelectedItem(): MutableSet<String>{
        return selectedItems
    }
}