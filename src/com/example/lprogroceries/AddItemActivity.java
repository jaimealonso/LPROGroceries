package com.example.lprogroceries;

import java.io.File;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lprogroceries.db.helper.*;
import com.example.lprogroceries.db.model.Object;


public class AddItemActivity extends Activity{
	
    private static final int CAPTURE_IMAGE = 100;
	private static final int SELECT_PHOTO = 200;
	private String photo_uri = null;
	private int list_objectsId;

	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
	    setContentView(R.layout.activity_additem); 
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    Intent intent = getIntent();
	    list_objectsId = intent.getIntExtra("list", 1);
	    
	    final EditText name_field = (EditText) findViewById(R.id.name_field);
	    
        Button camera_btn = (Button) findViewById(R.id.camera_btn);
        camera_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				path.mkdirs();
				File pic = new File(path, new Date().getTime()+".jpg");
				Uri photoUri = Uri.fromFile(pic);
				photo_uri = photoUri.getPath();
				//Toast toast = Toast.makeText(getApplicationContext(), photo_uri.getPath(), Toast.LENGTH_LONG);
				//toast.show();
				camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
				startActivityForResult(camera_intent, CAPTURE_IMAGE);
			}
		});
        
        Button gallery_btn = (Button) findViewById(R.id.gallery_btn);
        gallery_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photo_intent = new Intent(Intent.ACTION_PICK);
				photo_intent.setType("image/*");
				startActivityForResult(photo_intent, SELECT_PHOTO);
			}
		});
        
        Button ok_btn = (Button) findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				String name = name_field.getText().toString();
								
				Object temp = new Object(name,photo_uri);
				
				Log.e("LPROGroceries", "Name: "+temp.getName()+" Ref: "+temp.getRef());

				DatabaseHelper db = new DatabaseHelper(getApplicationContext());
				db.createObject(temp);
				
				Object o = db.getObjectByName(name);
				db.createListObject(o.getId(), list_objectsId);
				
				Log.e("LPROGroceries", "Name: "+o.getName()+" Ref: "+o.getRef());

				
				finish();
			}
        	
        });
	    
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
			case CAPTURE_IMAGE:
				if (resultCode == Activity.RESULT_OK){					
			        Bitmap scaledBitmap = scaleDown(BitmapFactory.decodeFile(photo_uri), 500.0f, true);			        
			        ImageView result = (ImageView) findViewById(R.id.preview);     
			        result.setImageBitmap(scaledBitmap);
				}
				break;
			case SELECT_PHOTO:
				if (resultCode == Activity.RESULT_OK){
					Uri selectedPhoto = data.getData();
					
		            String[] filePathColumn = { MediaStore.Images.Media.DATA };
		            Cursor cursor = getContentResolver().query(selectedPhoto,filePathColumn, null, null, null);
		            cursor.moveToFirst();
		            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		            String picturePath = cursor.getString(columnIndex);
		            cursor.close();
		            
		            photo_uri = picturePath;
		            
			        Bitmap scaledBitmap = scaleDown(BitmapFactory.decodeFile(picturePath), 500.0f, true);			        
			        ImageView result = (ImageView) findViewById(R.id.preview);     
			        result.setImageBitmap(scaledBitmap);
				}
		}
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

}
