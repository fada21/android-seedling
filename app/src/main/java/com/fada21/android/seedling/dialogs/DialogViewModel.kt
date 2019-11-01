package com.fada21.android.seedling.dialogs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {

    val liveData = MutableLiveData<DialogEvent>()
    var onDialogEvent: (DialogEvent) -> Unit = {}
    var onSingleItemSelected: (position: Int) -> Unit = {}

}

sealed class DialogEvent {
    object Shown : DialogEvent()
    object Positive : DialogEvent()
    object Negative : DialogEvent()
    object Cancelled : DialogEvent()
    object Dismissed : DialogEvent()
}