package com.fada21.android.seedling

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fada21.android.seedling.dialogs.NewDialogInfo
import com.fada21.android.seedling.dialogs.dialogFragmentDisplayer
import kotlinx.android.synthetic.main.main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog_button.setOnClickListener {
            val singleChoiceItemsList = TestSingleChoiceItemsList()
            val newDialogInfo = NewDialogInfo(
                title = "test title",
//                message = "test message",
                positiveButton = "OK",
                singleChoiceItemsList = singleChoiceItemsList
            )
            dialogFragmentDisplayer(
                dialogInfo = newDialogInfo,
                onDialogEvent = { Log.d("Dialogs", "$it") }
            ).display()
        }
    }
}