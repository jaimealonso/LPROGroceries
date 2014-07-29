package com.example.lprogroceries;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class AddItemDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        builder.setMultiChoiceItems(R.array.list, null, null).setPositiveButton(R.string.ok, null).setNegativeButton(R.string.cancel, null);
       
        return builder.create();
    }
}