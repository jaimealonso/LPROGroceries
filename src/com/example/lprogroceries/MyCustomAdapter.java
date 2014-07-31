package com.example.lprogroceries;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.lprogroceries.db.model.Object;


public class MyCustomAdapter extends BaseAdapter implements ListAdapter { 
	private List<Object> list; 
	private Context context; 



	public MyCustomAdapter(List<Object> list, Context context) { 
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
			view = inflater.inflate(R.layout.row_list, null);
		} 

		//Handle TextView and display string from your list
		TextView listItemText = (TextView)view.findViewById(R.id.list_item_string); 
		listItemText.setText(list.get(position).getName()); 

		//Handle buttons and add onClickListeners
		/*Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
    Button addBtn = (Button)view.findViewById(R.id.add_btn);

    deleteBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) { 
            //do something
            list.remove(position); //or some other task
            notifyDataSetChanged();
        }
    });
    addBtn.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) { 
            //do something
            notifyDataSetChanged();
        }
    });*/

		return view; 
	}
}
