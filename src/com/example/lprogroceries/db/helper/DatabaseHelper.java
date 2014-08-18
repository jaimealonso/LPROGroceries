package com.example.lprogroceries.db.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lprogroceries.db.model.Object;
import com.example.lprogroceries.db.model.MyList;
import com.example.lprogroceries.db.model.Capture;




public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "lproGroceries";

	// Table Names
	private static final String TABLE_LIST = "lists";
	private static final String TABLE_OBJECT = "objects";
	private static final String TABLE_LIST_OBJECT = "list_objects";
	private static final String TABLE_CAPTURE = "captures";

	// Common column names
	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";

	// Objects table

	private static final String KEY_OBJECT_NAME = "object_name";
	private static final String KEY_OBJECT_REF = "object_ref";

	// Lists table

	private static final String KEY_LIST_NAME = "list_name";

	// Captures table

	private static final String KEY_CAPTURE_REF = "capture_ref";
	private static final String KEY_ID_FOUND = "idFoundList";
	private static final String KEY_ID_MISSING = "idMissingList";

	// List_Objects table

	private static final String KEY_OBJECT_ID = "object_id";
	private static final String KEY_LIST_ID = "list_id";

	// Table Create Statements
	// Objects table create statement
	private static final String CREATE_TABLE_OBJECT = "CREATE TABLE "
			+ TABLE_OBJECT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_OBJECT_NAME + " TEXT," + KEY_OBJECT_REF + " TEXT,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	// Lists table create statement
	private static final String CREATE_TABLE_LIST = "CREATE TABLE "
			+ TABLE_LIST + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_LIST_NAME + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

	// Captures table create statement

	private static final String CREATE_TABLE_CAPTURE = "CREATE TABLE "
			+ TABLE_CAPTURE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_ID_FOUND + " INTEGER," + KEY_ID_MISSING + " INTEGER,"
			+ KEY_OBJECT_REF + " TEXT," + KEY_CREATED_AT + " DATETIME" + ")";

	private static final String CREATE_TABLE_LIST_OBJECT = "CREATE TABLE "
			+ TABLE_LIST_OBJECT + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_OBJECT_ID + " INTEGER," + KEY_LIST_ID + " INTEGER,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	 @Override
	    public void onCreate(SQLiteDatabase db) {
	 
	        // creating required tables
	        db.execSQL(CREATE_TABLE_OBJECT);
	        db.execSQL(CREATE_TABLE_LIST);
	        db.execSQL(CREATE_TABLE_LIST_OBJECT);
	        db.execSQL(CREATE_TABLE_CAPTURE);


	        
	        
	    }
	 
	 @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // on upgrade drop older tables
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBJECT);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST_OBJECT);
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAPTURE);
	 
	        // create new tables
	        onCreate(db);
	    }
	 
	 
	 
	 // ------------------------ "OBJECTS" table methods ----------------//
	    
	    /**
	    * Creating an object
	    */
	   public long createObject(Object object) {
	       SQLiteDatabase db = this.getWritableDatabase();

	       ContentValues values = new ContentValues();
	       values.put(KEY_OBJECT_NAME, object.getName());
	       values.put(KEY_OBJECT_REF, object.getRef());
	       values.put(KEY_CREATED_AT, getDateTime());

	       // insert row
	       long object_id = db.insert(TABLE_OBJECT, null, values);

	       // insert tag_ids
//	       for (long tag_id : tag_ids) {
//	           createTodoTag(todo_id, tag_id);
//	       }

	       return object_id;
	   }

	   /**
	    * get single object
	    */
	   public Object getObject(long object_id) {
	       SQLiteDatabase db = this.getReadableDatabase();

	       String selectQuery = "SELECT  * FROM " + TABLE_OBJECT + " WHERE "
	               + KEY_ID + " = " + object_id;

	       Log.e(LOG, selectQuery);

	       Cursor c = db.rawQuery(selectQuery, null);

	       if (c != null)
	           c.moveToFirst();

	       Object obj = new Object();
	       obj.setId(c.getInt(c.getColumnIndex(KEY_ID)));
	       obj.setName((c.getString(c.getColumnIndex(KEY_OBJECT_NAME))));
	       obj.setRef((c.getString(c.getColumnIndex(KEY_OBJECT_REF))));
	       
	       return obj;
	   }
	   
	   /**
	    * get single object by name
	    */
	   public Object getObjectByName(String object_name) {
	       SQLiteDatabase db = this.getReadableDatabase();

	       String selectQuery = "SELECT  * FROM " + TABLE_OBJECT + " WHERE "
	               + KEY_OBJECT_NAME + " = '" + object_name + "'";

	       Log.e(LOG, selectQuery);

	       Cursor c = db.rawQuery(selectQuery, null);

	       if (c != null)
	           c.moveToFirst();

	       Object obj = new Object();
	       obj.setId(c.getInt(c.getColumnIndex(KEY_ID)));
	       obj.setName((c.getString(c.getColumnIndex(KEY_OBJECT_NAME))));
	       obj.setRef((c.getString(c.getColumnIndex(KEY_OBJECT_REF))));
	       
	       return obj;
	   }

	   /**
	    * getting all objects
	    * */
	   public List<Object> getAllObjects() {
	       List<Object> objects = new ArrayList<Object>();
	       String selectQuery = "SELECT  * FROM " + TABLE_OBJECT;

	       Log.e(LOG, selectQuery);

	       SQLiteDatabase db = this.getReadableDatabase();
	       Cursor c = db.rawQuery(selectQuery, null);

	       // looping through all rows and adding to list
	       if (c.moveToFirst()) {
	           do {
	               Object obj = new Object();
	               obj.setId(c.getInt((c.getColumnIndex(KEY_ID))));
	               obj.setName((c.getString(c.getColumnIndex(KEY_OBJECT_NAME))));
	    	       obj.setRef((c.getString(c.getColumnIndex(KEY_OBJECT_REF))));

	               // adding to todo list
	               objects.add(obj);
	           } while (c.moveToNext());
	       }

	       return objects;
	   }

	   /**
	    * getting all objects under single list
	    * */
	   public List<Object> getAllObjectsByList(long list_id) {
	       List<Object> objects = new ArrayList<Object>();

	       String selectQuery = "SELECT  * FROM " + TABLE_OBJECT + " tobj, "
	               + TABLE_LIST + " tlist, " + TABLE_LIST_OBJECT + " tlo WHERE tlist."
	               + KEY_ID + " = '" + list_id + "'" + " AND tlist." + KEY_ID
	               + " = " + "tlo." + KEY_LIST_ID + " AND tOBJ." + KEY_ID + " = "
	               + "tlo." + KEY_OBJECT_ID;

	       Log.e(LOG, selectQuery);

	       SQLiteDatabase db = this.getReadableDatabase();
	       Cursor c = db.rawQuery(selectQuery, null);

	       // looping through all rows and adding to list
	       if (c.moveToFirst()) {
	           do {
	               Object obj = new Object();
	               obj.setId(c.getInt((c.getColumnIndex(KEY_ID))));
	               obj.setName((c.getString(c.getColumnIndex(KEY_OBJECT_NAME))));
	    	       obj.setRef((c.getString(c.getColumnIndex(KEY_OBJECT_REF))));

	               // adding to todo list
	               objects.add(obj);
	           } while (c.moveToNext());
	       }

	       return objects;
	   }

	   /**
	    * getting object count
	    */
	   public int getObjectCount() {
	       String countQuery = "SELECT  * FROM " + TABLE_OBJECT;
	       SQLiteDatabase db = this.getReadableDatabase();
	       Cursor cursor = db.rawQuery(countQuery, null);

	       int count = cursor.getCount();
	       cursor.close();

	       // return count
	       return count;
	   }

	   /**
	    * Updating an object
	    */
	   public int updateObject(Object object) {
	       SQLiteDatabase db = this.getWritableDatabase();

	       ContentValues values = new ContentValues();
	       values.put(KEY_OBJECT_NAME, object.getName());
	       values.put(KEY_OBJECT_REF, object.getRef());

	       // updating row
	       return db.update(TABLE_OBJECT, values, KEY_ID + " = ?",
	               new String[] { String.valueOf(object.getId()) });
	   }

	   /**
	    * Deleting an object
	    */
	   public void deleteObject(long object_id) {
	       SQLiteDatabase db = this.getWritableDatabase();
	       db.delete(TABLE_OBJECT, KEY_ID + " = ?",
	               new String[] { String.valueOf(object_id) });
	   }
	 
	   
	   
	   // ------------------------ "lists" table methods ----------------//

	   /**
	    * Creating list
	    */
	   public long createList(MyList list) {
	       SQLiteDatabase db = this.getWritableDatabase();

	       ContentValues values = new ContentValues();
	       values.put(KEY_LIST_NAME, list.getList_name());
	       values.put(KEY_CREATED_AT, getDateTime());

	       // insert row
	       long list_id = db.insert(TABLE_LIST, null, values);

	       if(!list.getObjectList().isEmpty()){
	    	   for(Object o : list.getObjectList()){
	    		   this.createListObject(o.getId(), list_id);
	    	   }
	       }
	       
	       return list_id;
	   }

	   /**
	    * getting all lists
	    * */
	   public List<MyList> getAllLists() {
	       List<MyList> lists = new ArrayList<MyList>();
	       String selectQuery = "SELECT  * FROM " + TABLE_LIST;

	       Log.e(LOG, selectQuery);

	       SQLiteDatabase db = this.getReadableDatabase();
	       Cursor c = db.rawQuery(selectQuery, null);

	       // looping through all rows and adding to list
	       if (c.moveToFirst()) {
	           do {
	               MyList list = new MyList();
	               list.setId(c.getInt((c.getColumnIndex(KEY_ID))));
	               list.setList_name(c.getString(c.getColumnIndex(KEY_LIST_NAME)));

	               // adding to tags list
	               lists.add(list);
	           } while (c.moveToNext());
	       }
	       return lists;
	   }

	   /**
	    * Updating a list
	    */
	   public int updateList(MyList list) {
	       SQLiteDatabase db = this.getWritableDatabase();

	       ContentValues values = new ContentValues();
	       values.put(KEY_LIST_NAME, list.getList_name());

	       // updating row
	       return db.update(TABLE_LIST, values, KEY_ID + " = ?",
	               new String[] { String.valueOf(list.getId()) });
	   }

	   /**
	    * Deleting a tag
	    */
	   public void deleteList(MyList list, boolean should_delete_all_object_lists) {
	       SQLiteDatabase db = this.getWritableDatabase();

	       // before deleting tag
	       // check if todos under this tag should also be deleted
	       if (should_delete_all_object_lists) {
	           // get all todos under this tag
	           List<Object> allObjectLists = getAllObjectsByList(list.getId());

	           // delete all todos
	           for (Object object : allObjectLists) {
	               // delete todo
	               deleteObject(object.getId());
	           }
	       }

	       // now delete the tag
	       db.delete(TABLE_LIST, KEY_ID + " = ?",
	               new String[] { String.valueOf(list.getId()) });
	   }
	   
	   
	   
	   // ------------------------ "list_objects" table methods ----------------//

	   /**
	    * Creating list_object
	    */
	   public long createListObject(long object_id, long list_id) {
	       SQLiteDatabase db = this.getWritableDatabase();

	       ContentValues values = new ContentValues();
	       values.put(KEY_OBJECT_ID, object_id);
	       values.put(KEY_LIST_ID, list_id);
	       values.put(KEY_CREATED_AT, getDateTime());

	       long id = db.insert(TABLE_LIST_OBJECT, null, values);

	       return id;
	   }

	   /**
	    * Updating an object list
	    */
	   public int updateNoteTag(long id, long list_id) {
	       SQLiteDatabase db = this.getWritableDatabase();

	       ContentValues values = new ContentValues();
	       values.put(KEY_LIST_ID, list_id);

	       // updating row
	       return db.update(TABLE_OBJECT, values, KEY_ID + " = ?",
	               new String[] { String.valueOf(id) });
	   }

	   /**
	    * Deleting an object list
	    */
	   public void deleteObjectList(long id) {
	       SQLiteDatabase db = this.getWritableDatabase();
	       db.delete(TABLE_OBJECT, KEY_ID + " = ?",
	               new String[] { String.valueOf(id) });
	   }
	   
	   /**
	    * Delete object from list
	    */
	   public void deleteObjectFromList(long idObject, long idList){
	       SQLiteDatabase db = this.getWritableDatabase();
	       db.delete(TABLE_LIST_OBJECT, KEY_OBJECT_ID + "=? AND " + KEY_LIST_ID + "=?",
	    		   new String[] {String.valueOf(idObject), String.valueOf(idList)});
	   }

	   // ------------------------ "captures" table methods ----------------//

	   /**
	    * Creating a capture
	    */
	   
	   public long createCapture(Capture capture){
	       SQLiteDatabase db = this.getWritableDatabase();

		   ContentValues values = new ContentValues();
	       values.put(KEY_ID_FOUND, capture.getIdFoundList());
	       values.put(KEY_ID_MISSING, capture.getIdMissingList());
	       values.put(KEY_OBJECT_REF, capture.getRef());
	       values.put(KEY_CREATED_AT, getDateTime());

	       // insert row
	       long capture_id = db.insert(TABLE_CAPTURE, null, values);
	       return capture_id;
	   }
	   
	   /**
	    * Getting a capture
	    */
	   
	   public Capture getCapture(long capture_id) {
	       SQLiteDatabase db = this.getReadableDatabase();

	       String selectQuery = "SELECT  * FROM " + TABLE_CAPTURE + " WHERE "
	               + KEY_ID + " = " + capture_id;

	       Log.e(LOG, selectQuery);

	       Cursor c = db.rawQuery(selectQuery, null);

	       if (c != null)
	           c.moveToFirst();

	       Capture capt = new Capture();
	       capt.setId(c.getInt(c.getColumnIndex(KEY_ID)));
	       capt.setRef((c.getString(c.getColumnIndex(KEY_OBJECT_REF))));
	       capt.setIdFoundList(c.getInt(c.getColumnIndex(KEY_ID_FOUND)));
	       capt.setIdMissingList(c.getInt(c.getColumnIndex(KEY_ID_MISSING)));
	       capt.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
	       
	       return capt;
	   }
	   
	   /**
	    * getting all captures
	    * */
	   public List<Capture> getAllCaptures() {
	       List<Capture> captures = new ArrayList<Capture>();
	       String selectQuery = "SELECT  * FROM " + TABLE_CAPTURE;

	       Log.e(LOG, selectQuery);

	       SQLiteDatabase db = this.getReadableDatabase();
	       Cursor c = db.rawQuery(selectQuery, null);

	       // looping through all rows and adding to list
	       if (c.moveToFirst()) {
	           do {
	        	   Capture capt = new Capture();
	    	       capt.setId(c.getInt(c.getColumnIndex(KEY_ID)));
	    	       capt.setRef((c.getString(c.getColumnIndex(KEY_OBJECT_REF))));
	    	       capt.setIdFoundList(c.getInt(c.getColumnIndex(KEY_ID_FOUND)));
	    	       capt.setIdMissingList(c.getInt(c.getColumnIndex(KEY_ID_MISSING)));
	    	       capt.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

	               // adding to todo list
	               captures.add(capt);
	           } while (c.moveToNext());
	       }

	       return captures;
	   }
	   
	   
	   /**
	    * Updating a capture
	    */
	   public int updateCapture(Capture capture) {
	       SQLiteDatabase db = this.getWritableDatabase();

	       ContentValues values = new ContentValues();
	       values.put(KEY_ID_FOUND, capture.getIdFoundList());
	       values.put(KEY_ID_MISSING, capture.getIdMissingList());
	       values.put(KEY_OBJECT_REF, capture.getRef());

	       // updating row
	       return db.update(TABLE_CAPTURE, values, KEY_ID + " = ?",
	               new String[] { String.valueOf(capture.getId()) });
	   }

	   /**
	    * Deleting a capture
	    */
	   public void deleteCapture(long capture_id) {
	       SQLiteDatabase db = this.getWritableDatabase();
	       db.delete(TABLE_CAPTURE, KEY_ID + " = ?",
	               new String[] { String.valueOf(capture_id) });
	   }

	   
	   
	   
	   
	   // closing database
	   public void closeDB() {
	       SQLiteDatabase db = this.getReadableDatabase();
	       if (db != null && db.isOpen())
	           db.close();
	   }
	   
	   
	   
	   private String getDateTime() {
	        SimpleDateFormat dateFormat = new SimpleDateFormat(
	                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	        Date date = new Date();
	        return dateFormat.format(date);
	    }
	   
}