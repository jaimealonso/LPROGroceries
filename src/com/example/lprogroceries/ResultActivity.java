package com.example.lprogroceries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class ResultActivity extends Activity{

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        //Intent intent = getIntent();
        
        //String photoUri = intent.getStringExtra("PHOTO_URI");
        
        
        //ImageView result = (ImageView) findViewById(R.id.imageView1);
        //result.setImageBitmap(BitmapFactory.decodeFile(photoUri));
        
        /*File imgFile = new  File(photoUri);
        if(imgFile.exists()){

        	Toast toast = Toast.makeText(getApplicationContext(), "IT WORKS! :D", Toast.LENGTH_LONG);
        	toast.show();
            //Bitmap myBitmap = BitmapFactory.decodeFile(photoUri);

            ImageView myImage = (ImageView) findViewById(R.id.imageView1);

            myImage.setImageURI(Uri.fromFile(imgFile));
            //myImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));

        }
        else{
        	Toast toast = Toast.makeText(getApplicationContext(), "IT DOESN'T WORK :(", Toast.LENGTH_LONG);
        	toast.show();
        }*/
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
