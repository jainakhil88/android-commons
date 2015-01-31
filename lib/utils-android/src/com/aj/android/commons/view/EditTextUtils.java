package com.aj.android.commons.view;

import android.widget.EditText;

import com.aj.android.commons.java.StringUtils;
/**
 * Common EditText related utilities from checking for empty EditText and other as 
 * validating whether data is within range or not and many others. 
 * 
 * @author Akhil Jain.
 *
 */
public class EditTextUtils {
	
	/**
	 * Checks whether the {@link EditText} has any text input or not.
	 * <br>
	 * <b>If the view is null, true is returned</b>. 
	 * @param editText -{@link EditText}.
	 * @return boolean -true if editText is <code>null</code> or there is no input, else false if text is entered.
	 */
	public static boolean isEditTextEmpty(EditText editText){
		if(StringUtils.isNull(editText) || StringUtils.isEmpty(editText.getText().toString())){
			return true;
		}
		return false;
	}
	
	/**
	 * Clear the contents of {@link EditText}.
	 * 
	 * @param editText -{@link EditText}.
	 */
	public static void clearEditText(EditText editText){
		if(editText!=null){
			editText.setText("");
		}
	}
	
	/**
	 * Returns {@link String} from {@link EditText} text.
	 * <br>
	 * <b>If the view component is null, null will be returned</b>.
	 * @param editText -{@link EditText}.
	 * @return boolean -{@link String} value of edit text, if view is not null.
	 */
	public static String getString(EditText editText){
		String text=null;
		if(editText!=null){
			text=editText.getEditableText().toString();
		}
		return text;
	}
	
	/***
	 * Parse the input {@link String} text from {@link EditText} to {@link Integer}.
	 * <br>
	 * <b>If the view component is null or unable to parse the string, null will be returned</b>.
	 *  
	 *  @see {@link Integer#parseInt(String)}.
	 *  
	 * @param editText -{@link EditText}.
	 * @return {@link Integer} -parsed integer value or null.
	 */
	public static Integer getInteger(EditText editText)
	{
		Integer value=null;
		if(editText!=null){
			try{
			value=Integer.parseInt(getString(editText));
			}catch(Exception e){
				value=null;
			}
		}
		return value;
	}
	
	/***
	 * Parse the input {@link String} text from {@link EditText} to {@link Long}.
	 * <br>
	 * <b>If the view component is null or unable to parse the string, null will be returned</b>.
	 *  
	 *  @see {@link Long#parseLong(String)}.
	 *  
	 * @param editText -{@link EditText}.
	 * @return {@link Long} -parsed long value or null.
	 */
	public static Long getLong(EditText editText){
		Long value=null;
		if(editText!=null){
			try{
				value=Long.parseLong(getString(editText));
			}catch(Exception e){
				value=null;
			}
		}
		return value;
	}
	
	
	/***
	 * Parse the input {@link String} text from {@link EditText} to {@link Double}.
	 * <br>
	 * <b>If the view component is null or unable to parse the string, null will be returned</b>.
	 *  
	 *  @see {@link Double#parseDouble(String)}.
	 *  
	 * @param editText -{@link EditText}.
	 * @return {@link Double} -parsed double value or null.
	 */
	public static Double getDouble(EditText editText){
		Double value=null;
		if(editText!=null){	
			try{
				value=Double.parseDouble(getString(editText));
			}catch(Exception e){
				value=null;
			}	
		}
		return value;
	}

	
	/**
	 * Check whether the input text in {@link EditText} is valid email or not.
	 * <br>
	 * <b>If the view component is null, false will be returned</b>.
	 * 
	 * @see {@link StringUtils#isEmailValid(CharSequence)}.
	 * 
	 * @param editText -{@link EditText}.
	 * @return boolean -true if the provided text is valid email, else false.
	 * 
	 **/
	public static boolean isEmailValid(EditText editText){
		boolean isEmailValid=false;
		if(editText!=null){
			isEmailValid=StringUtils.isEmailValid(getString(editText));
		}
		return isEmailValid;
	}
	
	/**
	 * Check whether the given input in the within the range of minimum and maximum.
	 * <br>
	 * <b>If the view component is null, false will be returned</b>.
	 * 
	 * @see {@link #getLong(EditText)}.
	 * 
	 * @param editText -{@link EditText}.
	 * @param min -long minimum value.
	 * @param max -long maximum value.
	 * @return boolean -returns true if the value is between range, else false.
	 */
	public static boolean isWithinRange(EditText editText, long min, long max){
		boolean inRange=false;
		if(editText!=null){
			Long value=getLong(editText);
			if(value!=null){
				if(value>=min &&value<=max){
					inRange=true;
				}
			}
		}
		return inRange;
	}
	
	/**
	 * Check whether the given input in the within the range of minimum and maximum.
	 * <br>
	 * <b>If the view component is null, false will be returned</b>.
	 * 
	 * @see {@link #getDouble(EditText)}.
	 * 
	 * @param editText -{@link EditText}.
	 * @param min -double minimum value.
	 * @param max -double maximum value.
	 * @return boolean -returns true if the value is between range, else false.
	 */
	public static boolean isWithinRange(EditText editText, double min, double max){
		boolean inRange=false;
		if(editText!=null){
			Double value=getDouble(editText);
			if(value!=null){
				if(value>=min &&value<=max){
					inRange=true;
				}
			}
		}
		return inRange;
	}
	
}
