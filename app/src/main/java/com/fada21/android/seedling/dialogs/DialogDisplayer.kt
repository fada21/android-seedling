package com.fada21.android.seedling.dialogs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders

private const val DEFAULT_DIALOG_TAG = "dialog_tag"

class DialogDisplayer(
    private val fragmentManager: FragmentManager,
    private val dialogBlueprint: DialogBlueprint,
    private val dialogTag: String = DEFAULT_DIALOG_TAG,
    private val onDialogEvent: ((DialogEvent) -> Unit)? = null
) {

    fun observe() = fragmentManager.findFragmentByTag(dialogTag)?.let(this::observeDialog)

    private fun observeDialog(fragment: Fragment) {
        if (onDialogEvent != null) {
            val vm = ViewModelProviders.of(fragment)[DialogViewModel::class.java]
            vm.onDialogEvent = onDialogEvent
        }
    }

    fun display() {
        val dialogFragment = ReattachingDialogFragment(dialogBlueprint)
        dialogFragment.showNow(fragmentManager, dialogTag)
        observeDialog(dialogFragment)
    }

}

@JvmName("dialogDisplayerFrom")
fun FragmentActivity.dialogDisplayer(
    dialogBlueprint: DialogBlueprint,
    dialogTag: String = DEFAULT_DIALOG_TAG,
    onDialogEvent: ((DialogEvent) -> Unit) = {}
): DialogDisplayer = DialogDisplayer(
    fragmentManager = supportFragmentManager,
    dialogBlueprint = dialogBlueprint,
    dialogTag = dialogTag,
    onDialogEvent = onDialogEvent
)

@JvmName("dialogDisplayerFrom")
fun Fragment.dialogDisplayer(
    dialogBlueprint: DialogBlueprint,
    dialogTag: String = DEFAULT_DIALOG_TAG,
    onDialogEvent: ((DialogEvent) -> Unit) = {}
): DialogDisplayer = DialogDisplayer(
    fragmentManager = childFragmentManager,
    dialogBlueprint = dialogBlueprint,
    dialogTag = dialogTag,
    onDialogEvent = onDialogEvent
)
