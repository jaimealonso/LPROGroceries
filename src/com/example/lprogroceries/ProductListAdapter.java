package com.example.lprogroceries;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.lprogroceries.db.model.Object;


public class ProductListAdapter extends BaseAdapter implements ListAdapter { 
	private List<Object> list; 
	private Context context; 



	public ProductListAdapter(List<Object> list, Context context) { 
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
	
	public void setMyList(List<Object> list){
		this.list = list;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
			view = inflater.inflate(R.layout.row_list, null);
		} 

		//Handle TextView and display string from your list
		TextView listItemText = (TextView)view.findViewById(R.id.list_item_string); 
		listItemText.setText(list.get(position).getName()); 

		//Handle buttons and add onClickListeners
		ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.delete_btn);

		deleteBtn.setOnClickListener(new View.OnClickListener(){
	        @Override
	        public void onClick(View v) { 
	            PLAdapterListener activity = (PLAdapterListener) context;
	            activity.onDelete(list.get(position).getName());
	        }
	    });

		return view; 
	}
}
