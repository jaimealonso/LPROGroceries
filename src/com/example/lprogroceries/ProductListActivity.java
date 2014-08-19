package com.example.lprogroceries;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.lprogroceries.db.helper.*;
import com.example.lprogroceries.db.model.MyList;
import com.example.lprogroceries.db.model.Object;

public class ProductListActivity extends Activity implements PLAdapterListener{
	
	private int list_objectsId;
	private DatabaseHelper db;
	private ListView lView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.activity_list); 
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    db = new DatabaseHelper(getApplicationContext());
	    
	    list_objectsId = 1;
	    
	    List<Object> list = db.getAllObjectsByList(list_objectsId);

	    ProductListAdapter adapter = new ProductListAdapter(list, this);

	    lView = (ListView)findViewById(R.id.listView3);
	    lView.setAdapter(adapter);
	    
        ImageButton plus_button = (ImageButton) findViewById(R.id.imageButton1);
        plus_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent additem_intent = new Intent("com.example.AddItemActivity");
				additem_intent.putExtra("list", list_objectsId);
				startActivity(additem_intent);
				
			}
		});
        
        //db.closeDB();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.basic, menu);

       return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        
        switch(id){
        	case R.id.about:
        		AboutDialog about = new AboutDialog();
        		about.show(getFragmentManager(), null);
        		return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onDelete(int id) {
		db.deleteObjectFromList(id, list_objectsId);
		
		((ProductListAdapter) lView.getAdapter()).setMyList(db.getAllObjectsByList(list_objectsId));
	}
	
	@Override
	public void onResume(){
		super.onResume();
		((ProductListAdapter) lView.getAdapter()).setMyList(db.getAllObjectsByList(list_objectsId));
	}

}
