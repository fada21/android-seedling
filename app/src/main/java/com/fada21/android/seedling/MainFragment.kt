package com.fada21.android.seedling

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fada21.android.seedling.dialogs.DialogBlueprint
import com.fada21.android.seedling.dialogs.dialogDisplayer
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
            val newDialogInfo = DialogBlueprint(
                title = "test title",
                positiveButton = "OK",
                singleChoiceItemsList = TestSingleChoiceItemsList()
            )
            dialogDisplayer(
                dialogBlueprint = newDialogInfo,
                onDialogEvent = { Log.d("Dialogs", "$it") },
                onSingleItemSelected = { Log.d("Dialogs", "position $it selected") }
            ).display()
        }
    }

}
