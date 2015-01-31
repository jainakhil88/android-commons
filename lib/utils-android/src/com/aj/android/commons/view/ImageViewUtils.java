package com.aj.android.commons.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.aj.android.commons.app.ContentProviderUtils;
import com.aj.android.commons.java.StringUtils;

/**
 * Collection of method for Resizing {@link Bitmap}, calculating sample size 
 * and scaling bitmap based on various scaling options.
 *  
 * @author Akhil Jain
 *
 */
public class ImageViewUtils {
	
	/**
	 * {@link Enum} which provides scaling options for the bitmap / image.
	 *
	 */
	public static enum ScalingOptions {
		SMALLER_THAN_VIEW, ROUND_TO_CLOSEST_MATCH, LARGER_THAN_VIEW_OR_FULL_SIZE, MATCH_TO_LARGER_DIMENSION, MATCH_TO_SMALLER_DIMENSION
	}
	
	/**
	 * Gets the {@link Point} dimensions for the given imageview.
	 *
	 * @see {@link #getDimensions(ImageView, boolean)}.
	 * 
	 * @param imageView -{@link ImageView}.
	 * @return {@link Point} -point object.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Point getImageViewDimensions(ImageView imageView) {
		Point dimensions =null;
		if(StringUtils.isNull(imageView)){
			throw new NullPointerException("imageView cannot be null");
		}
		dimensions= new Point();
		dimensions.x = getDimensions(imageView, true);
		dimensions.y = getDimensions(imageView, false);
		if (dimensions.x <= 0) {
			dimensions.x = -1;
		}
		if (dimensions.y <= 0) {
			dimensions.y = -1;
		}		
		return dimensions;
	}

	/**
	 * Gets the {@link Point} dimensions for the given imageview.
	 *
	 * @see {@link ViewUtils#getParentDimensions(ViewGroup, boolean)}.
	 * 
	 * @param imageView -{@link ImageView}.
	 * @param isWidth -boolean true if getting the dimensions for the width, else false for height.   
	 * @return {@link Point} -point object.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	private static int getDimensions(ImageView imageView, boolean isWidth) {
		if(StringUtils.isNull(imageView)){
			throw new NullPointerException("imageView cannot be null");
		}
		LayoutParams params = imageView.getLayoutParams();
		if (params == null) {
			return -1;
		}
		int length = isWidth ? params.width : params.height;
		if (length == LayoutParams.WRAP_CONTENT) {
			return -1;
		} else if (length == LayoutParams.MATCH_PARENT) {
			try {
				return ViewUtils.getParentDimensions((ViewGroup) imageView.getParent(), isWidth);
			} catch (ClassCastException e) {
				return -1;
			}
		} else {
			return length;
		}
	}
	
	/**
	 *  Get the stream format for the given {@link Bitmap} image.
	 *  
	 *  
	 * @param bitmap - {@link Bitmap} object.
	 * @param format - {@link CompressFormat} format of the image.
	 * @param compressQuality - Hint to the compressor, 0-100. 
	 * 0 meaning compress for small size, 100 meaning compress for max quality. 
	 * Some formats, like PNG which is lossless, will ignore the quality setting.
	 * 
	 * @return {@link InputStream} based on provided settings
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static InputStream getInputStream(Bitmap bitmap, CompressFormat format, int compressQuality){
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		if(StringUtils.isNull(format)){
			throw new NullPointerException("format cannot be null");
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		if(format==CompressFormat.PNG){
			/*ignored for PNG*/
			bitmap.compress(format, 0 , bos); 
		}else{
			bitmap.compress(format, compressQuality , bos); 
		}
		byte[] bitmapdata = bos.toByteArray();
		ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);
		return bs;
	}
	
	/**
	 * Gets scaled {@link Bitmap} for the given {@link Uri}.
	 * <br>
	 * Scales the {@link Bitmap} to less resolution, if the required width and required height are lesser than bitmap width and height.
	 * <br>
	 * Takes aspect-ratio into consideration while scaling.
	 *  
	 *  @see {@link ContentProviderUtils#getFilePathFromUri(Context, Uri)}.
	 *  @see {@link #getScaledBitmap(String, int, int)}.
	 *  
	 * @param context -{@link Context}.
	 * @param uriPath -{@link Uri} path of the image.
	 * @param requiredWidth -int required width.
	 * @param requiredHeight -int required height.
	 * @return {@link Bitmap} -scaled bitmap.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any parameter is empty.
	 */
	public static Bitmap getScaledBitmap(Context context,Uri uriPath, int requiredWidth, int requiredHeight){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(uriPath)){
			throw new NullPointerException("uriPath cannot be null");
		}
		if(StringUtils.isEmpty(uriPath.toString())){
			throw new IllegalArgumentException("uriPath cannot be empty");
		}
		return getScaledBitmap(ContentProviderUtils.getFilePathFromUri(context, uriPath), requiredWidth, requiredHeight);
	}
	
	/**
	 * Gets scaled {@link Bitmap} for the given {@link String}.
	 * <br>
	 * Scales the {@link Bitmap} to less resolution, if the required width and required height are lesser than bitmap width and height.
	 * <br>
	 * Takes aspect-ratio into consideration while scaling.
	 *  
	 *  @see {@link #calculateInSampleSize(android.graphics.BitmapFactory.Options, int, int)}.
	 *  
	 * @param context -{@link Context}.
	 * @param filePath -{@link String} absolute file path for image.
	 * @param requiredWidth -int required width.
	 * @param requiredHeight -int required height.
	 * @return {@link Bitmap} -scaled bitmap.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap getScaledBitmap(String filePath, int requiredWidth, int requiredHeight){
		if(StringUtils.isNull(filePath)){
			throw new NullPointerException("file path cannot be null");
		}
		Bitmap resizedBitmap = null;
		// First decode with inJustDecodeBounds=true to check dimensions
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
		
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		resizedBitmap = BitmapFactory.decodeFile(filePath, options); 
		
		return resizedBitmap;   
	}
	
	/**
	 * Gets scaled {@link Bitmap} for the given {@link String}.
	 * <br>
	 * Scales the {@link Bitmap} to less resolution, if the required width and required height are lesser than bitmap width and height.
	 * <br>
	 * Takes aspect-ratio into consideration while scaling.
	 *  
	 *  @see {@link #getInputStream(Bitmap, CompressFormat, int)}.
	 *  
	 * @param bitmap -{@link Bitmap}.
	 * @param format -{@link CompressFormat}.
	 * @param quality -quality of the image.
	 * @param requiredWidth -int required width.
	 * @param requiredHeight -int required height.
	 * @return {@link Bitmap} -scaled bitmap.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap getScaledBitmapFromBitmap(Bitmap bitmap,CompressFormat format, int quality, int requiredWidth, int requiredHeight){
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		if(StringUtils.isNull(format)){
			throw new NullPointerException("format cannot be null");
		}
		Bitmap resizedBitmap = null;
		// First decode with inJustDecodeBounds=true to check dimensions
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(getInputStream(bitmap, format, quality),null,options);
		
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
		
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		resizedBitmap = BitmapFactory.decodeStream(getInputStream(bitmap, format, quality), null, options); 
		
		return resizedBitmap;   
	}
	
	/**
	 * Sample down or Increase the {@link Bitmap} exactly to given dimensions. 
	 * <br>
	 * Does <b>NOT</b> takes aspect-ratio into consideration.
	 * 
	 * @see {@link ContentProviderUtils#getRealPathFromURI(Context, Uri)}.
	 * @see {@link #getExactScaledBitmap(String, int, int)}.
	 * 
	 * @param context -{@link Context}.
	 * @param uriPath -{@link Uri} path of the image.
	 * @param requiredWidth -int required width.
	 * @param requiredHeight -int required height.
	 * @return {@link Bitmap} -exactly scaled bitmap.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any parameter is empty.
	 */
	public static Bitmap getExactScaledBitmap(Context context,Uri uriPath, int requiredWidth, int requiredHeight){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(uriPath)){
			throw new NullPointerException("uriPath cannot be null");
		}
		if(StringUtils.isEmpty(uriPath.toString())){
			throw new IllegalArgumentException("uriPath cannot be empty");
		}
		return getExactScaledBitmap(ContentProviderUtils.getFilePathFromUri(context, uriPath), requiredWidth, requiredHeight);
	}
	
	/**
	 * Sample down or Increase the {@link Bitmap} exactly to given dimensions. 
	 * <br>
	 *  Does <b>NOT</b> takes aspect-ratio into consideration.
	 * 
	 * @see {@link Bitmap#createScaledBitmap(Bitmap, int, int, boolean)}.
	 * 
	 * @param context -{@link Context}.
	 * @param filePath -{@link String} absolute file path for image.
	 * @param requiredWidth -int required width.
	 * @param requiredHeight -int required height.
	 * @return {@link Bitmap} -exactly scaled bitmap.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap getExactScaledBitmap(String filePath, int requiredWidth, int requiredHeight){
		if(StringUtils.isNull(filePath)){
			throw new NullPointerException("file path cannot be null");
		}
		Bitmap resizedBitmap = null;
		// First decode with inJustDecodeBounds=true to check dimensions
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		
		options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
		options.inJustDecodeBounds = false;
//		options.outHeight=requiredHeight;
//		options.outWidth=requiredWidth;
		resizedBitmap = BitmapFactory.decodeFile(filePath, options); 
		
		resizedBitmap=Bitmap.createScaledBitmap(resizedBitmap, requiredWidth, requiredHeight, true);
//		System.out.println("exact scale="+BitmapUtils.getBitmapDetails(resizedBitmap));
		return resizedBitmap;
	}
	
	/**
	 * Sample down or Increase the {@link Bitmap} exactly to given dimensions. 
	 * <br>
	 *  Does <b>NOT</b> takes aspect-ratio into consideration.
	 * 
	 * @see {@link #getInputStream(Bitmap, CompressFormat, int)}.
	 *  
	 * @param bitmap -{@link Bitmap}.
	 * @param format -{@link CompressFormat}.
	 * @param quality -quality of the image.
	 * @param requiredWidth -int required width.
	 * @param requiredHeight -int required height.
	 * @return {@link Bitmap} -exactly scaled bitmap.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap getExactScaledBitmapFromBitmap(Bitmap bitmap,CompressFormat format, int quality, int requiredWidth, int requiredHeight){
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		if(StringUtils.isNull(format)){
			throw new NullPointerException("format cannot be null");
		}
		Bitmap resizedBitmap = null;
		// First decode with inJustDecodeBounds=true to check dimensions
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(getInputStream(bitmap, format, quality),null,options);
		
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);
		
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		resizedBitmap = BitmapFactory.decodeStream(getInputStream(bitmap, format, quality), null, options); 
		
		resizedBitmap=Bitmap.createScaledBitmap(resizedBitmap, requiredWidth, requiredHeight, true);
		return resizedBitmap;   
	}
	
	
	/**
	 * Calculate sampling size for image.
	 * 
	 * @param options -{@link BitmapFactory.Options} is options for bitmap factoring to calculate the size;
	 * @param reqWidth -int required width for the image.
	 * @param reqHeight -int required height for the image.
	 * 
	 * @return int -sampling size for the image,for loading in memory.
	 * **/
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) 
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

