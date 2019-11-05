package com.fada21.android.seedling.dialogs

import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders


/**
 * Don't use this class directly. Show dialogs via DialogDisplayer
 */
class ObservableDialogFragment : SimpleDialogFragment() {

    private val vm by lazy { ViewModelProviders.of(this)[DialogViewModel::class.java] }

    override fun onDialogEvent(event: DialogEvent) {
        vm.onDialogEvent(event)
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

        operator fun invoke(dialogBlueprint: DialogBlueprint): ObservableDialogFragment {
            return ObservableDialogFragment().apply { with(dialogBlueprint) }
        }
    }
}
