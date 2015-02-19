package com.aj.android.commons.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.aj.android.commons.java.StringUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * Collection of generic methods for common purpose task  in {@link SQLiteDatabase}.
 *
 *@author Akhil Jain
 */
public class DBUtils {
	
	public enum SQLITE_DATA_TYPE{
		TEXT,
		NUMERIC,
		INTEGER,
		REAL,
		BLOB
	};

	/**Get all table names present in given database in List format, except <code>android_metadata</code> and <code>sqlite_sequence</code>.
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * 
	 * @param readableDatabase -{@link SQLiteDatabase} pass readable instance of database.
	 * @return {@link ArrayList}<{@link String}> -of all the table names, present in database.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static ArrayList<String> getAllTableNames(SQLiteDatabase readableDatabase){
		if(StringUtils.isNull(readableDatabase)){
			throw new NullPointerException("readableDatabase cannot be null");
		}
		ArrayList<String> tables = new ArrayList<String>();
		Cursor cursor=null;
		try{
			cursor = readableDatabase.rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null);
			if(cursor!=null){	
				cursor.moveToFirst();
				while (!cursor.isAfterLast()){
					String tableName = cursor.getString(1);
					if (!tableName.equals("android_metadata") &&!tableName.equals("sqlite_sequence")){
						//only add table if its not metadata and sqlite sequence
						tables.add(tableName);
					}
					cursor.moveToNext();
				}
				cursor.close();
			}
		}finally{
			closeDBConnection(readableDatabase, cursor);
		}
		return tables;
	}
	
	/**
	 * SQLite does not have a separate Boolean storage class.<br>
	 * Instead, Boolean values are stored as integers 0 (false) and 1 (true).<br>
	 * 
	 * If value is true it returns 1, else 0; 
	 * 
	 * @param value boolean value-either true or false
	 * @return either numeric 0 or 1
	 */
	public static int getSqliteBoolean(boolean value){
		int sqliteValue=-1;
		if(value){
			sqliteValue=1;
		}
		else{
			sqliteValue=0;
		}
		return sqliteValue;
	}


	/**
	 * SQLite does not have a separate Boolean storage class.<br>
	 * Instead, Boolean values are stored as integers 0 (false) and 1 (true).<br>
	 * 
	 * If value is 1 it returns true, else false; 
	 * 
	 * @param sqliteValue int value-either 1 or 0
	 * @return either true or false
	 */
	public static boolean getBoolean(int sqliteValue){
		boolean value=false;
		if(sqliteValue>=1){
			value=true;
		}
		else{
			value=false;
		}
		return value;
	}
	
	/***
	 * Gets the number of rows present in the given table for the given database.  
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * 
	 * @param tableName -{@link String} table name for which row count is required.
	 * @param readableDatabase -{@link SQLiteDatabase} pass readable instance of database.
	 * @return int -number of rows present in database, else -1.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static int getCount(String tableName, SQLiteDatabase readableDatabase) {
		if(StringUtils.isNull(readableDatabase)){
			throw new NullPointerException("readableDatabase cannot be null");
		}
		if(StringUtils.isNull(tableName)){
			throw new NullPointerException("tableName cannot be null");
		}
		if(StringUtils.isEmpty(tableName)){
			throw new IllegalArgumentException("tableName cannot be empty");
		}
		String countQuery = "SELECT  * FROM " + tableName;		
		Cursor cursor = null;
		int count=-1;
		try
		{
			cursor = readableDatabase.rawQuery(countQuery, null);
			count=cursor.getCount();
		}finally{
			closeDBConnection(readableDatabase, cursor);
		}		
		return count;
	}
	
	/**
	 * Retrieve the index for the given cursor, based on column name and index position for the column in cursor.
	 * 
	 * @param cursor -{@link Cursor} for which index of columns is required.
	 * @return {@link HashMap}<{@link String},{@link Integer}> - where Key is the {@link String} column name and {@link Integer} corresponding column index in the cursor.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null. 
	 */
	public static HashMap<String, Integer> getIndexOfColumns(Cursor cursor){
		if(StringUtils.isNull(cursor)){
			throw new NullPointerException("cursor cannot be null");
		}
		HashMap<String, Integer>indexes=new HashMap<>();
		for(int i=0;i<cursor.getColumnCount();i++){
			indexes.put(cursor.getColumnName(i), i);
		}
		return indexes;
	}
	
