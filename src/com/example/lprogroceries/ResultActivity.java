package com.example.lprogroceries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.android.OpenCVLoader;

import com.example.lprogroceries.db.helper.DatabaseHelper;
import com.example.lprogroceries.db.model.Capture;
import com.example.lprogroceries.db.model.MyList;
import com.example.lprogroceries.db.model.Object;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class ResultActivity extends Activity{

	private Capture currentCapture;
	private DatabaseHelper db;
	private ShareActionProvider mShareActionProvider;
	private List<Object> foundObjects, missingObjects;
	
	static{
		if(!OpenCVLoader.initDebug()){
			Log.e("LPROGroceries", "error loading opencv");
		}
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        db = new DatabaseHelper(getApplicationContext());
        
        Intent intent = getIntent();
        
        String previousActivity = intent.getStringExtra("Activity");
        
        String photoUri = null;
        
        //List<Object> foundObjects = null, missingObjects = null;
        
        if("main".equals(previousActivity)){
        
	        photoUri = intent.getStringExtra("PHOTO_URI");
	        
	        ////////////////////DECODING PART//////////////////////
	        ImageDecode image = new ImageDecode(db.getAllObjectsByList(1), photoUri);
	        foundObjects = image.getFoundObjects();
	        missingObjects = getMissingObjects(foundObjects);
	        //////////////////////////////////////////////////////
	        
	        MyList listFound = new MyList("found", (LinkedList<Object>) foundObjects);
	        MyList listMissing = new MyList("missing", (LinkedList<Object>) missingObjects);
	        
	        int idFoundList = (int) db.createList(listFound);
	        int idMissingList = (int) db.createList(listMissing);
	        
	    	currentCapture = new Capture(idFoundList, idMissingList, photoUri, null);
	    	db.createCapture(currentCapture);
	    	
	    	Log.e("LPROGROCERIES", "Detection finished, I will show you the results");
	    	
	        
        }
        else if("history".equals(previousActivity)){
        	
        	int idHistory = intent.getIntExtra("ID_HISTORY", 0);
        	Log.e("LPROGROCERIES", "Idhistory: "+idHistory);
        	currentCapture = db.getCapture(idHistory);
        	
        	photoUri = currentCapture.getRef();
        	
        	Log.e("LPROGROCERIES", "IdFound: "+currentCapture.getIdFoundList()+" IdMissing: "+currentCapture.getIdMissingList());
        	
    	    foundObjects = db.getAllObjectsByList(currentCapture.getIdFoundList());
    	    missingObjects = db.getAllObjectsByList(currentCapture.getIdMissingList());
    	    
    	    Log.e("LPROGROCERIES", "Sizef: "+foundObjects.size()+" Sizem: "+missingObjects.size());

        	
        }
	    
        Bitmap scaledBitmap = scaleDown(BitmapFactory.decodeFile(photoUri), 500.0f, true);
        
        ImageView result = (ImageView) findViewById(R.id.imageView1);
        
        
        result.setImageBitmap(scaledBitmap);
        
        ListView found = (ListView) findViewById(R.id.listView1);
        ListView missing = (ListView) findViewById(R.id.listView2);
        
        ArrayAdapter<Object> foundAdapter = new ArrayAdapter<Object>(this, 
        		android.R.layout.simple_list_item_1,
        		foundObjects);
        
        ArrayAdapter<Object> missingAdapter = new ArrayAdapter<Object>(this, 
        		android.R.layout.simple_list_item_1,
        		missingObjects);
        
        found.setAdapter(foundAdapter);
        missing.setAdapter(missingAdapter);

	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.result, menu);

        MenuItem item = menu.findItem(R.id.share);

        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, buildString());
		sendIntent.setType("text/plain");
		
		mShareActionProvider.setShareIntent(sendIntent);
        
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
        	case R.id.share:
        		return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    private String buildString(){
    	String result = "";
    	
    	result += getResources().getString(R.string.products_found)+": ";
    	
    	for(int i = 0; i < foundObjects.size(); i++){
    		if(i == (foundObjects.size()-1)){
    			result += foundObjects.get(i).getName()+".\n";
    		}
    		else{
    			result += foundObjects.get(i).getName()+", ";
    		}
    	}
    	
    	
    	result += getResources().getString(R.string.products_to_be_bought)+": ";
    	
    	for(int i = 0; i < missingObjects.size(); i++){
    		if(i == (missingObjects.size()-1)){
    			result += missingObjects.get(i).getName()+".\n";
    		}
    		else{
    			result += missingObjects.get(i).getName()+", ";
    		}
    	}
    	
    	return result;
    }
    
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
    
    private LinkedList<Object> getMissingObjects(List<Object> listFoundObjects){
    	LinkedList<Object> list = new LinkedList<Object>();
    	
    	List<Object> userList = db.getAllObjectsByList(1);
    	
    	for(Object o : userList){
    		if(!listFoundObjects.contains(o)){
    			list.add(o);
    			Log.e("LPROGROCERIES", "missing: "+o.getName());
    		}
    		else
    			Log.e("LPROGROCERIES", "found: "+o.getName());
    	}
    	
    	return list;
    }

}
