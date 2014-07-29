package com.example.lprogroceries;

import java.util.ArrayList;

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

	    //generate list
	    ArrayList<String> list = new ArrayList<String>();
	    list.add("01-07-2014");
	    list.add("08-07-2014");
	    list.add("15-07-2014");

	    //String [] values = {"01-07-2014", "08-07-2014", "15-07-2014"};
	    //instantiate custom adapter
	    HistoryAdapter adapter = new HistoryAdapter(list, this);

	    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
	    //handle listview and assign adapter
	    ListView lView = (ListView)findViewById(R.id.listView4);
	    lView.setAdapter(adapter);
	   
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);
        /*super.onCreateOptionsMenu(menu);
        MenuItem history = menu.add(0, 0, 0, R.string.history);
        history.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        MenuItem about = menu.add(0, 1, 1, R.string.about);
        about.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);*/

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
        	case R.id.share:
        		return true;
        //if (id == R.id.about || id == R.id.history) {
            //return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
}
