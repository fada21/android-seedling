package com.fada21.android.seedling.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders

private const val dialogInfoKey = "dialog-info"

class ReattachingDialogFragment : DialogFragment() {

    private val blueprint: DialogBlueprint by lazy { arguments?.getSerializable(dialogInfoKey) as DialogBlueprint }

    private val vm by lazy { ViewModelProviders.of(this)[DialogViewModel::class.java] }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = requireContext()
        val builder = AlertDialog.Builder(context)
        blueprint.run {
            title.toCharSequence(context)?.run(builder::setTitle)
            positiveButton.toCharSequence(context)?.let { text ->
                builder.setPositiveButton(text) { _, _ -> vm.onDialogEvent(DialogEvent.Positive) }
            }
            negativeButton.toCharSequence(context)?.let { text ->
                builder.setNegativeButton(text) { _, _ -> vm.onDialogEvent(DialogEvent.Negative) }
            }
            when (content) {
                is Content.Message -> content.text.toCharSequence(context)?.run(builder::setMessage)
                is Content.SingleChoice -> setSingleChoice(content, builder)
            }

        }

        val alertDialog = builder.create()
        alertDialog.setOnShowListener { vm.onDialogEvent(DialogEvent.Shown) }
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
            vm.onDialogEvent(DialogEvent.OnSingleItemSelected(position))
            onItemSelected(position)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        vm.onDialogEvent(DialogEvent.Cancelled)
        super.onCancel(dialog)
    }

    override fun onDismiss(dialog: DialogInterface) {
        vm.onDialogEvent(DialogEvent.Dismissed)
        super.onDismiss(dialog)
    }

    companion object {

        operator fun invoke(dialogBlueprint: DialogBlueprint): ReattachingDialogFragment {
            return ReattachingDialogFragment().apply {
                arguments = Bundle().apply { putSerializable(dialogInfoKey, dialogBlueprint) }
            }
        }
    }

}
