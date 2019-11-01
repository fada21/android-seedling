package com.fada21.android.seedling.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ListAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import java.io.Serializable

private const val dialogInfoKey = "dialog-info"

class NewDialogFragment : DialogFragment() {

    private val blueprint: DialogBlueprint by lazy { arguments?.getSerializable(dialogInfoKey) as DialogBlueprint }

    private val vm by lazy { ViewModelProviders.of(this)[DialogViewModel::class.java] }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        blueprint.run {
            title?.run(builder::setTitle)
            message?.run(builder::setMessage)
            positiveButton?.let {
                builder.setPositiveButton(it) { _, _ -> vm.onDialogEvent(DialogEvent.Positive) }
            }
            singleChoiceItemsList?.run {
                builder.setSingleChoiceItems(
                    createListAdapter(builder.context),
                    initialSelection
                ) { _, position ->
                    vm.onSingleItemSelected(position)
                    onItemSelected(position)
                }
            }
        }

        val alertDialog = builder.create()
        alertDialog.setOnShowListener { vm.onDialogEvent(DialogEvent.Shown) }
        return alertDialog
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

        operator fun invoke(dialogBlueprint: DialogBlueprint): NewDialogFragment {
            return NewDialogFragment().apply {
                arguments = Bundle().apply { putSerializable(dialogInfoKey, dialogBlueprint) }
            }
        }
    }

}

data class DialogBlueprint(
    val title: String? = null,
    val message: String? = null, // mutually exclusive with singleChoiceItemsList
    val positiveButton: String? = null,
    val singleChoiceItemsList: SingleChoiceItemsList? = null
) : Serializable

interface SingleChoiceItemsList : Serializable {
    fun createListAdapter(context: Context): ListAdapter
    val initialSelection: Int
    fun onItemSelected(position: Int)
}
