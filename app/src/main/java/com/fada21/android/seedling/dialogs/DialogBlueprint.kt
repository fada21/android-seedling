package com.fada21.android.seedling.dialogs

import android.content.Context
import android.widget.ListAdapter
import androidx.annotation.StringRes
import com.fada21.android.seedling.dialogs.Text.None

data class DialogBlueprint(
    val content: Content,
    val positiveButton: Text = None,
    val negativeButton: Text = None,
    val title: Text = None
)

sealed class Text {
    data class FromCharSequence(val text: CharSequence) : Text()
    data class FromResource(@StringRes val stringRes: Int) : Text()
    internal object None : Text()

    fun toCharSequence(context: Context): CharSequence? = when (this) {
        is FromCharSequence -> text
        is FromResource -> context.getString(stringRes)
        None -> null
    }
}

sealed class Content {
    data class Message(val text: Text) : Content()
    data class SingleChoice(val list: SingleChoiceItemsList) : Content()
}

interface SingleChoiceItemsList {
    fun createListAdapter(context: Context): ListAdapter
    val initialSelection: Int
    fun onItemSelected(position: Int)
}
