package com.fada21.android.seedling

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.fada21.android.seedling.dialogs.Content
import com.fada21.android.seedling.dialogs.DialogBlueprint
import com.fada21.android.seedling.dialogs.DialogDisplayer.Companion.dialogDisplayer
import com.fada21.android.seedling.dialogs.Text
import kotlinx.android.synthetic.main.main.*

class MainKotlinFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.main, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dialogBlueprint = DialogBlueprint(
            title = Text.FromCharSequence("test title"),
            positiveButton = Text.FromResource(android.R.string.ok),
            content = Content.SingleChoice(TestSingleChoiceItemsList())
        )
        val dialogDisplayer = dialogDisplayer(dialogBlueprint) {
            main_text.text = it.toString()
            Log.d("Dialogs", "$it")
        }
        dialogDisplayer.attach()
        dialog_button.setOnClickListener { dialogDisplayer.display() }
    }

}
