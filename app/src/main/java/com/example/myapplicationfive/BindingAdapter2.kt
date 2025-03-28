package com.example.myapplicationfive

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("itemsListType2")
fun setItemsList2(recyclerView: RecyclerView, itemsListType2: List<Item>?) {
    val itemAdapter: ItemAdapter = recyclerView.adapter as ItemAdapter

    if (itemsListType2 != null) {
        itemAdapter.submitList(itemsListType2.toMutableList())
    }
}