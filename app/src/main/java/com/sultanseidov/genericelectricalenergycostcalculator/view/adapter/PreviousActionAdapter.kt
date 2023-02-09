package com.sultanseidov.genericelectricalenergycostcalculator.view.adapter

import android.content.ClipData.Item
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.sultanseidov.genericelectricalenergycostcalculator.R
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.CustomerBillModel


class PreviousActionAdapter : BaseAdapter() {
    private var items: List<CustomerBillModel> = emptyList()

    fun uploadData(items: List<CustomerBillModel>){
        this.items=items
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_view, parent, false)
        val itemText: TextView = view.findViewById(R.id.textItemView)
        val data = items[position]
        itemText.text =
            "CSN: " + data.customerServiceNumber + "  Meters: " + data.customerElectricMeters + " units " + " Total Bill: " + data.customerTotalBillAmount
        return view
    }
}