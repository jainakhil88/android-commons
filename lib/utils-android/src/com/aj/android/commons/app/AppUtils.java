package com.aj.android.commons.app;

import java.util.ArrayList;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.aj.android.commons.java.StringUtils;
/**
 * Collection of utilities to get details related to Application.
 * 
 * @author Akhil Jain
 *
 */
public class AppUtils {
	
	/**
	 * Used to check whether the application has given permission defined or not.
	 * 
	 * @see {@link PackageManager#checkPermission(String, String)}.
	 * 
	 * @param context -{@link Context}.
	 * @param permissionName -Permission granted in {@link android.Manifest.permission}.
	 * @return boolean -true if the permission is defined in manifest, else false.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static boolean hasPermission(Context context,String permissionName){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(permissionName)){
			throw new NullPointerException("permissionName cannot be null");
		}
		if(StringUtils.isEmpty(permissionName)|| StringUtils.isBlank(permissionName)){
			throw new IllegalArgumentException("permissionName cannot be empty");
		}
		PackageManager pm = context.getPackageManager();
		int hasPerm = pm.checkPermission(permissionName,context.getPackageName());
		return (hasPerm==PackageManager.PERMISSION_GRANTED);
	}

	/**
	 * Used to check whether the system has feature for Google TV or not.
	 * 
	 * @see {@link PackageManager#hasSystemFeature(String)}.
	 * 
	 * @param context -{@link Context}.
	 * @return boolean -true if the system has feature com.google.android.tv
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static boolean isGoogleTV(Context context) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
        return context.getPackageManager().hasSystemFeature("com.google.android.tv");
    }

	/**
	 * Get the current Application directory path.<br>
	 * <code>E.g., /data/data/com.app.path</code>
	 * 
	 * @see {@link PackageManager#getPackageInfo(String, int)}.
	 * @see {@link ApplicationInfo#dataDir}.
	 * 
	 * @param context -{@link Context}.
	 * @return {@link String} -return the application directory path or null.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws NameNotFoundException
	 */
	public static String getAppDirectory(Context context) throws NameNotFoundException{
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		String appDirectory=null;
		try {
			appDirectory=context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.dataDir;
		} catch (NameNotFoundException e){
			throw e;
		}
		return appDirectory;
	}
	
	/**
	 * Get Package Name.<br\>
	 * 
	 * @see {@link Context#getPackageManager()}.
	 * 
	 * @param context -{@link Context}.
	 * @return {@link String} -return the application package name or null.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static String getPackageName(Context context){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		return context.getPackageName();
	}
	
	/**
	 * Get the version code as specified in <code>&lt;mainfest&gt; </code> tag.
	 * 
	 * @see {@link PackageManager#getPackageInfo(String, int)}.
	 * @see {@link PackageInfo#versionCode}.
	 * 
	 * @param context -{@link Context}.
	 * @return int -return application version code.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws NameNotFoundException 
	 */
	public static int getVersionCode(Context context) throws NameNotFoundException{
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		PackageInfo pInfo;
		int versionCode=-1;
		try {
			pInfo = context.getPackageManager().getPackageInfo(getPackageName(context), 0);
			versionCode= pInfo.versionCode;
		} catch (NameNotFoundException e) {
			throw new NameNotFoundException();
		}
		return versionCode;
	}

	/**
	 * Get the version name as specified in <code>&lt;mainfest&gt; </code> tag.
	 * 
	 * @see {@link PackageManager#getPackageInfo(String, int)}.
	 * @see {@link PackageInfo#versionName}.
	 * 
	 * @param context -{@link Context}.
	 * @return {@link String} -return application version name.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws NameNotFoundException 
	 */
	public static String getVersionName(Context context) throws NameNotFoundException{
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		PackageInfo pInfo;
		String versionName=null;
		try {
			pInfo = context.getPackageManager().getPackageInfo(getPackageName(context), 0);
			versionName= pInfo.versionName;
		} catch (NameNotFoundException e) {
			throw new NameNotFoundException();
		}
		return versionName;
	}
	
	/**
	 * Gets the list of all available App Widgets.
	 * 
	 * @see {@link AppWidgetManager#getInstalledProviders()}.
	 * 
	 * @param context -{@link Context}.
	 * @return -An {@link ArrayList} of {@link AppWidgetProviderInfo}.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	
	public static ArrayList<AppWidgetProviderInfo> getAllAppWidgets(Context context){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		AppWidgetManager manager=AppWidgetManager.getInstance(context);
		ArrayList<AppWidgetProviderInfo>infoList=new ArrayList<>();
		for(AppWidgetProviderInfo info:manager.getInstalledProviders()){
			infoList.add(info);
		}
		return infoList;
	}
	
	//list all app
	
	
}
