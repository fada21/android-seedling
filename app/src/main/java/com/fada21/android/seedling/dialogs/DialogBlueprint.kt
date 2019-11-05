package com.fada21.android.seedling.dialogs

import android.content.Context
import android.widget.ListAdapter
import androidx.annotation.StringRes
import com.fada21.android.seedling.dialogs.Content.Message
import com.fada21.android.seedling.dialogs.Content.SingleChoice
import com.fada21.android.seedling.dialogs.Text.*
import java.io.Serializable

data class DialogBlueprint(
    val content: Content,
    val positiveButton: Text = None,
    val negativeButton: Text = None,
    val title: Text = None
) : Serializable {

    companion object {
        private const val serialVersionUID: Long = 201911041556
    }
}

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


/**
 * Java compatibility utils
 */
class DialogBlueprintBuilder(private var content: Content) {

    private var positiveButton: Text? = null
    fun setPositiveButton(@StringRes stringRes: Int) = apply {
        positiveButton = FromResource(stringRes)
    }

    fun setPositiveButton(charSequence: CharSequence) = apply {
        positiveButton = FromCharSequence(charSequence)
    }

    private var negativeButton: Text? = null
    fun setNegativeButton(@StringRes stringRes: Int) = apply {
        negativeButton = FromResource(stringRes)
    }

    fun setNegativeButton(charSequence: CharSequence) = apply {
        negativeButton = FromCharSequence(charSequence)
    }

    private var title: Text? = null
    fun setTitle(@StringRes stringRes: Int) = apply {
        title = FromResource(stringRes)
    }

    fun setTitle(charSequence: CharSequence) = apply {
        title = FromCharSequence(charSequence)
    }

    fun build(): DialogBlueprint = DialogBlueprint(
        content = content,
        positiveButton = positiveButton ?: None,
        negativeButton = negativeButton ?: None,
        title = title ?: None
    )

    companion object {

        @JvmStatic
        fun dialogBlueprintBuilderOf(list: SingleChoiceItemsList) =
            DialogBlueprintBuilder(SingleChoice(list))


        @JvmStatic
        fun dialogBlueprintBuilderOf(@StringRes messageStringRes: Int) =
            DialogBlueprintBuilder(Message(FromResource(messageStringRes)))


        @JvmStatic
        fun dialogBlueprintBuilderOf(message: CharSequence) =
            DialogBlueprintBuilder(Message(FromCharSequence(message)))
    }

}
