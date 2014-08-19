package com.example.lprogroceries;

import java.util.ArrayList;
import java.util.List;

import com.example.lprogroceries.db.helper.DatabaseHelper;
import com.example.lprogroceries.db.model.Capture;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class HistoryActivity extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.activity_history); 
	    
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
	    
	    List<Capture> allCaptures = db.getAllCaptures();
	    
	    //instantiate custom adapter
	    HistoryAdapter adapter = new HistoryAdapter((ArrayList<Capture>) allCaptures, this);

	    //handle listview and assign adapter
	    ListView lView = (ListView)findViewById(R.id.listView4);
	    lView.setAdapter(adapter);
	   
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
    
}
