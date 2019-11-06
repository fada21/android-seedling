package com.fada21.android.seedling.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders

/**
 * Don't use this class directly. Show dialogs via DialogDisplayer
 */
open class SimpleDialogFragment : DialogFragment() {

    protected val vm by lazy { ViewModelProviders.of(this)[DialogViewModel::class.java] }

    protected var initialBlueprint: DialogBlueprint? = null
    private val blueprint: DialogBlueprint? by lazy {
        initialBlueprint?.also { vm.dialogBlueprint = it } ?: vm.dialogBlueprint
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val builder = AlertDialog.Builder(context)
        blueprint?.run {
            title.toCharSequence(context)?.run(builder::setTitle)
            positiveButton.toCharSequence(context)?.let { text ->
                builder.setPositiveButton(text) { _, _ -> onDialogEvent(DialogEvent.Positive) }
            }
            negativeButton.toCharSequence(context)?.let { text ->
                builder.setNegativeButton(text) { _, _ -> onDialogEvent(DialogEvent.Negative) }
            }
            when (content) {
                is Content.Message -> content.text.toCharSequence(context)?.run(builder::setMessage)
                is Content.SingleChoice -> setSingleChoice(content, builder)
            }
        } ?: dismissAllowingStateLoss()

        val alertDialog = builder.create()
        if (blueprint != null) alertDialog.setOnShowListener { onDialogEvent(DialogEvent.Shown) }
        return alertDialog
    }

    private fun setSingleChoice(
        content: Content.SingleChoice,
        builder: AlertDialog.Builder
    ) = content.list.run {
        builder.setSingleChoiceItems(
            createListAdapter(builder.context),
            initialSelection
        ) { _, position ->
            onDialogEvent(DialogEvent.OnSingleItemSelected(position))
            onItemSelected(position)
        }
    }

    protected open fun onDialogEvent(event: DialogEvent) {
        // no op
    }

    companion object {

        operator fun invoke(dialogBlueprint: DialogBlueprint): SimpleDialogFragment {
            return SimpleDialogFragment().apply {
                initialBlueprint = dialogBlueprint
            }
        }
    }
}
