package com.example.lprogroceries;

import java.util.ArrayList;

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

public class ListActivity extends Activity{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.activity_list); 
	    
        //ActionBar actionBar = getActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

	    //generate list
	    ArrayList<String> list = new ArrayList<String>();
	    list.add("CocaCola");
	    list.add("Aquarius");

	    //instantiate custom adapter
	    MyCustomAdapter adapter = new MyCustomAdapter(list, this);

	    //handle listview and assign adapter
	    ListView lView = (ListView)findViewById(R.id.listView3);
	    lView.setAdapter(adapter);
	    
        ImageButton plus_button = (ImageButton) findViewById(R.id.imageButton1);
        plus_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AddItemDialog aidialog = new AddItemDialog();
				aidialog.show(getFragmentManager(), null);
			}
		});
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        	case R.id.history:
        		//startActivity(new Intent(ListActivity.this, HistoryActivity.class));
        		return true;
        //if (id == R.id.about || id == R.id.history) {
            //return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

}
