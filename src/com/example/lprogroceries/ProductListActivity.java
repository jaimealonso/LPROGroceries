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

public class ProductListActivity extends Activity{
	
	private int list_objectsId;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.activity_list); 

	    final DatabaseHelper db = new DatabaseHelper(getApplicationContext());
	    	    
	    
	    if(db.getAllObjects().isEmpty()){
		    
	        Object o1 = new Object(1, "CocaCola", "");
	        Object o2 = new Object(2, "Pepsi", "");
	        Object o3 = new Object(3, "Aquarius", "");
	        Object o4 = new Object(4, "Fanta", "");
	        Object o5 = new Object(5, "Kas", "");
	        Object o6 = new Object(6, "Sprite", "");
	        Object o7 = new Object(7, "SevenUp", "");
	        
	        db.createObject(o1);
	        db.createObject(o2);
	        db.createObject(o3);
	        db.createObject(o4);
	        db.createObject(o5);
	        db.createObject(o6);
	        db.createObject(o7);
	        
	    }
	    
	    /*if(db.getAllLists().isEmpty()){
	    	list_objectsId = 1;
	    	MyList list_objects = new MyList(list_objectsId, "user", new LinkedList<Object>());
	    	db.createList(list_objects);
	    }
	    else{
	    	list_objectsId = 1;
	    }*/
	    
	    //generate list
	    //List<Object> list = db.getAllObjectsByList(list_objectsId);
	    List<Object> list = db.getAllObjects();

	    //instantiate custom adapter
	    MyCustomAdapter adapter = new MyCustomAdapter(list, this);

	    //handle listview and assign adapter
	    ListView lView = (ListView)findViewById(R.id.listView3);
	    lView.setAdapter(adapter);
	    
        ImageButton plus_button = (ImageButton) findViewById(R.id.imageButton1);
        plus_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/*List<Object> totalList = db.getAllObjects();
				List<Object> userList = db.getAllObjectsByList(list_objectsId);
				
				
				CharSequence[] items = buildRemainingList(totalList,userList);
				
				
				AddItemDialog aidialog = AddItemDialog.newInstance(items);*/
				
				AddItemDialog aidialog = new AddItemDialog();
				aidialog.show(getFragmentManager(), null);
			}
		});
        
        db.closeDB();
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

}
