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

    /**
     * If dialog events are relevant then invoke this method i.e. in onCreate() to make sure dialogs always gets reattached
     *
     * Detach is not needed as viewmodel will go away with dialog fragment
     */
    fun attach() = fragmentManager.findFragmentByTag(dialogTag)?.let(this::observeDialog)

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

    companion object {

        fun FragmentActivity.dialogDisplayer(
            dialogBlueprint: DialogBlueprint,
            dialogTag: String = DEFAULT_DIALOG_TAG,
            onDialogEvent: ((DialogEvent) -> Unit)? = null
        ): DialogDisplayer = DialogDisplayer(
            fragmentManager = supportFragmentManager,
            dialogBlueprint = dialogBlueprint,
            dialogTag = dialogTag,
            onDialogEvent = onDialogEvent
        )

        fun Fragment.dialogDisplayer(
            dialogBlueprint: DialogBlueprint,
            dialogTag: String = DEFAULT_DIALOG_TAG,
            onDialogEvent: ((DialogEvent) -> Unit)? = null
        ): DialogDisplayer = DialogDisplayer(
            fragmentManager = childFragmentManager,
            dialogBlueprint = dialogBlueprint,
            dialogTag = dialogTag,
            onDialogEvent = onDialogEvent
        )

    }

}

/**
 * Java compatibility utils
 */
class DialogDisplayerBuilder private constructor(private val fragmentManager: FragmentManager) {

    private var _dialogTag = DEFAULT_DIALOG_TAG
    fun setDialogTag(dialogTag: String): DialogDisplayerBuilder = apply {
        _dialogTag = dialogTag
    }

    private var _onDialogEvent: ((DialogEvent) -> Unit)? = null
    fun setOnDialogEventAction(onDialogEvent: DialogEventAction) = apply {
        _onDialogEvent = onDialogEvent::onDialogEvent
    }

    fun buildWith(dialogBlueprint: DialogBlueprint): DialogDisplayer {
        return DialogDisplayer(
            fragmentManager = fragmentManager,
            dialogBlueprint = dialogBlueprint,
            dialogTag = _dialogTag,
            onDialogEvent = _onDialogEvent
        )
    }

    companion object {

        @JvmStatic
        fun dialogDisplayerBuilderFrom(activity: FragmentActivity): DialogDisplayerBuilder =
            DialogDisplayerBuilder(activity.supportFragmentManager)

        @JvmStatic
        fun dialogDisplayerBuilderFrom(fragment: Fragment): DialogDisplayerBuilder =
            DialogDisplayerBuilder(fragment.childFragmentManager)

    }

    interface DialogEventAction {
        fun onDialogEvent(dialogEvent: DialogEvent)
    }

}
