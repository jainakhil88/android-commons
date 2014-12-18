package com.aj.android.commons.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.aj.android.commons.exception.PermissionNotDefinedException;
import com.aj.android.commons.io.StreamUtils;
import com.aj.android.commons.java.StringUtils;
/**
 * Collection of utilities related to Assets and Raw directory.
 * @author Akhil Jain
 *
 */
public class AssetUtils {

	/**
	 * Returns the input stream for the file that has to be open from asset directory.
	 * 
	 * @see {@link Context#getAssets()}.
	 * @see {@link AssetManager#open(String)}.
	 * 
	 * @param context -{@link Context}.
	 * @param assetFilename -{@link String} filename which needs to be open from assets directory.
	 * @return {@link InputStream} of the asset file.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 * @throws IOException
	 */
	public static InputStream getAsset(Context context, String assetFilename) throws IOException {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(assetFilename)){
			throw new NullPointerException("assetFilename cannot be null");
		}
		if(StringUtils.isEmpty(assetFilename)|| StringUtils.isBlank(assetFilename)){
			throw new IllegalArgumentException("assetFilename cannot be empty");
		}
		AssetManager mngr = context.getAssets();
		InputStream inputStream = null;
		try {
			inputStream = mngr.open(assetFilename);
			if (inputStream.available() > 0) {
				return inputStream;
			}
		} catch (IOException e) {
			throw e;
		}
		return inputStream;
	}

	/**
	 * Gets the {@link String} from asset directory.
	 * 
	 * @see {@link #getAsset(Context, String)}.
	 * @see {@link StreamUtils#convertStreamToString(InputStream)}.
	 * 
	 * @param context -{@link Context}.
	 * @param assetFilename -{@link String} filename which needs to be open from assets directory.
	 * @return {@link String} format of the file.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 * @throws IOException
	 */
	public static String getAssetToString(Context context, String assetFilename) throws IOException {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(assetFilename)){
			throw new NullPointerException("assetFilename cannot be null");
		}	
		if(StringUtils.isEmpty(assetFilename)|| StringUtils.isBlank(assetFilename)){
			throw new IllegalArgumentException("assetFilename cannot be empty");
		}
		InputStream inputStream = getAsset(context, assetFilename);
		String line = null;
		if (inputStream != null) {
			line=StreamUtils.convertStreamToString(inputStream);
		}else{
			line=null;
		}
		return line;
	}
	
	/**
	 * Gets the {@link String} file name for raw resource based on id.<br>
	 * <code>E.g - id-R.raw.rawfile</code>
	 * 
	 * @param context -{@link Context}.
	 * @param rawId -rawid of the resource.
	 * @return {@link String} name of the file.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IOException
	 */
	public static String getFileName(Context context,int rawId){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		Resources resources=context.getResources();
		String rawFilename=resources.getResourceName(rawId);
		return rawFilename;
	}
	
	
	/**
	 * Copy the file from assets to given destination file.
	 * 
	 * @see {@link StreamUtils #copy(InputStream, java.io.OutputStream)}.
	 * 
	 * @param context -{@link Context}.
	 * @param assetFilename -{@link String} filename which needs to be open from assets directory.
	 * @param destinationFile -{@link File} location where the file has to be written.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE}.
	 * @throws IOException
	 */
	public static void copyFileFromAsset(Context context, String assetFilename,File destinationFile) throws IOException {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(assetFilename)){
			throw new NullPointerException("assetFilename cannot be null");
		}
		if(StringUtils.isNull(destinationFile)){
			throw new NullPointerException("destinationFile cannot be null");
		}
		if(StringUtils.isEmpty(assetFilename)|| StringUtils.isBlank(assetFilename)){
			throw new IllegalArgumentException("assetFilename cannot be empty");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
		}
        InputStream stream = context.getAssets().open(assetFilename);
        StreamUtils.copy(stream, new FileOutputStream(destinationFile));
        stream.close();
    }
	
	
	/**
     * Get an asset using ACCESS_STREAMING mode. This provides access to files that have been bundled with an
     * application as assets -- that is, files placed in to the "assets" directory.
     * 
     * @param context -{@link Context}.
     * @param assetFilename -The name of the asset to open.
     * @return {@link String} form of the file.
     * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 * @throws IOException 
     */
    public static String getFileFromAssets(Context context, String assetFilename) throws IOException {
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(assetFilename)){
			throw new NullPointerException("assetFilename cannot be null");
		}
		if(StringUtils.isEmpty(assetFilename)|| StringUtils.isBlank(assetFilename)){
			throw new IllegalArgumentException("assetFilename cannot be empty");
		}
        return StreamUtils.convertStreamToString(context.getResources().getAssets().open(assetFilename));
    }

    /**
     * Get content from a raw resource. This can only be used with resources whose value is the name of an asset files
     * -- that is, it can be used to open drawable, sound, and raw resources; it will fail on string and color
     * resources.
     * 
     * @see {@link StreamUtils#convertStreamToString(InputStream)}.
     * 
     * @param context -{@link Context}.
     * @param resId -The resource identifier to open, as generated by the appt tool.
     * @return {@link String} name of the file.
     * 
	 * @throws NullPointerException if any of the parameters is null.
     * @throws IOException 
     */
    public static String getFileFromRaw(Context context, int resId) throws IOException {
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
        return StreamUtils.convertStreamToString(context.getResources().openRawResource(resId));
    }
    
    /**
     * Returns content of given {@link String} asset file name where each list item is a new line from the given file.
     * 
     * @see {@link StreamUtils#convertStreamToList(InputStream, String)}.
     * 
     * @param context -{@link Context}.
     * @param assetFilename -The {@link String} name of the asset to open.
     * @return {@link List} representation of content.
     * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
     * @throws IOException 
     */
    public static List<String> getFileToListFromAssets(Context context, String assetFilename) throws IOException {
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(assetFilename)){
			throw new NullPointerException("assetFilename cannot be null");
		}
		if(StringUtils.isEmpty(assetFilename)|| StringUtils.isBlank(assetFilename)){
			throw new IllegalArgumentException("assetFilename cannot be empty");
		}
        return StreamUtils.convertStreamToList(context.getResources().getAssets().open(assetFilename));
    }

    /**
     * Returns content of given raw resource where each list item is a new line from the given resource.
     * 
     * @see {@link StreamUtils#convertStreamToList(InputStream)}.
     * 
     * @param context -{@link Context}.
     * @param resId -int raw respurce id.
     * @return {@link List} -representation of content.
     * 
	 * @throws NullPointerException if any of the parameters is null.	 
     * @throws IOException 
     */
    public static List<String> getFileToListFromRaw(Context context, int resId) throws IOException {
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
        return StreamUtils.convertStreamToList(context.getResources().openRawResource(resId));
    }
    
    /**
     * Gets list of all file name present in raw directory.
     * 
     * @param context -{@link Context}.
     * @return {@link ArrayList} of {@link String} of all file names.
     * 
     * @throws NullPointerException if any of the parameters is null.	 
     */
    public static ArrayList<String> getListOfAllRawFileName(Context context){
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
    	ArrayList<String> listOfFileName=new ArrayList<>();
    	String packageName=AppUtils.getPackageName(context);
    	try {
			Class<?> rClass=Class.forName(packageName+".R");
			Class<?> classesOfR[]= rClass.getDeclaredClasses();
			Class<?> raw=null;
			boolean containsRaw=false;
			for(int j=0;j<classesOfR.length;j++){
				if(classesOfR[j].getSimpleName().contains("raw")){
					containsRaw=true;
					raw=Class.forName(classesOfR[j].getName());
				}
			}
			if(containsRaw){
				if(raw!=null){
					Field fields[]=raw.getFields();
					for(int i=0;i<fields.length;i++){
						Field field=fields[i];
						listOfFileName.add(field.getName());
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return listOfFileName;
    }
    
    /**
     * Gets list of all raw id for resources present in raw directory.
     * 
     * @param context -{@link Context}.
     * @return {@link ArrayList} of {@link Integer} of all file in raw directory.
     * 
     * @throws NullPointerException if any of the parameters is null.	 
     */
    public static ArrayList<Integer> getListOfIdOfAllRawFileName(Context context){
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
    	ArrayList<Integer> listOfFileName=new ArrayList<>();
    	String packageName=AppUtils.getPackageName(context);
    	try {
			Class<?> rClass=Class.forName(packageName+".R");
			Class<?> classesOfR[]= rClass.getDeclaredClasses();
			Class<?> raw=null;
			boolean containsRaw=false;
			for(int j=0;j<classesOfR.length;j++){
				if(classesOfR[j].getSimpleName().contains("raw")){
					containsRaw=true;
					raw=Class.forName(classesOfR[j].getName());
				}
			}
			if(containsRaw){
				if(raw!=null){
					Field fields[]=raw.getFields();
					for(int i=0;i<fields.length;i++){
						Field field=fields[i];
						listOfFileName.add(field.getInt(null));
					}
				}
			}
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
    	return listOfFileName;
    }
}