	/**
	 * Check if the column table name exist in given table and database.
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * @param tableName -{@link String} table name where needs to be searched.
	 * @param columnName -{@link String} column name which needs to be searched.
	 * @param readableDatabase -{@link SQLiteDatabase} pass readable instance of database.
	 * @return boolean -returns true if the table exist in the database, else false.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static boolean checkIfColumnExist(SQLiteDatabase readableDatabase,String tableName, String columnName){
		boolean isExist = false;
		String query="PRAGMA table_info("+tableName+")";
		Cursor cursor = readableDatabase.rawQuery(query,null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			if(cursor.getString(1).toString().equalsIgnoreCase(columnName)) {
				isExist = true;
				break;
			}
			cursor.moveToNext();
		}
		closeDBConnection(readableDatabase, cursor);
		return isExist;
	}

	/**
	 * Check if the given table name exist in given database or not.
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * @param tableName -{@link String} table name which needs to be searched.
	 * @param readableDatabase -{@link SQLiteDatabase} pass readable instance of database.
	 * @return boolean -returns true if the table exist in the database, else false.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static boolean checkIfTableExist(String tableName,SQLiteDatabase readableDatabase){
		if(StringUtils.isNull(readableDatabase)){
			throw new NullPointerException("readableDatabase cannot be null");
		}
		if(StringUtils.isNull(tableName)){
			throw new NullPointerException("tableName cannot be null");
		}
		if(StringUtils.isEmpty(tableName)){
			throw new IllegalArgumentException("tableName cannot be empty");
		}
		boolean exist=false;
		Cursor cursor=readableDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='"+tableName+"';", null);
		if(cursor!=null){
			if(cursor.getCount()==1){
				exist=true;
			}
		}
		closeDBConnection(readableDatabase, cursor);
		return exist;
	}

	/**
	 * Check if given table contain any records. Returns true if the table has even one record, else false.
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * @param tableName -{@link String} table name which needs to be looked for any record.
	 * @param readableDatabase -{@link SQLiteDatabase} pass readable instance of database.
	 * @return boolean -returns true if table contain any records, else false.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static boolean containsRecords(String tableName,SQLiteDatabase readableDatabase){
		if(StringUtils.isNull(readableDatabase)){
			throw new NullPointerException("readableDatabase cannot be null");
		}
		if(StringUtils.isNull(tableName)){
			throw new NullPointerException("tableName cannot be null");
		}
		if(StringUtils.isEmpty(tableName)){
			throw new IllegalArgumentException("tableName cannot be empty");
		}
		boolean records=false;
		Cursor cursor=null;
		try
		{
			cursor=readableDatabase.query(tableName, null, null, null, null, null, null);
			if(cursor!=null){
				if(cursor.getCount()>0){
					records=true;
				}
			}
		}
		finally{
			closeDBConnection(readableDatabase, cursor);
		}
		return records;
	}
	/**
	 * Add new Column to database, the new column is added to last of database.
	 * 
	 * @param writableDatabase -{@link SQLiteDatabase} pass writable instance of database.
	 * @param tableName -{@link String} table name where needs to be searched.
	 * @param columnName -{@link String} column name which needs to be searched.
	 * @param dataType -{@link SQLITE_DATA_TYPE} datatype for sqlite.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static void addColumn(SQLiteDatabase writableDatabase,String tableName, String columnName ,SQLITE_DATA_TYPE dataType){
		if(StringUtils.isNull(writableDatabase)){
			throw new NullPointerException("writableDatabase cannot be null");
		}
		if(StringUtils.isNull(tableName)){
			throw new NullPointerException("tableName cannot be null");
		}
		if(StringUtils.isEmpty(tableName)){
			throw new IllegalArgumentException("tableName cannot be empty");
		}
		if(StringUtils.isNull(columnName)){
			throw new NullPointerException("columnName cannot be null");
		}
		if(StringUtils.isEmpty(columnName)){
			throw new IllegalArgumentException("columnName cannot be empty");
		}
		String alterQuery="ALTER TABLE "+tableName+" ADD COLUMN "+columnName+ " "+ dataType.toString();
		writableDatabase.execSQL(alterQuery);
		closeDBConnection(writableDatabase,null);
	}
	
	
	/**Inserting the records into the given table in the given database.
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * @param tableName -{@link String} table name where records needs to be saved. 
	 * @param contentValues -{@link ContentValues} bundle of values which needs to be saved.
	 * @param writableDatabase -{@link SQLiteDatabase} pass writable instance of database.
	 * @return int -row count where record is inserted.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static long insertRecords(String tableName,ContentValues contentValues,SQLiteDatabase writableDatabase){
		if(StringUtils.isNull(writableDatabase)){
			throw new NullPointerException("writableDatabase cannot be null");
		}
		if(StringUtils.isNull(tableName)){
			throw new NullPointerException("tableName cannot be null");
		}
		if(StringUtils.isEmpty(tableName)){
			throw new IllegalArgumentException("tableName cannot be empty");
		}
		if(StringUtils.isNull(contentValues)){
			throw new NullPointerException("contentValues cannot be null");
		}
		long rowID=-1;
		try{
			// Inserting Row
			rowID=writableDatabase.insert(tableName, null, contentValues);    		 
		}
		finally{
			closeDBConnection(writableDatabase, null);
		}
		return rowID;
	}


	/**
	 * Drop / Remove the table from the given database.
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically. 
	 * @param tableName -{@link String} table name which needs to be removed.
	 * @param writableDatabase -{@link SQLiteDatabase} pass writable instance of database.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static void dropTable(String tableName,SQLiteDatabase writableDatabase){
		if(StringUtils.isNull(writableDatabase)){
			throw new NullPointerException("writableDatabase cannot be null");
		}
		if(StringUtils.isNull(tableName)){
			throw new NullPointerException("tableName cannot be null");
		}
		if(StringUtils.isEmpty(tableName)){
			throw new IllegalArgumentException("tableName cannot be empty");
		}
		try{
			String sql="DROP TABLE IF EXISTS "+tableName;
			writableDatabase.execSQL(sql);
		}
		finally{
			closeDBConnection(writableDatabase, null);
		}
	}

	/**
	 * Drop / Remove all tables from the given database.
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * @param writableDatabase -{@link SQLiteDatabase} pass writable instance of database.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static void dropAllTables(SQLiteDatabase writableDatabase){
		if(StringUtils.isNull(writableDatabase)){
			throw new NullPointerException("writableDatabase cannot be null");
		}
		List<String> tables=getAllTableNames(writableDatabase);
		for(String tableName:tables){
			dropTable(tableName,writableDatabase);
		}
	}

	/**
	 * Delete all the data in the given table from given database.
	 *	<br>
	 * {@link SQLiteDatabase} is closed automatically.
	 *@param tableName -{@link String} table name from which data needs to be deleted.
	 *@param writableDatabase -{@link SQLiteDatabase} pass writable instance of database.
	 *
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.	 
	 */
	public static void deleteAllData(String tableName,SQLiteDatabase writableDatabase){
		if(StringUtils.isNull(writableDatabase)){
			throw new NullPointerException("writableDatabase cannot be null");
		}
		if(StringUtils.isNull(tableName)){
			throw new NullPointerException("tableName cannot be null");
		}
		if(StringUtils.isEmpty(tableName)){
			throw new IllegalArgumentException("tableName cannot be empty");
		}
		try{
			writableDatabase.execSQL("delete from "+ tableName);
		}
		finally{
			closeDBConnection(writableDatabase, null);
		}
	}
	
	/**
	 * Close if {@link SQLiteDatabase}  or {@link Cursor} is open. 
	 * <br>
	 * <pre>E.g.,
	 * closeDBConnection(database,cursor);
	 * closeDBConnection(null,cursor);
	 * closeDBConnection(database,null);
	 * </pre>
	 * @param database -{@link SQLiteDatabase} which has to be closed.
	 * @param cursor -{@link Cursor} which has to be closed.
	 */
	public static void closeDBConnection(SQLiteDatabase database,Cursor cursor){
		if(cursor!=null){
			if(!cursor.isClosed()){
				cursor.close();
			}
		}
		if(database!=null){
			if(database.isOpen()){
				database.close();
			}
		}
	}	


}
