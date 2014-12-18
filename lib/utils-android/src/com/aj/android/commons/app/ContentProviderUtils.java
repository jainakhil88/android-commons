package com.aj.android.commons.app;

import java.io.File;
import java.net.URI;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

import com.aj.android.commons.io.FileUtils;
import com.aj.android.commons.java.StringUtils;

/**
 * Collection of utilities related to Android {@link ContentProvider}.
 * @author Akhil Jain
 *
 */
public class ContentProviderUtils {

	
	//get file name from uri
	//get uri from file path
	//other utils to get all gallery images uri
	
	
	/**
	 * Get the file path from {@link Uri}. It works with new Storage Provider APIs, 
	 * which is available from {@link VERSION_CODES#KITKAT} / API Level 19.
	 * 
	 * @see {@link ContentResolver#query(Uri, String[], String, String[], String)}.
	 * 
	 * @param context -{@link Context}.
	 * @param uriPath -{@link Uri} Media content Uri.
	 * @return {@link String} name of the file.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static String getFilePathFromUri( Context context, Uri uriPath ) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(uriPath)){
			throw new NullPointerException("uriPath cannot be null");
		}
		if(StringUtils.isEmpty(uriPath.toString())){
			throw new IllegalArgumentException("uriPath cannot be empty");
		}
//		if(VersionUtils.isKitkatOrHigher()){
//			return getPath(context, uriPath);
//		}else{
			Cursor cur = null;
			String path = null;
			if(!uriPath.getScheme().contains("content")){				
				path=new File(URI.create(uriPath.toString())).getAbsolutePath();
			}else{
				try {
					String[] projection = { MediaColumns.DATA };
					cur = context.getContentResolver().query( uriPath, projection, null, null, null );
					
					if( cur != null && cur.getCount() != 0 ) {
						cur.moveToFirst();
						path = cur.getString( cur.getColumnIndexOrThrow(MediaColumns.DATA) );
					}
				} catch (Exception e) {
					throw e;
				} finally {
					if( cur != null && !cur.isClosed() )
						cur.close();
				}
			}
			return path;
//		}
	}
	
		
	
	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri The Uri to query.
	 * @author paulburke
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("NewApi")
	private static String getPath(final Context context, final Uri uri) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(uri)){
			throw new NullPointerException("uri cannot be null");
		}
		if(StringUtils.isEmpty(uri.toString())){
			throw new IllegalArgumentException("uri cannot be empty");
		}
		
	    // DocumentProvider
	    if (VersionUtils.isKitkatOrHigher() && isDocumentUri(context, uri)) {
	        // ExternalStorageProvider
	        if (isExternalStorageDocument(uri)) {
	            final String docId = getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            if ("primary".equalsIgnoreCase(type)) {
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }

	            // TODO handle non-primary volumes
	        }
	        // DownloadsProvider
	        else if (isDownloadsDocument(uri)) {

	            final String id = getDocumentId(uri);
	            final Uri contentUri = ContentUris.withAppendedId(
	                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

	            return getDataColumn(context, contentUri, null, null);
	        }
	        // MediaProvider
	        else if (isMediaDocument(uri)) {
	            final String docId = getDocumentId(uri);
	            final String[] split = docId.split(":");
	            final String type = split[0];

	            Uri contentUri = null;
	            if ("image".equals(type)) {
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } else if ("video".equals(type)) {
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } else if ("audio".equals(type)) {
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }

	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] {
	                    split[1]
	            };

	            return getDataColumn(context, contentUri, selection, selectionArgs);
	        }
	    }
	    // MediaStore (and general)
	    else if ("content".equalsIgnoreCase(uri.getScheme())) {
	        return getDataColumn(context, uri, null, null);
	    }
	    // File
	    else if ("file".equalsIgnoreCase(uri.getScheme())) {
	        return uri.getPath();
	    }

	    return null;
	}
	
	/**
     * Test if the given URI represents a {@link Document} backed by a
     * {@link DocumentsProvider}.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT) 
    @SuppressLint("NewApi")
    private static boolean isDocumentUri(Context context, Uri uri) {
    	String PROVIDER_INTERFACE = "android.content.action.DOCUMENTS_PROVIDER";
    	String PATH_DOCUMENT = "document";
        final List<String> paths = uri.getPathSegments();
        if (paths.size() < 2) {
            return false;
        }
        if (!PATH_DOCUMENT.equals(paths.get(0))) {
            return false;
        }

        final Intent intent = new Intent(PROVIDER_INTERFACE);
        final List<ResolveInfo> infos = context.getPackageManager().queryIntentContentProviders(intent, 0);
        for (ResolveInfo info : infos) {
            if (uri.getAuthority().equals(info.providerInfo.authority)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Extract the {@link Document#COLUMN_DOCUMENT_ID} from the given URI.
     */
    private static String getDocumentId(Uri documentUri) {
    	String PATH_DOCUMENT = "document";
        final List<String> paths = documentUri.getPathSegments();
        if (paths.size() < 2) {
            throw new IllegalArgumentException("Not a document: " + documentUri);
        }
        if (!PATH_DOCUMENT.equals(paths.get(0))) {
            throw new IllegalArgumentException("Not a document: " + documentUri);
        }
        return paths.get(1);
    }

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context -{@link Context}.
	 * @param uri -The Uri to query.
	 * @param selection -(Optional) Filter used in the query.
	 * @param selectionArgs -(Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	private static String getDataColumn(Context context, Uri uri, String selection,String[] selectionArgs) {
	    Cursor cursor = null;
	    final String column = "_data";
	    final String[] projection = {
	            column
	    };

	    try {
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
	                null);
	        if (cursor != null && cursor.moveToFirst()) {
	            final int column_index = cursor.getColumnIndexOrThrow(column);
	            return cursor.getString(column_index);
	        }
	    } finally {
	        if (cursor != null)
	            cursor.close();
	    }
	    return null;
	}


