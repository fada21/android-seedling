package com.fada21.android.seedling.dialogs

import androidx.annotation.StringRes
import com.fada21.android.seedling.dialogs.Content.Message
import com.fada21.android.seedling.dialogs.Content.SingleChoice
import com.fada21.android.seedling.dialogs.Text.FromResource

/**
 * Java convenience utils for DialogBlueprint
 */
class DialogBlueprintBuilder(private var content: Content) {

    private var positiveButton: Text? = null
    fun setPositiveButton(@StringRes stringRes: Int) = apply {
        positiveButton = FromResource(stringRes)
    }

    fun setPositiveButton(charSequence: CharSequence) = apply {
        positiveButton = Text.FromCharSequence(charSequence)
    }

    private var negativeButton: Text? = null
    fun setNegativeButton(@StringRes stringRes: Int) = apply {
        negativeButton = FromResource(stringRes)
    }

    fun setNegativeButton(charSequence: CharSequence) = apply {
        negativeButton = Text.FromCharSequence(charSequence)
    }

    private var title: Text? = null
    fun setTitle(@StringRes stringRes: Int) = apply {
        title = FromResource(stringRes)
    }

    fun setTitle(charSequence: CharSequence) = apply {
        title = Text.FromCharSequence(charSequence)
    }

    fun build() = DialogBlueprint(
        content = content,
        positiveButton = positiveButton ?: Text.None,
        negativeButton = negativeButton ?: Text.None,
        title = title ?: Text.None
    )

    companion object {

        @JvmStatic
        fun dialogBlueprintBuilderFor(list: SingleChoiceItemsList) =
            DialogBlueprintBuilder(SingleChoice(list))

        @JvmStatic
        fun dialogBlueprintBuilderFor(@StringRes messageStringRes: Int) =
            DialogBlueprintBuilder(Message(FromResource(messageStringRes)))

        @JvmStatic
        fun dialogBlueprintBuilderFor(message: CharSequence) =
            DialogBlueprintBuilder(Message(Text.FromCharSequence(message)))
    }

}
