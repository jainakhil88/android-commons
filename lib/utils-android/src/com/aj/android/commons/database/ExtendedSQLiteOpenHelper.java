package com.aj.android.commons.database;

import java.util.ArrayList;
import java.util.HashMap;

import com.aj.android.commons.database.DBUtils.SQLITE_DATA_TYPE;
import com.aj.android.commons.java.StringUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * An abstract extension of {@link SQLiteOpenHelper} with collection of generic methods for {@link SQLiteDatabase} and {@link Cursor}
 * defined in class. 
 * <br>
 * 
 * To use, simply extends your database from {@link ExtendedSQLiteOpenHelper} and use normally as with {@link SQLiteOpenHelper}.
 * <br>
 * E.g.,
 *  <code> 
 *  class Database extends {@link ExtendedSQLiteOpenHelper}
 *  </code>
 *  
  *@author Akhil Jain
 */
public abstract class ExtendedSQLiteOpenHelper extends SQLiteOpenHelper {

	/***
	 * Constructor
	 * 
	 * @param context -{@link Context}.
	 * @param name -{@link String} name of the database.
	 * @param factory -{@link CursorFactory}.
	 * @param version -version number of the database,
	 */
	public ExtendedSQLiteOpenHelper(Context context, String name,CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/**Get all table names present in database in List format, except <code>android_metadata</code> and <code>sqlite_sequence</code>.
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * 
	 * @see {@link DBUtils#getAllTableNames(SQLiteDatabase)}.
	 * 
	 * @return {@link ArrayList}<{@link String}> - of all the table names, present in database.
	 */
	protected ArrayList<String> getAllTableNames(){
		return DBUtils.getAllTableNames(getReadableDatabase()); 
	}

	/**
	 * SQLite does not have a separate Boolean storage class.<br>
	 * Instead, Boolean values are stored as integers 0 (false) and 1 (true).<br> 
	 * If value is true it returns 1, else 0; <br>
	 * 
	 * @see {@link DBUtils#getSqliteBoolean(boolean)}.
	 * 
	 * @param value -boolean value.
	 * @return int -either numeric 0 or 1.
	 */
	public static int getSqliteBoolean(boolean value){
		return DBUtils.getSqliteBoolean(value);
	}


	/**
	 * SQLite does not have a separate Boolean storage class.<br>
	 * Instead, Boolean values are stored as integers 0 (false) and 1 (true).
	 * <br>
	 * If value is 1 it returns true, else false. 
	 * 
	 * @see {@link DBUtils#getBoolean(int)}.
	 * 
	 * @param sqliteValue int -either 1 or 0.
	 * @return boolean -either true or false.
	 * 
	 */
	public static boolean getBoolean(int sqliteValue){
		return DBUtils.getBoolean(sqliteValue);
	}

	/**
	 * Gets the number of rows present in the given table. 
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * 
	 * @see {@link DBUtils#getCount(String, SQLiteDatabase)}.
	 * 
	 * @param tableName -{@link String} table name for which row count is required.
	 * @return int -number of rows present in database, else -1.
	 * 
	 */
	public int getCount(String tableName) {    	
		return DBUtils.getCount(tableName, getReadableDatabase());
	}
	
	/**
	 * Retrieve the index for the given cursor, based on column name and index position for the column in cursor.
	 * @see {@link DBUtils#getIndexOfColumns(Cursor)}.
	 * 
	 * @param cursor -{@link Cursor} for which index of columns is required.
	 * @return {@link HashMap}<{@link String},{@link Integer}> - where Key is the {@link String} column name and {@link Integer} corresponding column index in the cursor.
	 * 
	 */
	public HashMap<String, Integer> getIndexOfColumns(Cursor cursor){
		return DBUtils.getIndexOfColumns(cursor);
	}
	
	/**
	 * Check if the column table name exist in given table.
	 * <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * 
	 * @param tableName -{@link String} table name where needs to be searched.
	 * @param columnName -{@link String} column name which needs to be searched.
	 * @return boolean -returns true if the table exist in the database, else false.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public boolean checkIfColumnExist(String tableName, String columnName){
		return DBUtils.checkIfColumnExist(getReadableDatabase(), tableName, columnName);
	}

	/**
	 * Check if the given table name exist in database or not.<br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * 
	 * @see {@link DBUtils#checkIfTableExist(String, SQLiteDatabase)}.
	 * 
	 * @param tableName -{@link String} table name which needs to be searched.
	 * @return boolean -returns true if the table exist in the database, else false.
	 */
	protected boolean checkIfTableExist(String tableName){
		return DBUtils.checkIfTableExist(tableName, getReadableDatabase()); 
	}

	/**
	 * Check if given table contain any records. Returns true if the table has even one record, else false.<br> 
	 * {@link SQLiteDatabase} is closed automatically.
	 * 
	 *	@see {@link DBUtils#containsRecords(String, SQLiteDatabase)}.
	 * 
	 * @param tableName -{@link String} table name which needs to be looked for any record.
	 * @return boolean -returns true if table contain any records, else false.
	 */
	public boolean containsRecords(String tableName){
		return DBUtils.containsRecords(tableName, getReadableDatabase()); 
	}

	/**
	 * Add new Column to database, the new column is added to last of database.
	 * 
	 * @param tableName -{@link String} table name where needs to be searched.
	 * @param columnName -{@link String} column name which needs to be searched.
	 * @param dataType -{@link SQLITE_DATA_TYPE} datatype for sqlite.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public void addColumn(String tableName, String columnName ,SQLITE_DATA_TYPE dataType){
		DBUtils.addColumn(getWritableDatabase(), tableName, columnName, dataType);
	}
	
	/**Inserting the records into the given table.<br>
	 * 
	 * {@link SQLiteDatabase} is closed automatically.
	 *	@see {@link DBUtils#insertRecords(String, ContentValues, SQLiteDatabase)}.
	 * 
	 * @param tableName -{@link String} table name where records needs to be saved. 
	 * @param contentValues -{@link ContentValues} bundle of values which needs to be saved.
	 * @return int -row count where record is inserted.
	 */
	public long insertRecords(String tableName,ContentValues contentValues){
		return DBUtils.insertRecords(tableName, contentValues, getWritableDatabase());
	}
	
	
	/**
	 * Drop / Remove the table from the database. <br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * 
	 *	@see {@link DBUtils#dropTable(String, SQLiteDatabase)}.
	 * 
	 * @param tableName -{@link String} table name which needs to be removed.
	 */
	public void dropTable(String tableName){
		DBUtils.dropTable(tableName,getWritableDatabase());
	}

	/**
	 * Drop / Remove all tables from the database.<br>
	 * {@link SQLiteDatabase} is closed automatically.
	 *	@see {@link DBUtils#dropAllTables(SQLiteDatabase)}.
	 */
	public void dropAllTables(){
		DBUtils.dropAllTables(getWritableDatabase());
	}

	/**
	 * Delete all the data in the given table.<br>
	 * {@link SQLiteDatabase} is closed automatically.
	 * 
	 *	@see {@link DBUtils#deleteAllData(String, SQLiteDatabase)}.
	 *	@param tableName -{@link String} table name from which data needs to be deleted.
	 */
	public void deleteAllData(String tableName){
		DBUtils.deleteAllData(tableName, getWritableDatabase());;
	}
	
	/**
	 * Close if {@link SQLiteDatabase}  or {@link Cursor} is open. 
	 * <br>
	 * <pre>E.g.,
	 * closeDBConnection(database,cursor);
	 * closeDBConnection(null,cursor);
	 * closeDBConnection(database,null);
	 * </pre>
	 * 
	 * @see {@link DBUtils#closeDBConnection(SQLiteDatabase, Cursor)}.
	 * 
	 * @param database -{@link SQLiteDatabase} which has to be closed.
	 * @param cursor -{@link Cursor} which has to be closed.
	 */
	public void closeDBConnection(SQLiteDatabase database,Cursor cursor){
		DBUtils.closeDBConnection(database, cursor);
	}
}
