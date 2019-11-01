package com.fada21.android.seedling.dialogs

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class NewDialogDisplayer(
    val fragmentManager: FragmentManager,
    val dialogInfo: NewDialogInfo,
    val dialogTag: String = "dialog_tag",
    val lifecycleOwner: LifecycleOwner,
    val onDialogEvent: ((DialogEvent) -> Unit)
) {

    init {
        val dialogFragment = fragmentManager.findFragmentByTag(dialogTag)
        dialogFragment?.let { observeDialog(it) }
    }

    private fun observeDialog(it: Fragment) {
        val vm = ViewModelProviders.of(it)[DialogViewModel::class.java]
        vm.liveData.observe(lifecycleOwner, Observer(onDialogEvent))
        vm.onEvent = onDialogEvent
    }

    fun display() {
        val dialogFragment = NewDialogFragment.invoke(dialogInfo)
        dialogFragment.showNow(fragmentManager, dialogTag)
        observeDialog(dialogFragment)
    }

}

fun FragmentActivity.dialogFragmentDisplayer(
    dialogInfo: NewDialogInfo,
    dialogTag: String = "dialog_tag",
    onDialogEvent: ((DialogEvent) -> Unit)
): NewDialogDisplayer = NewDialogDisplayer(
    fragmentManager = supportFragmentManager,
    dialogInfo = dialogInfo,
    dialogTag = dialogTag,
    lifecycleOwner = this,
    onDialogEvent = onDialogEvent
)

fun Fragment.dialogFragmentDisplayer(
    dialogInfo: NewDialogInfo,
    dialogTag: String = "dialog_tag",
    onDialogEvent: ((DialogEvent) -> Unit)
): NewDialogDisplayer = NewDialogDisplayer(
    fragmentManager = childFragmentManager,
    dialogInfo = dialogInfo,
    dialogTag = dialogTag,
    lifecycleOwner = viewLifecycleOwner,
    onDialogEvent = onDialogEvent
)
