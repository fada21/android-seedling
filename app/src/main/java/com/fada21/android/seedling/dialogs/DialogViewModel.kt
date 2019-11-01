package com.fada21.android.seedling.dialogs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {

    val liveData = MutableLiveData<DialogEvent>()
    var onEvent: (DialogEvent) -> Unit = {}


}

sealed class DialogEvent {
    object Positive : DialogEvent()
    object Negative : DialogEvent()
    object Cancelled : DialogEvent()
    object Dismissed : DialogEvent()
}