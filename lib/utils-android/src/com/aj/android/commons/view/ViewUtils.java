package com.aj.android.commons.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aj.android.commons.app.VersionUtils;
import com.aj.android.commons.java.StringUtils;

public class ViewUtils {
	
	/**
	 * Sets the visibility of the provided view as visible.
	 * <br>
	 * If the parameter view is <code>null</code>, no change will be done. 
	 * 
	 * @see {@link View#VISIBLE}.
	 * @see {@link View#setVisibility(int)}.
	 *  
	 * @param view -{@link View}.
	 */
	public static void setVisible(View view){
		if(StringUtils.isNotNull(view)){
			view.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * Sets the visibility of the provided view as gone.
	 * <br>
	 * If the parameter view is <code>null</code>, no change will be done. 
	 * 
	 * @see {@link View#GONE}.
	 * @see {@link View#setVisibility(int)}.
	 *  
	 * @param view -{@link View}.
	 */
	public static void setGone(View view){
		if(StringUtils.isNotNull(view)){
			view.setVisibility(View.GONE);
		}
	}
	
	/**
	 * Sets the visibility of the provided view as invisible.
	 * <br>
	 * If the parameter view is <code>null</code>, no change will be done. 
	 * 
	 * @see {@link View#INVISIBLE}.
	 * @see {@link View#setVisibility(int)}.
	 *  
	 * @param view -{@link View}.
	 */
	public static  void setInvisible(View view){
		if(StringUtils.isNotNull(view)){
			view.setVisibility(View.INVISIBLE);
		}
	}
	
	/**
	 * Generic method to hide the input keyboard.
	 * <br>
	 * Will hide the input keyboard, if given view and context both are not <code>null</code>. 
	 * 
	 * @see {@link InputMethodManager#hideSoftInputFromInputMethod(android.os.IBinder, int)}.
	 * 
	 * @param context -{@link Context}.
	 * @param view -{@link View} from which input keyboard has to be hidden.
	 */
	public static void hideInputKeyboard(Context context,View view){
		if(StringUtils.isNotNull(context) && StringUtils.isNotNull(view)){
			InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);	
			imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
		}
	}
	
	/**
	 * Recycle all the images from the provided {@link View}  or {@link ViewGroup}.
	 * <br>
	 * Recycles {@link Bitmap} from the view.
	 * <br>
	 * Recursively traverses if the provided view is {@link ViewGroup} for recycling the image / bitmap.
	 * <br>
	 * No changes are done if the view is <code>null</code>.
	 * <br><br>
	 * <b>This operation cannot be reversed, so it should only be called if you are sure there are no further uses for the bitmap.</b>
	 * 
	 * @see {@link Bitmap#recycle()}.
	 * @param view -{@link View}.
	 */
	public static void recycleImagesFromView(View view) {
		if(StringUtils.isNotNull(view)){
			if(view instanceof ImageView){
				Drawable drawable = ((ImageView)view).getDrawable();
				if(drawable instanceof BitmapDrawable){
					BitmapDrawable bitmapDrawable = (BitmapDrawable)drawable;
					bitmapDrawable.getBitmap().recycle();
				}
			}
			if (view instanceof ViewGroup) {
				for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
					recycleImagesFromView(((ViewGroup) view).getChildAt(i));
				}
			}
		}
    }
	
	
	/**
	 *Checks whether the view is from right to left..
	 *<br>
	 *If the view is <code>null</code> then <code>false</code> will be returned.
	 *<br>
	 *If android OS / API version is less than 17 / {@link VERSION_CODES#JELLY_BEAN_MR1}, then <code>false</code> will be returned.<br>
	 *All the view before API Level 17 are left to right.
	 *
	 *@see {@link View#getLayoutDirection()}.
	 * 
	 *@param view -{@link View} for which layout needs to checked.
	 */
	@SuppressLint("NewApi")
	public static boolean isLayoutRtl(View view) {
		if (VersionUtils.isJellyBeanMR1OrHigher()) {
			if(StringUtils.isNotNull(view)){
				return view.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
			}else{
				return false;
			}
		} else {
			// All layouts are LTR before JB MR1.
			return false;
		}
	}
	
	/**
	 * Gets dimension of the viewgroup parent, whether the view is {@link LayoutParams#WRAP_CONTENT} or
	 * {@link LayoutParams#MATCH_PARENT}.
	 * 
	 * @param parent -{@link ViewGroup}.
	 * @param isWidth -boolean value to specify whether it is width or not.
	 *  
	 * @return int - {@link LayoutParams#WRAP_CONTENT} or {@link LayoutParams#MATCH_PARENT}.
	 */
	public static int getParentDimensions(ViewGroup parent, boolean isWidth) {
		LayoutParams params;
		if (parent == null || (params = parent.getLayoutParams()) == null) {
			return -1;
		}
		int length = isWidth ? params.width : params.height;
		if (length == LayoutParams.WRAP_CONTENT) {
			return -1;
		} else if (length == LayoutParams.MATCH_PARENT) {
			try {
				return getParentDimensions((ViewGroup) parent.getParent(), isWidth);
			} catch (ClassCastException e) {
				return -1;
			}
		} else {
			return length;
		}
	}
	
	/**
	 * Gets the view size for the given view.
	 * <br>
	 * If the given view is <code>null<code> then <code>-1</code>.
	 * <br>
	 * <pre>
	 *  [0]=widthOfTheView.
	 *  [1]=heightOfTheView.
	 * </pre>
	 * 
	 * @see {@link View#measure(int, int)}.
	 * 
	 * @param view -{@link View}.
	 * @return int[] -size of the view 
	 */
	public static int[] getViewSize(View view){		
		int viewDetails[]=new int[2];
		if(StringUtils.isNotNull(view)){
			view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);;
			viewDetails[0]=view.getMeasuredWidth();
			viewDetails[1]=view.getMeasuredHeight();
		}else{
			viewDetails[0]=-1;
			viewDetails[1]=-1;
		}
		return viewDetails;
	}
	
	/**
	 * Get text/ label text of the checked {@link RadioButton} from the given radio group.
	 * <br>
	 * If the radiogroup is <code>null</code>, then blank string will be returned.
	 * <br>
	 * If no {@link RadioButton} is checked, blank string will be returned.
	 * 
	 * @see {@link RadioGroup#getCheckedRadioButtonId()}.
	 * 
	 * @param radioGroup -{@link RadioGroup}.
	 * @return {@link String} text / label text of the checked radio button.
	 */
	public static String getCheckedRadioButtonText(RadioGroup radioGroup){
		String selection="";
		if(StringUtils.isNotNull(radioGroup)){
			if(radioGroup.getCheckedRadioButtonId()!=-1){
				int id= radioGroup.getCheckedRadioButtonId();
				View radioButton = radioGroup.findViewById(id);
				int radioId = radioGroup.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
				selection= (String) btn.getText();
			}
		}
		return selection;
	}
}
