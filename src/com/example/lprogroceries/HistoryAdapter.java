package com.example.lprogroceries;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

public class HistoryAdapter extends BaseAdapter implements ListAdapter { 
	private ArrayList<String> list = new ArrayList<String>(); 
	private Context context; 



	public HistoryAdapter(ArrayList<String> list, Context context) { 
		this.list = list; 
		this.context = context; 
	} 

	@Override
	public int getCount() { 
		return list.size(); 
	} 

	@Override
	public Object getItem(int pos) { 
		return list.get(pos); 
	} 

	@Override
	public long getItemId(int pos) { 
		//return list.get(pos).getId();
		//just return 0 if your list items do not have an Id variable.
		return 0;
	} 

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
			view = inflater.inflate(R.layout.row_history, null);
		} 

		//Handle TextView and display string from your list
		TextView listItemText = (TextView)view.findViewById(R.id.history_item_string); 
		listItemText.setText(list.get(position)); 

		//Handle buttons and add onClickListeners

	    ImageButton arrow_btn = (ImageButton) view.findViewById(R.id.arrow_btn);
		arrow_btn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) { 
            //do something
            context.startActivity(new Intent("com.example.ResultActivity"));
        }
    });
		return view; 
	}
}
