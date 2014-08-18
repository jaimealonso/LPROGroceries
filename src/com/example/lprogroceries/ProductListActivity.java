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

public class ProductListActivity extends Activity implements AddItemDialogListener, PLAdapterListener{
	
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
	    
	    //generate list
	    List<Object> list = db.getAllObjectsByList(list_objectsId);
	    //List<Object> list = db.getAllObjects();

	    //instantiate custom adapter
	    ProductListAdapter adapter = new ProductListAdapter(list, this);

	    //handle listview and assign adapter
	    lView = (ListView)findViewById(R.id.listView3);
	    lView.setAdapter(adapter);
	    
        ImageButton plus_button = (ImageButton) findViewById(R.id.imageButton1);
        plus_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*List<Object> totalList = db.getAllObjects();
				List<Object> userList = db.getAllObjectsByList(list_objectsId);
				
				
				CharSequence[] items = buildRemainingList(totalList,userList);
				
				
				AddItemDialog aidialog = AddItemDialog.newInstance(items);
				
				aidialog.show(getFragmentManager(), null);*/
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
        getMenuInflater().inflate(R.menu.main, menu);

       return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        switch(id){
        	case R.id.about:
        		AboutDialog about = new AboutDialog();
        		about.show(getFragmentManager(), null);
        		return true;
        	case R.id.history:
        		startActivity(new Intent("com.example.HistoryActivity"));
        		return true;
        //if (id == R.id.about || id == R.id.history) {
            //return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    private CharSequence [] buildRemainingList(List<Object> totalList, List<Object> userList){
    	List<String> remainingList = new ArrayList<String>();
    	
    	for(Object o : totalList){
    		if (!userList.contains(o)){
    			remainingList.add(o.getName());
    		}
    	}
    	
    	return remainingList.toArray(new CharSequence[remainingList.size()]);
    }

	@Override
	public void onReturn(CharSequence[] list) {
		for(CharSequence c : list){
			Object o = db.getObjectByName((String)c);
			db.createListObject(o.getId(), list_objectsId);
		}
		
		((ProductListAdapter) lView.getAdapter()).setMyList(db.getAllObjectsByList(list_objectsId));

	}

	@Override
	public void onDelete(String objectName) {
		long idObject = db.getObjectByName(objectName).getId();
		db.deleteObjectFromList(idObject, list_objectsId);
		
		((ProductListAdapter) lView.getAdapter()).setMyList(db.getAllObjectsByList(list_objectsId));
	}
	
	@Override
	public void onResume(){
		super.onResume();
		((ProductListAdapter) lView.getAdapter()).setMyList(db.getAllObjectsByList(list_objectsId));
	}

}