	/**
	 * Checks whether the provided {@link Uri} is from external storage.
	 * 
	 * @param uri -{@link Uri} to check.
	 * @return boolean -returns true if the uri is from external storage, else false.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		if(StringUtils.isNull(uri)){
			throw new NullPointerException("uri cannot be null");
		}
		if(StringUtils.isEmpty(uri.toString())){
			throw new IllegalArgumentException("uri cannot be empty");
		}
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * Checks whether the provided {@link Uri} is from download document provider.
	 * 
	 * @param uri -{@link Uri} to check.
	 * @return boolean -returns true if the uri is download document, else false.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		if(StringUtils.isNull(uri)){
			throw new NullPointerException("uri cannot be null");
		}
		if(StringUtils.isEmpty(uri.toString())){
			throw new IllegalArgumentException("uri cannot be empty");
		}
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * Checks whether the provided {@link Uri} is media document.
	 * 
	 * @param uri -{@link Uri} to check.
	 * @return boolean -returns true if the uri is media document, else false.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static boolean isMediaDocument(Uri uri) {
		if(StringUtils.isNull(uri)){
			throw new NullPointerException("uri cannot be null");
		}
		if(StringUtils.isEmpty(uri.toString())){
			throw new IllegalArgumentException("uri cannot be empty");
		}
	    return "com.android.providers.media.documents".equals(uri.getAuthority());
	}
	
	/**
	 *Gets the {@link String} file name from the {@link Uri}.
	 * 
	 * @param uri -{@link Uri} to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static String getFileNameFromUri(Context context, Uri uri){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(uri)){
			throw new NullPointerException("uri cannot be null");
		}
		if(StringUtils.isEmpty(uri.toString())){
			throw new IllegalArgumentException("uri cannot be empty");
		}
		String fileName=null;
		String filePath=getFilePathFromUri(context, uri);
		if(StringUtils.isNotNull(filePath)){
			FileUtils.getFileName(filePath);
		}
		return fileName;
	}
}
