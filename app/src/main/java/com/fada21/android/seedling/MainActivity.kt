package com.fada21.android.seedling

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.fada21.android.seedling.dialogs.Content
import com.fada21.android.seedling.dialogs.DialogBlueprint
import com.fada21.android.seedling.dialogs.Text
import com.fada21.android.seedling.dialogs.dialogDisplayer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
