package com.akhil.android8;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.aj.android.commons.database.ExtendedSQLiteOpenHelper;

public class Mysql extends ExtendedSQLiteOpenHelper
{
	private static final String DATABASE_NAME = "database.db";
	
	private static String TABLE_NAME="area";
	
	public static final String COLUMN_AREA_ID="areaID";
	public static final String COLUMN_AREA_NAME="areaName";
	public static final String COLUMN_AREA_CODE="areaCode";
	
	private static final String CREATE_TABLE="create table "+TABLE_NAME+"("
							+COLUMN_AREA_ID+" integer not null,"
							+COLUMN_AREA_NAME+" text,"
							+COLUMN_AREA_CODE+" text"
							+")";

	private static final String TAG = null;		
	
	public Mysql(Context context, String name, CursorFactory factory,int version) {
		super(context, DATABASE_NAME, null, 1);
		if(!checkIfTableExist(TABLE_NAME))
		{
			createTable();
		}
	}

	private void createTable() 
	{
		SQLiteDatabase database =this.getWritableDatabase();
		database.execSQL(CREATE_TABLE);
		database.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	public void save(ContentValues values)
	{
		insertRecords(TABLE_NAME, values);
	}

	public void getRecords()
	{
		SQLiteDatabase database=this.getReadableDatabase();
		Cursor cursor=database.rawQuery("select * from "+TABLE_NAME+" where "+COLUMN_AREA_ID+"<4", null);
		
		if(cursor!=null)
		{
			int position=cursor.getPosition();
			HashMap<String, Integer> indexes=getIndexOfColumns(cursor);
			printMap(indexes);
			Log.d(TAG, "before position "+position);
			while(cursor.moveToNext()){
				int areaIdIndex=cursor.getColumnIndex(COLUMN_AREA_ID);
				int areaNameIndex=cursor.getColumnIndex(COLUMN_AREA_NAME);
				int areaCodeIndex=cursor.getColumnIndex(COLUMN_AREA_CODE);
				
				Log.d(TAG, "id indx "+areaIdIndex);
				Log.d(TAG, "name indx "+areaNameIndex);
				Log.d(TAG, "code indx "+areaCodeIndex);
			}
			
			Log.d(TAG, "after position "+position);
		}
	}
	
	public static void printMap(Map<String,Integer> map) {
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    Log.d(TAG, "key="+key+"\tvalue="+value);
		}
	}
}
