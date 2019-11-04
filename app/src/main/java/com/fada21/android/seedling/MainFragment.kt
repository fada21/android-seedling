package com.fada21.android.seedling

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fada21.android.seedling.dialogs.Content
import com.fada21.android.seedling.dialogs.DialogBlueprint
import com.fada21.android.seedling.dialogs.Text
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
        val dialogBlueprint = DialogBlueprint(
            title = Text.FromCharSequence("test title"),
            positiveButton = Text.FromCharSequence("OK"),
            content = Content.SingleChoice(TestSingleChoiceItemsList())
        )
        val dialogDisplayer = dialogDisplayer(
            dialogBlueprint = dialogBlueprint,
            onDialogEvent = { Log.d("Dialogs", "$it") })
        dialogDisplayer.observe()
        dialog_button.setOnClickListener { dialogDisplayer.display() }
    }

}
