package com.example.lprogroceries;

import java.io.File;
import java.util.Date;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import com.droid4you.util.cropimage.CropImage;
import com.example.lprogroceries.db.helper.*;
import com.example.lprogroceries.db.model.Object;



public class AddItemActivity extends Activity{
	
    private static final int CAPTURE_IMAGE = 100;
	private static final int SELECT_PHOTO = 200;
	private static final int CROP_PHOTO = 300;
	private String photo_uri = null;
	private int list_objectsId;
	private ImageView image;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
	    setContentView(R.layout.activity_additem); 
	    ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
		image = (ImageView) findViewById(R.id.preview);
		image.setTag(false);
	    
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
								
		        //Drawable result = (Drawable) ((ImageView) findViewById(R.id.preview)).getDrawable();;     

		        //Drawable ic_launcher = getResources().getDrawable(R.drawable.ic_launcher);
		        
		        if("".equals(name) || !(Boolean)image.getTag()){
		        	finish();
		        }
		        else{
				Object temp = new Object(name,photo_uri);
				
				Log.e("LPROGroceries", "Name: "+temp.getName()+" Ref: "+temp.getRef());

				DatabaseHelper db = new DatabaseHelper(getApplicationContext());
				long id = db.createObject(temp);
				
				db.createListObject(id, list_objectsId);
				
				Log.e("LPROGroceries", "Name: "+name+" Ref: "+photo_uri);

				
				finish();
		        }
			}
        	
        });
        
        Button cancel_btn = (Button) findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
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

			        runCropImage(photo_uri);
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
		            
		            runCropImage(photo_uri);

				}
			case CROP_PHOTO:
				if(resultCode == Activity.RESULT_OK){
					
					if(photo_uri == null || data.getExtras() == null)
						return;
					
					Bitmap scaledBitmap = scaleDown(BitmapFactory.decodeFile(photo_uri), 500.0f, true);			        
			        image.setImageBitmap(scaledBitmap);
			        image.setTag(true);
			        
				}
				break;
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
    
    private void runCropImage(String filePath) {

        // create explicit intent
        Intent intent = new Intent(this, CropImage.class);

        // tell CropImage activity to look for image to crop 
        intent.putExtra("image-path", filePath);

        // allow CropImage activity to rescale image
        intent.putExtra("scale", true);
        // if the aspect ratio is fixed to ratio 3/2
        
        // start activity CropImage with certain request code and listen
        // for result
        startActivityForResult(intent, CROP_PHOTO);
    }

}
