package com.example.lprogroceries;

import java.util.ArrayList;
import java.util.List;

import com.example.lprogroceries.db.model.Object;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class AddItemDialog extends DialogFragment {

	private CharSequence[] items;
	
	static AddItemDialog newInstance(CharSequence [] items){
		AddItemDialog a = new AddItemDialog();
		
		Bundle args = new Bundle();
		args.putCharSequenceArray("remainingList", items);
		a.setArguments(args);
		
		return a;
	}

	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	
		 //items = getArguments().getCharSequenceArray("remainingList");

    	
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        

        
        builder.setMultiChoiceItems(R.array.list, null, null).setPositiveButton(R.string.ok, null).setNegativeButton(R.string.cancel, null);
       
        return builder.create();
    }
    

}