//		Logger.d(TAG, "image width*height="+width+"*"+height);
//		Logger.d(TAG, "required image width*height="+reqWidth+"*"+reqHeight);
		
		if (height > reqHeight || width > reqWidth) 
		{
			if (width > height) {
				inSampleSize = Math.round((float)height / (float)reqHeight);    
			} else {
				inSampleSize = Math.round((float)width / (float)reqWidth);    
			}   
			
//			while(options.outWidth/inSampleSize/2>=reqWidth && options.outHeight/inSampleSize/2>=reqHeight)
//         	inSampleSize*=2;
			
			
			// This offers some additional logic in case the image has a strange
	        // aspect ratio. For example, a panorama may have a much larger
	        // width than height. In these cases the total pixels might still
	        // end up being too large to fit comfortably in memory, so we should
	        // be more aggressive with sample down the image (=larger
	        // inSampleSize).
	        final float totalPixels = width * height;

	        // Anything more than 2x the requested pixels we'll sample down further.
	        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

	        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) 
	        {
	            inSampleSize++;
	        }
		}
//		Logger.d(TAG, "sample size is "+inSampleSize);
		return inSampleSize;    
	}
	
	/**
	 * Gets the scaled down bitmap from the given image {@link Uri} based on given {@link ScalingOptions}.  
	 * 
	 * @see {@link ContentProviderUtils#getRealPathFromURI(Context, Uri)}.
	 * @see {@link #getBitmapBasedOnScalingOptions(String, int, int, ScalingOptions)}.
	 * 
	 * @param context -{@link Context}.
	 * @param uriPath -{@link Uri} path of the image.
	 * @param requiredWidth -int required width.
	 * @param requiredHeight -int required height.
	 * @param scalingPreference -{@link ScalingOptions} to specify on what basis images have to be scaled.
	 * @return {@link Bitmap} -scaled bitmap based on scaling options.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static Bitmap getBitmapBasedOnScalingOptions(Context context,Uri uriPath, int requiredWidth, int requiredHeight,ScalingOptions scalingPreference){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(StringUtils.isNull(uriPath)){
			throw new NullPointerException("uriPath cannot be null");
		}
		if(StringUtils.isEmpty(uriPath.toString())){
			throw new IllegalArgumentException("uriPath cannot be empty");
		}
		return getBitmapBasedOnScalingOptions(ContentProviderUtils.getFilePathFromUri(context, uriPath), requiredWidth, requiredHeight, scalingPreference);
	}
	
	/**
	 * Gets the scaled down bitmap from the given image {@link String} file path  based on given {@link ScalingOptions}.  
	 * 
	 * @param context -{@link Context}.
	 * @param filePath -{@link String} absolute file path for image.
	 * @param requiredWidth -int required width.
	 * @param requiredHeight -int required height.
	 * @param scalingPreference -{@link ScalingOptions} to specify on what basis images have to be scaled.
	 * @return {@link Bitmap} -scaled bitmap based on scaling options.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap getBitmapBasedOnScalingOptions(String filePath, int requiredWidth, int requiredHeight,ScalingOptions scalingPreference){
		if(StringUtils.isNull(filePath)){
			throw new NullPointerException("file path cannot be null");
		}
		Bitmap resizedBitmap = null;
		// First decode with inJustDecodeBounds=true to check dimensions
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		
		options.inSampleSize = calculateSampleSize(options, requiredWidth, requiredHeight, scalingPreference);
		options.inJustDecodeBounds = false;
		resizedBitmap = BitmapFactory.decodeFile(filePath, options); 
		
		return resizedBitmap;
	}
	
	/**
	 * Gets the scaled down bitmap from the given image {@link String} file path  based on given {@link ScalingOptions}.  
	 * @see {@link #getInputStream(Bitmap, CompressFormat, int)}.
	 *  
	 * @param bitmap -{@link Bitmap}.
	 * @param format -{@link CompressFormat}.
	 * @param quality -quality of the image.
	 * @param requiredWidth -int required width.
	 * @param requiredHeight -int required height.
	 * @param scalingPreference -{@link ScalingOptions} to specify on what basis images have to be scaled.
	 * @return {@link Bitmap} -scaled bitmap based on scaling options.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap getBitmapBasedOnScalingOptionsFromBitmap(Bitmap bitmap,CompressFormat format, int quality, int requiredWidth, int requiredHeight,ScalingOptions scalingPreference){
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		if(StringUtils.isNull(format)){
			throw new NullPointerException("format cannot be null");
		}
		Bitmap resizedBitmap = null;
		// First decode with inJustDecodeBounds=true to check dimensions
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(getInputStream(bitmap, format, quality),null,options);
		
		// Calculate inSampleSize
		options.inSampleSize = calculateSampleSize(options, requiredWidth, requiredHeight,scalingPreference);
		
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		resizedBitmap = BitmapFactory.decodeStream(getInputStream(bitmap, format, quality), null, options); 
		
		return resizedBitmap;   
	}
	
	
	/**
	 * Calculates a sample size that will potentially save memory and not result in a loss of quality when the image is made to fill the image view.
	 * 
	 * @param width
	 *            The image will not be scaled down to be smaller than this width. Null for no scaling by width.
	 * @param height
	 *            The image will not be scaled down to be smaller than this height. Null for no scaling by height.
	 * @param imageDimensions
	 *            The dimensions of the image, as decoded from the full image on disk.
	 * @return The calculated sample size. 1 if both height and width are null.
	 */
	
	private static int calculateSampleSize(BitmapFactory.Options options,int requiredWidth,int requiredHeight,ScalingOptions scalingPreference) {
		int widthSampleSize=calculateSampleSizeForDimension(options.outWidth, requiredWidth	, scalingPreference);
		int heightSampleSize=calculateSampleSizeForDimension(options.outHeight, requiredHeight, scalingPreference);
		return calculateOverallSampleSize(widthSampleSize, heightSampleSize, scalingPreference);
	}
	
	private static int calculateOverallSampleSize(int widthSampleSize, int heightSampleSize, ScalingOptions scalingPreference) {
		int sampleSize = 1;

		if (widthSampleSize != -1 && heightSampleSize != -1) {
			switch (scalingPreference) {
			case MATCH_TO_LARGER_DIMENSION:
			case LARGER_THAN_VIEW_OR_FULL_SIZE:
				sampleSize = Math.min(widthSampleSize, heightSampleSize);
				break;
			case ROUND_TO_CLOSEST_MATCH:
			case MATCH_TO_SMALLER_DIMENSION:
			case SMALLER_THAN_VIEW:
				sampleSize = Math.max(widthSampleSize, heightSampleSize);
				break;
			}
		} else if (widthSampleSize != -1 || heightSampleSize != -1) {
			int tempSampleSize;
			if (widthSampleSize == -1) {
				tempSampleSize = heightSampleSize;
			} else {
				tempSampleSize = widthSampleSize;
			}

			switch (scalingPreference) {
			case MATCH_TO_LARGER_DIMENSION:
			case MATCH_TO_SMALLER_DIMENSION:
			case ROUND_TO_CLOSEST_MATCH:
			case SMALLER_THAN_VIEW:
				sampleSize = tempSampleSize;
				break;
			case LARGER_THAN_VIEW_OR_FULL_SIZE:
				break;
			}
		}

		return sampleSize;
	}

	private static int calculateSampleSizeForDimension(int imageDimension, Integer boundingDimension, ScalingOptions scalingPreference) {
		int sampleSize = 1;

		if (boundingDimension == null) {
			sampleSize = -1;
		} else if (imageDimension <= boundingDimension) {
			sampleSize = 1;
		} else {
			float imageWidthToBoundsWidthRatio = (float) imageDimension / (float) boundingDimension;
			switch (scalingPreference) {
			case MATCH_TO_SMALLER_DIMENSION:
			case MATCH_TO_LARGER_DIMENSION:
			case LARGER_THAN_VIEW_OR_FULL_SIZE:
				sampleSize = (int) imageWidthToBoundsWidthRatio;
				break;
			case ROUND_TO_CLOSEST_MATCH:
				sampleSize = Math.round(imageWidthToBoundsWidthRatio);
				break;
			case SMALLER_THAN_VIEW:
				sampleSize = (int) Math.ceil(imageWidthToBoundsWidthRatio);
				break;
			}
		}

		return sampleSize;
	}
	
	
}
