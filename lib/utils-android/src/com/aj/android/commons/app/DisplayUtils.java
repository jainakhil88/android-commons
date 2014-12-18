package com.aj.android.commons.app;

import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.aj.android.commons.java.StringUtils;

public class DisplayUtils {

	/**
	 * Returns the screen size of device.
	 * 
	 * @see {@link WindowManager#getDefaultDisplay()}.
	 * @see {@link #getScreenRawSize(Display)}.
	 * 
	 * @param context -{@link Context}.
	 * @return {@link Point} - point.
	 * 
	 */
	public static Point getScreenRawSize(Context context) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		return getScreenRawSize(display);
	}
	
	/**
	 * Gets the screen size from the given display.
	 * 
	 * @param display -{@link Display}.
	 * @return {@link Point} - point.
	 */
	@SuppressLint("NewApi")
	public static Point getScreenRawSize(Display display) {
		if(StringUtils.isNull(display)){
			throw new NullPointerException("display cannot be null");
		}
		if (VersionUtils.isJellyBeanMR1OrHigher()) {
			Point outPoint = new Point();
			DisplayMetrics metrics = new DisplayMetrics();
			display.getRealMetrics(metrics);
			outPoint.x = metrics.widthPixels;
			outPoint.y = metrics.heightPixels;
			return outPoint;
		} else {
			Point outPoint = new Point();
			Method mGetRawH;
			try {
				mGetRawH = Display.class.getMethod("getRawHeight");
				Method mGetRawW = Display.class.getMethod("getRawWidth");
				outPoint.x = (Integer) mGetRawW.invoke(display);
				outPoint.y = (Integer) mGetRawH.invoke(display);
				return outPoint;
			} catch (Throwable e) {
				return new Point(0, 0);
			}
		}
	}
	
	/**
	 * Gets the action bar height in dp.
	 * <br>
	 * For devices before {@link VERSION_CODES#HONEYCOMB} / API Level 11,
	 * It is always fixed value.
	 * 
	 * @param context -{@link Context}.
	 * @return int - density pixels height for action bar.
	 */
	public static int getActionBarHeightInDp(Context context) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		int actionBarHeight = 0;
		TypedValue tv = new TypedValue();
//		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		if (Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv,true)){
				actionBarHeight = (int) TypedValue.complexToFloat(tv.data);
			}
		} else {
			tv.data = 48;
			actionBarHeight = (int) TypedValue.complexToFloat(tv.data);
		}
		return actionBarHeight;
	}

	/**
	 * Gets the action bar height in pixels.
	 * <br>
	 * For devices before {@link VERSION_CODES#HONEYCOMB} / API Level 11,
	 * It is always fixed value.
	 * 
	 * @param context -{@link Context}.
	 * @return int - pixels height for action bar.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int getActionBarHeight(Context context) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		int actionBarHeight = 0;
		TypedValue tv = new TypedValue();
		final DisplayMetrics dm = context.getResources().getDisplayMetrics();
		if (Build.VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv,true)){
				actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, dm);
			}
		} else {
			tv.data = 48;
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,dm);
		}
		return actionBarHeight;
	}

	/**
	 * Gets the status bar height in pixels.
	 * 
	 * @param context -{@link Context}.
	 * @return int - pixels height for status bar.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int getStatusBarHeight(Context context) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * Gets the system bar height in pixels.
	 * 
	 * @param context -{@link Context}.
	 * @return int - pixels height for system bar.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int getSystemBarHeight(Context context) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		int result = 0;
		int resourceId = context.getResources().getIdentifier("system_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * Gets the status bar height in dp.
	 * 
	 * @param context -{@link Context}.
	 * @return int - dp height for status bar.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int getStatusBarHeightInDp(Context context) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResourceValue(context, resourceId);
		}
		return result;
	}

	/**
	 * Gets the system bar height in dp.
	 * 
	 * @param context -{@link Context}.
	 * @return int - dp height for system bar.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int getSystemBarHeightInDp(Context context) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		int result = 0;
		int resourceId = context.getResources().getIdentifier("system_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResourceValue(context, resourceId);
		}
		return result;
	}

	/**
	 * Get resource value from the resource id.
	 *  
	 * @param context -{@link Context}.
	 * @return int - dp height for system bar.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int getResourceValue(Context context, int resId) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		TypedValue value = new TypedValue();
		context.getResources().getValue(resId, value, true);
		return (int) TypedValue.complexToFloat(value.data);
	}

	/**
	 * Converts dp to pixels.
	 * 
	 * @param context -{@link Context}.
	 * @param dp - int dp.
	 * @return int - pixels.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int dpToPx(Context context, int dp) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		return (int) ((dp * context.getResources().getDisplayMetrics().density) + 0.5);
	}

	/**
	 * Converts pixels to dp.
	 * 
	 * @param context -{@link Context}.
	 * @param px - int pixels.
	 * @return int - dp.
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static int pxToDp(Context context, int px) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		return (int) ((px / context.getResources().getDisplayMetrics().density) + 0.5);
	}

	/**
	 * Gets the display density for the device. 
	 * 
	 * @see {@link DisplayMetrics#density}.
	 * 
	 * @param context -{@link Context}.
	 * @return float - dispaly density for the device.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static float getDisplayDensity(Context context) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.density;
	}
	
	/**
	 * Gets the orientation for the device. 
	 * 
	 * @see {@link Context#getResources()}.
	 * @see {@link Configuration#orientation}.
	 * 
	 * @param context -{@link Context}.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static boolean isLandscape(Context context) {
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
	
	/**
	 * Gets the display pixel width for the device. 
	 * 
	 * @see {@link #getScreenRawSize(Context)}.
	 * 
	 * @param context -{@link Context}.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
    public static int getDisplayPixelWidth(Context context) {
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
        Point size = getScreenRawSize(context);
        return (size.x);
    }

	/**
	 * Gets the display pixel height for the device. 
	 * 
	 * @see {@link #getScreenRawSize(Context)}.
	 * 
	 * @param context -{@link Context}.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
    public static int getDisplayPixelHeight(Context context) {
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
        Point size = getScreenRawSize(context);
        return (size.y);
    }
    
    
}
