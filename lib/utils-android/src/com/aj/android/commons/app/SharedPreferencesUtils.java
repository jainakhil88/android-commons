package com.aj.android.commons.app;

import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION_CODES;

import com.aj.android.commons.java.StringUtils;

/**
 * Collection of utilities related {@link SharedPreferences}, use this for quick saving 
 * and retrieving information from shared preferences.
 * 
 * @author Akhil Jain
 *
 */
public class SharedPreferencesUtils {

	String preferenceName;
	Context context;
	
	/**
	 *  Initialize context and preference name, The preference name specified here will be used in all method calls when getting or setting the information.  
	 *  
	 * @param context  -{@link Context}
	 * @param preferenceName - {@link String} name of file where preferences need to be saved.
	 *  
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public SharedPreferencesUtils(Context context,String preferenceName){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(preferenceName)){
			throw new NullPointerException("preferenceName cannot be null");
		}
		if(StringUtils.isEmpty(preferenceName) || StringUtils.isBlank(preferenceName)){
			throw new IllegalArgumentException("preferenceName cannot be empty");
		}
		this.context=context;
		this.preferenceName=preferenceName;
	}
	
	/***
	 * Gets string for the given preference key. If no value is found corresponding to key then <code>null</code> will be returned.
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be retrieved.  
	 * @return {@link String} - preference value if key exist, else <code>null</code>. 
	 */
	public String getString(String preferenceKey){
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		String preferenceValue=preferences.getString(preferenceKey, null);
		return preferenceValue;
	}
	
	/***
	 * Gets boolean for the given preference key. If no value is found corresponding to key then <code>false</code> will be returned.
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be retrieved.  
	 * @return boolean - preference value if key exist, else <code>false</code>. 
	 */
	public boolean getBoolean(String preferenceKey){
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		boolean preferenceValue=preferences.getBoolean(preferenceKey, false);
		return preferenceValue;
	}
	
	/***
	 * Gets integer for the given preference key. If no value is found corresponding to key then <code>-1</code> will be returned.
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be retrieved.  
	 * @return int - preference value if key exist, else <code>-1</code>. 
	 */
	public int getInt(String preferenceKey){
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		int preferenceValue=preferences.getInt(preferenceKey, -1);
		return preferenceValue;
	}
	
	/***
	 * Gets long for the given preference key. If no value is found corresponding to key then <code>-1</code> will be returned.
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be retrieved.  
	 * @return int - preference value if key exist, else <code>-1</code>.  
	 */
	public long getLong(String preferenceKey){
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		long preferenceValue=preferences.getLong(preferenceKey, -1);
		return preferenceValue;
	}
	
	/***
	 * Gets float for the given preference key. If no value is found corresponding to key then <code>-1</code> will be returned.
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be retrieved.  
	 * @return float - preference value if key exist, else <code>-1</code>.  
	 */
	public float getFloat(String preferenceKey){
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		float preferenceValue=preferences.getFloat(preferenceKey, -1);
		return preferenceValue;
	}
	
	/***
	 * Gets {@link Set} of {@link String} for the given preference key. If no value is found corresponding to key then <code>null</code> will be returned.
	 * <br>
	 * This call is only applicable for API Level 11({@link VERSION_CODES#HONEYCOMB}) or Higher
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be retrieved.  
	 * @return {@link Set}<{@link String}> - preference value if key exist, else <code>null</code>.  
	 */
	@SuppressLint("NewApi")
	public Set<String> getStringSet(String preferenceKey) throws Exception{
		if(VersionUtils.isPreHoneycomb()){
			throw new Exception("Following method setStringSet() is not available before API Level 11");
		}
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		Set<String> preferenceValue=preferences.getStringSet(preferenceKey, null);
		return preferenceValue;
	}
	
	/**
	 * Set the {@link String} value for the given preference key.	 
	 * <br>
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be saved.
	 * @param preferenceValue -{@link String} value needs to be saved.   
	 */
	public void setString(String preferenceKey,String preferenceValue){
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putString(preferenceKey, preferenceValue);
		editor.commit();
	}
	
	/**
	 * Set the boolean value for the given preference key.	 
	 * <br>
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be saved.
	 * @param preferenceValue -boolean value needs to be saved.   
	 */
	public void setBoolean(String preferenceKey,boolean preferenceValue){
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putBoolean(preferenceKey, preferenceValue);
		editor.commit();
	}
	
	/**
	 * Set the int value for the given preference key.	 
	 * <br>
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be saved.
	 * @param preferenceValue -int value needs to be saved.   
	 */
	public void setInt(String preferenceKey,int preferenceValue){
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putInt(preferenceKey, preferenceValue);
		editor.commit();
	}
	
	/**
	 * Set the long value for the given preference key.	 
	 * <br>
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be saved.
	 * @param preferenceValue -long value needs to be saved.   
	 */
	public void setLong(String preferenceKey,long preferenceValue){
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putLong(preferenceKey, preferenceValue);
		editor.commit();
	}
	
	/**
	 * Set the float value for the given preference key.	 
	 * <br>
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be saved.
	 * @param preferenceValue -float value needs to be saved.   
	 */
	public void setFloat(String preferenceKey,float preferenceValue){
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=preferences.edit();
		editor.putFloat(preferenceKey, preferenceValue);
		editor.commit();
	}
	
	/***
	 * Sets {@link Set} of {@link String} for the given preference key.
	 * <br>
	 * This call is only applicable for API Level 11({@link VERSION_CODES#HONEYCOMB}) or Higher
	 * 
	 * @param preferenceKey -{@link String} key name for which value needs to be retrieved.
	 * @param preferenceValue -{@link Set}<{@link String}> preference value needs to be saved.     
	 */
	@SuppressLint("NewApi")
	public void setStringSet(String preferenceKey,Set<String> preferenceValue) throws Exception{
		if(VersionUtils.isPreHoneycomb()){
			throw new Exception("Following method setStringSet() is not available before API Level 11");
		}
		SharedPreferences preferences=context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		SharedPreferences.Editor configEditor = preferences.edit();
		configEditor.putStringSet(preferenceKey, preferenceValue);
		configEditor.commit();
	}
	

}
