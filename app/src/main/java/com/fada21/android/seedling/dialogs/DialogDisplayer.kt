package com.fada21.android.seedling.dialogs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders

private const val DEFAULT_DIALOG_TAG = "dialog_tag"

class DialogDisplayer(
    private val fragmentManager: FragmentManager,
    private val dialogBlueprint: DialogBlueprint,
    private val dialogTag: String = DEFAULT_DIALOG_TAG
) {

    private var dialogEventListener: ((DialogEvent) -> Unit)? = null

    fun observe(onDialogEvent: (DialogEvent) -> Unit) {
        dialogEventListener = onDialogEvent
        fragmentManager.findFragmentByTag(dialogTag)?.let { fragment ->
            observeDialog(fragment, onDialogEvent)
        }
    }

    fun observe(onDialogEvent: DialogEventListener) {
        dialogEventListener = onDialogEvent::onDialogEvent
        fragmentManager.findFragmentByTag(dialogTag)?.let { fragment ->
            observeDialog(fragment, onDialogEvent::onDialogEvent)
        }
    }

    private fun observeDialog(fragment: Fragment, onDialogEvent: (DialogEvent) -> Unit) {
        val vm = ViewModelProviders.of(fragment)[DialogViewModel::class.java]
        vm.onDialogEvent = onDialogEvent
    }

    fun display() {
        val onDialogEvent = dialogEventListener
        if (onDialogEvent == null) {
            SimpleDialogFragment(dialogBlueprint).showNow(fragmentManager, dialogTag)
        } else {
            val dialogFragment = ObservableDialogFragment(dialogBlueprint)
            dialogFragment.showNow(fragmentManager, dialogTag)
            observeDialog(dialogFragment, onDialogEvent)
        }
    }

    companion object {

        @JvmStatic
        @JvmOverloads
        @JvmName("dialogDisplayerFrom")
        fun FragmentActivity.dialogDisplayer(
            dialogBlueprint: DialogBlueprint,
            dialogTag: String = DEFAULT_DIALOG_TAG
        ): DialogDisplayer = DialogDisplayer(
            fragmentManager = supportFragmentManager,
            dialogBlueprint = dialogBlueprint,
            dialogTag = dialogTag
        )

        @JvmStatic
        @JvmOverloads
        @JvmName("dialogDisplayerFrom")
        fun Fragment.dialogDisplayer(
            dialogBlueprint: DialogBlueprint,
            dialogTag: String = DEFAULT_DIALOG_TAG
        ): DialogDisplayer = DialogDisplayer(
            fragmentManager = childFragmentManager,
            dialogBlueprint = dialogBlueprint,
            dialogTag = dialogTag
        )

    }

    interface DialogEventListener {
        fun onDialogEvent(dialogEvent: DialogEvent)
    }

}
