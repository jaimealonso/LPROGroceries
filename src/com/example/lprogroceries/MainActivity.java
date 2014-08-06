package com.example.lprogroceries;

import java.io.File;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int CAPTURE_IMAGE = 100;
	private static final int SELECT_PHOTO = 200;
	private static Uri photo_uri = null;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button take_picture = (Button) findViewById(R.id.button1);
        take_picture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
				path.mkdirs();
				File pic = new File(path, new Date().getTime()+".jpg");
				photo_uri = Uri.fromFile(pic);
				Toast toast = Toast.makeText(getApplicationContext(), photo_uri.getPath(), Toast.LENGTH_LONG);
				toast.show();
				camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photo_uri);
				startActivityForResult(camera_intent, CAPTURE_IMAGE);
			}
		});
        
        Button select_picture = (Button) findViewById(R.id.button2);
        select_picture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent photo_intent = new Intent(Intent.ACTION_PICK);
				photo_intent.setType("image/*");
				startActivityForResult(photo_intent, SELECT_PHOTO);
			}
		});
        
        Button edit_list = (Button) findViewById(R.id.button3);
        edit_list.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent list_intent = new Intent("com.example.ProductListActivity");
				startActivity(list_intent);
			}
		});
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
			case CAPTURE_IMAGE:
				if (resultCode == Activity.RESULT_OK){
					Intent result_intent = new Intent("com.example.ResultActivity");
					result_intent.putExtra("PHOTO_URI", photo_uri.getPath());
					result_intent.putExtra("Activity", "main");
					startActivity(result_intent);
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
		            
					Intent result_intent = new Intent("com.example.ResultActivity");
					Toast toast = Toast.makeText(getApplicationContext(), picturePath, Toast.LENGTH_LONG);
					toast.show();
					result_intent.putExtra("PHOTO_URI", picturePath);
					result_intent.putExtra("Activity", "main");
					startActivity(result_intent);
				}
		}
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
        		break;
        	case R.id.history:
        		startActivity(new Intent(this, HistoryActivity.class));
        		break;
        //if (id == R.id.about || id == R.id.history) {
            //return true;
        	default:
                return super.onOptionsItemSelected(item);
        }
        
        return true;
        
    }
}
