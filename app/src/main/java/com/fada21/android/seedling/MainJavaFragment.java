package com.fada21.android.seedling;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fada21.android.seedling.dialogs.DialogBlueprint;
import com.fada21.android.seedling.dialogs.DialogDisplayer;

import static com.fada21.android.seedling.dialogs.DialogBlueprintBuilder.dialogBlueprintBuilderFor;
import static com.fada21.android.seedling.dialogs.DialogDisplayer.dialogDisplayerFrom;

public class MainJavaFragment extends Fragment {

    private Button dialogButton;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogButton = view.findViewById(R.id.dialog_button);
        dialogButton.setText("Show dialog from fragment in java");
        textView = view.findViewById(R.id.main_text);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DialogBlueprint dialogBlueprint = dialogBlueprintBuilderFor(new TestSingleChoiceItemsList())
                .setPositiveButton("OK")
                .setTitle("test title")
                .build();

        DialogDisplayer dialogDisplayer = dialogDisplayerFrom(this, dialogBlueprint, "java_fragment_dialog");

        dialogDisplayer.observe(dialogEvent -> {
            Log.d("Dialogs", dialogEvent.toString());
            textView.setText("Last event: " + dialogEvent);
        });
        dialogButton.setOnClickListener(v -> dialogDisplayer.display());
    }

}
