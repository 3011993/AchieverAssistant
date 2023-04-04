package com.example.achieverassistant.quotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.achieverassistant.R
import com.example.achieverassistant.quotes.data.SelectedItem

class SelectedItemAdapter(context: Context, objects: MutableList<SelectedItem>) :
    ArrayAdapter<SelectedItem>(context, 0, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, _convertView: View?, parent: ViewGroup): View {
        var convertView = _convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context)
                .inflate(R.layout.spinner_item_row, parent, false)

        }
        val imageView = convertView!!.findViewById<ImageView>(R.id.imageview_selected_item)
        val selectedItemText = convertView.findViewById<TextView>(R.id.text_view_selected_item)

        val currentItem = getItem(position)

        if (currentItem != null) {
            imageView.setImageResource(currentItem.avatar)
            selectedItemText.text = currentItem.member
        }

        return convertView

    }
}