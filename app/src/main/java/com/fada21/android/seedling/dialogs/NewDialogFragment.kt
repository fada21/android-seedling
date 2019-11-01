package com.fada21.android.seedling.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import java.io.Serializable

private const val dialogInfoKey = "dialog-info"

class NewDialogFragment : DialogFragment() {

    private val info: NewDialogInfo by lazy { arguments?.getSerializable(dialogInfoKey) as NewDialogInfo }
    private var itemSelected: Int = -1

    val vm by lazy { ViewModelProviders.of(this)[DialogViewModel::class.java] }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        info.run {
            title?.run(builder::setTitle)
            message?.run(builder::setMessage)
            positiveButton?.let {
                builder.setPositiveButton(it) { dialog, which ->
                    vm.onEvent(
                        DialogEvent.Positive
                    )
                }
            }
            singleChoiceItemsList?.run {
                builder.setSingleChoiceItems(
                    createListAdapter(builder.context),
                    initialSelection
                ) { _, position ->
                    Toast.makeText(
                        builder.context,
                        "position $position checked",
                        Toast.LENGTH_SHORT
                    ).show()
                    itemSelected = position
                    onItemSelected(position)
                }
            }
        }
        vm
        return builder.create()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        vm.onEvent(DialogEvent.Cancelled)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        vm.onEvent(DialogEvent.Dismissed)
    }


    companion object {

        operator fun invoke(dialogInfo: NewDialogInfo): NewDialogFragment {
            return NewDialogFragment().apply {
                arguments = Bundle().apply { putSerializable(dialogInfoKey, dialogInfo) }
            }
        }
    }

}

data class NewDialogInfo(
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
