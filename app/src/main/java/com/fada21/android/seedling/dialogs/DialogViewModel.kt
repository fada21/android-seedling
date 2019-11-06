package com.fada21.android.seedling.dialogs

import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {
    var dialogBlueprint: DialogBlueprint? = null
    var onDialogEvent: (DialogEvent) -> Unit = {}
}

sealed class DialogEvent {
    object Shown : DialogEvent()
    object Positive : DialogEvent()
    object Negative : DialogEvent()
    object Cancelled : DialogEvent()
    object Dismissed : DialogEvent()
    data class OnSingleItemSelected(val position: Int) : DialogEvent()
}