package com.fada21.android.seedling.dialogs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders

private const val DEFAULT_DIALOG_TAG = "dialog_tag"

class DialogDisplayer(
    private val fragmentManager: FragmentManager,
    private val dialogBlueprint: DialogBlueprint,
    private val dialogTag: String,
    private val onDialogEvent: (DialogEvent) -> Unit,
    private val onSingleItemSelected: (Int) -> Unit
) {

    init {
        fragmentManager.findFragmentByTag(dialogTag)?.let(this::observeDialog)
    }

    private fun observeDialog(fragment: Fragment) {
        val vm = ViewModelProviders.of(fragment)[DialogViewModel::class.java]
        vm.onDialogEvent = onDialogEvent
        vm.onSingleItemSelected = onSingleItemSelected
    }

    fun display() {
        val dialogFragment = NewDialogFragment(dialogBlueprint)
        dialogFragment.showNow(fragmentManager, dialogTag)
        observeDialog(dialogFragment)
    }

}

@JvmName("dialogDisplayerFrom")
fun FragmentActivity.dialogDisplayer(
    dialogBlueprint: DialogBlueprint,
    dialogTag: String = DEFAULT_DIALOG_TAG,
    onDialogEvent: ((DialogEvent) -> Unit) = {},
    onSingleItemSelected: (Int) -> Unit = {}
): DialogDisplayer = DialogDisplayer(
    fragmentManager = supportFragmentManager,
    dialogBlueprint = dialogBlueprint,
    dialogTag = dialogTag,
    onDialogEvent = onDialogEvent,
    onSingleItemSelected = onSingleItemSelected
)

@JvmName("dialogDisplayerFrom")
fun Fragment.dialogDisplayer(
    dialogBlueprint: DialogBlueprint,
    dialogTag: String = DEFAULT_DIALOG_TAG,
    onDialogEvent: ((DialogEvent) -> Unit) = {},
    onSingleItemSelected: (Int) -> Unit = {}
): DialogDisplayer = DialogDisplayer(
    fragmentManager = childFragmentManager,
    dialogBlueprint = dialogBlueprint,
    dialogTag = dialogTag,
    onDialogEvent = onDialogEvent,
    onSingleItemSelected = onSingleItemSelected
)
