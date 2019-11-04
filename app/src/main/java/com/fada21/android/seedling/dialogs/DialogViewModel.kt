package com.fada21.android.seedling.dialogs

import androidx.lifecycle.ViewModel

class DialogViewModel(var onDialogEvent: (DialogEvent) -> Unit = {}) : ViewModel()

sealed class DialogEvent {
    object Shown : DialogEvent()
    object Positive : DialogEvent()
    object Negative : DialogEvent()
    object Cancelled : DialogEvent()
    object Dismissed : DialogEvent()
    data class OnSingleItemSelected(val index: Int) : DialogEvent()
}