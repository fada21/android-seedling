package com.fada21.android.seedling

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import com.fada21.android.seedling.dialogs.SingleChoiceItemsList

class TestSingleChoiceItemsList() : SingleChoiceItemsList {
    override fun createListAdapter(context: Context): ListAdapter =
        ArrayAdapter<String>(
            context,
            android.R.layout.simple_list_item_single_choice,
            listOf("item 1", "item 2", "item 3", "list 4")
        )

    override val initialSelection: Int = 0

    override fun onItemSelected(position: Int) {}
}