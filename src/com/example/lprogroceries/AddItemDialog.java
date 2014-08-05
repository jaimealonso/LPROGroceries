package com.example.lprogroceries;

import java.util.ArrayList;
import java.util.List;

import com.example.lprogroceries.db.model.Object;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;

public class AddItemDialog extends DialogFragment {

	
	private CharSequence [] items;
	private boolean [] checkedItems;
	
	static AddItemDialog newInstance(CharSequence [] items){
		AddItemDialog a = new AddItemDialog();
		
		Bundle args = new Bundle();
		args.putCharSequenceArray("remainingList", items);
		a.setArguments(args);
		
		return a;
	}

	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	
		items = getArguments().getCharSequenceArray("remainingList");
		
		checkedItems = new boolean[items.length];
    	
        Builder builder = new AlertDialog.Builder(getActivity());
        
        Builder b1 = builder.setMultiChoiceItems(items, null, new OnMultiChoiceClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
				checkedItems[arg1] = arg2;
			}
        	
        });
        
        Builder b2 = b1.setNegativeButton(R.string.cancel, null);
        
        Builder b3 = b2.setPositiveButton(R.string.ok, new OnClickListener(){

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				AddItemDialogListener activity = (AddItemDialogListener) getActivity();
				
				List<CharSequence> listResult = new ArrayList<CharSequence>();
				
				for(int i = 0; i < items.length; i++){
					if(checkedItems[i]){
						listResult.add(items[i]);
					}
				}
				
				CharSequence[] result = listResult.toArray(new CharSequence[listResult.size()]);
				
				activity.onReturn(result);
			}
        	
        });
        
       
        return b3.create();
    }
    

}