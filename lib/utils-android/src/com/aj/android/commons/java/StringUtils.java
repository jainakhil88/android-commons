package com.aj.android.commons.java;

import java.net.URL;
import java.util.Locale;

import com.aj.android.commons.net.NetworkUtils;

import android.text.TextUtils;
import android.util.Patterns;

public class StringUtils {

	/**
	 * Check whether given {@link Object} is <code>null</code> or not.
	 * <br>
	 * Returns <b>true</b> is given object is <code>null</code>. 
	 * 
	 * @param object -{@link Object}
	 * @return boolean -true if object is <code>null</code>, else false.
	 */
	public static boolean isNull(Object object){
		return (object==null);
	}
	
	/**
	 * Check whether given {@link Object} is not <code>null</code> or not.
	 * <br>
	 * Returns <b>true</b> is given object is not <code>null</code>. 
	 * 
	 * @see {@link #isNull(Object)}.
	 * 
	 * @param object -{@link Object}
	 * @return boolean -true if object is not <code>null</code>, else false.
	 */
	public static boolean isNotNull(Object object){
		return !(isNull(object));
	}
	
	/**
	 * Returns true if the string is null or 0-length.
	 * 
	 * @param text -{@link CharSequence} object.
	 * @return boolean 
	 */
	public static boolean isEmpty(CharSequence text){
		return TextUtils.isEmpty(text);
	}
	
	/**
	 * Returns true if the given {@link CharSequence} is valid email.
	 * 
	 * @see {@link Patterns#EMAIL_ADDRESS}.
	 * 
	 * @param email -{@link CharSequence} object.
	 * @return boolean
	 */
	public static boolean isEmailValid(CharSequence email){
		if(isEmpty(email) || isBlank(email)){
			return false;
		}
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	
	/**
	 * Returns true if the given {@link CharSequence} is valid Network {@link URL}.
	 * <br>
	 * Internally calls {@link NetworkUtils#isValidURL(String)}.
	 * @param url -network url in {@link CharSequence} / {@link String} form.
	 * @return boolean
	 */
	public static boolean isURLValid(CharSequence url){
		if(isEmpty(url) || isBlank(url)){
			return false;
		}
		return NetworkUtils.isValidURL(url.toString());
	}
	
	 /**
     * Checks if a CharSequence is whitespace, empty ("") or null.
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param text -the {@link CharSequence} to check, may be null.
     * @return boolean -{@code true} if the CharSequence is null, empty or whitespace.
     */
    public static boolean isBlank(CharSequence text) {
        if (isEmpty(text)) {
            return true;
        }
        int strLen=text.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Trims leading and trailing spaces from the given text.
     * <br>
     * Trims the spaces in the same string.
     * <br>
     * No change if the input is blank or <code>null</code>.
     * @param text -{@link CharSequence} object.  
     */
    public static void trimWhiteSpace(CharSequence text)
    {
    	if(isEmpty(text) || isBlank(text)){
    		return;
    	}
    		
    	//For left trim
    	String trimmed=text.toString().replaceAll("^\\s+", "");

    	//For right trim
    	trimmed.replaceAll("\\s+$", "");
    }
    
    
    /**
     * Trim leading whitespace from the given String.
     *<br>
     * Returns a new {@link String} object.
     *
     * @param text -the String to trim.
     * @return {@link String} -the trimmed String.
     * @see Character#isWhitespace
     */
    public static String trimLeadingWhitespace(CharSequence text) {
        if (isEmpty(text)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(text);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * Trim trailing whitespace from the given String.
     *<br>
     * Returns a new {@link String} object.
     * @param text -the {@link CharSequence} to trim.
     * @return {@link String} -the trimmed String.
     * @see Character#isWhitespace
     */
    public static String trimTrailingWhitespace(CharSequence text) {
        if (isEmpty(text)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(text);
        while (sb.length() > 0
                && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    
    /**
     * Gets the position of given character from the starting of provided string, based on given nth occurrence.
     *  
     * @param text -{@link String} input.
     * @param character -char which needs to be searched.
     * @param occurence -int, the occurrence number of character.
     * @param lookFromLast -boolean, if set to true,will look for given character from back of string.
     * @return int -position of character in given input.
     */
    public static int nthOccurenceOfCharacter(CharSequence text,char character,int occurence,boolean lookFromLast)
    {
    	if(isEmpty(text)){
    		return -1;
    	}
    	String str=text.toString();
    	if (text == null || occurence < 1){
    		return -1;
    	}
    	if(lookFromLast){
    		//look for given character from back of string
    		int pos = str.length();
            while (occurence-- > 0 && pos != -1){
                pos = str.lastIndexOf(character, pos - 1);
            }
            return pos;
    	}
    	else{
    		//look for given character from start of string 
    		int pos = str.indexOf(character, 0);
    		while (occurence-- > 0 && pos != -1){
    			pos = str.indexOf(character, pos+1);
    		}    		
    		return pos+1;
    	}
    }
    
    /**
     * Gets the position of given character from the back of the provided string, based on given nth occurence.
     * <br>
     *  @see #nthOccurenceOfCharacterFromLast(CharSequence, char, int) for getting occurence from back of string.
     *  
     * @param text -{@link String} input.
     * @param character -char which needs to be searched.
     * @param occurence -int, the occurence number of chracter.
     * @return int -position of character in given input.
     */
 /*   public static int nthOccurenceOfCharacterFromLast(CharSequence text,char c,int position)
    {
    	if(isEmpty(text)){
    		return -1;
    	}
    	
    	String str=text.toString();
    	if (text == null || position < 1){
    		return -1;
    	}
        int pos = str.length();
        while (position-- > 0 && pos != -1){
            pos = str.lastIndexOf(c, pos - 1);
        }
        return pos;
    }*/
    
    /***
     * Gets the position of given search string from the starting of provided string, based on given nth occurrence.
     * 
     * @param text -the {@link String} input.
     * @param searchString -{@link String} to be searched.
     * @param occurrence -int, the occurrence number of String.
     * @return int -start position of string in given input based on the occurrence.
     */
    public static int nthIndexOfString(String text, String searchString, int occurrence) {
    	if(isEmpty(text)||isEmpty(searchString)){
    		return -1;
    	}
    	
        int index = text.indexOf(searchString);
        if (index == -1) return -1;
        
        for (int i = 1; i < occurrence; i++) {
            index = text.indexOf(searchString, index + 1);
            if (index == -1) return -1;
        }
        return index;
    }
    
    /**
     * Gets the position of given search string from the starting of provided string, 
     * based on given nth occurrence ignoring the case..
     * <br>
     * Internally calls {@link #nthIndexOfString(String, String, int)}
     * 
     * @param text -the {@link String}  input.
     * @param searchString -{@link String} to be searched.
     * @param occurrence -int, the occurrence number of String.
     * @param locale -{@link Locale} object.
     * @return int -start position of string in given input based on the occurrence.
     */
	public static int nthIndexOfStringIgnoreCase(String text, String searchString, int occurrence,Locale locale) {
		return nthIndexOfString(text.toLowerCase(locale), searchString.toLowerCase(locale), occurrence);
	}
	
	/**
	 * Makes given input text to camel case.
	 * 
	 * @param locale -{@link Locale} object.
	 * @param inputs -{@link String} variable length argument.
	 * @return {@link String} -single concatenated camel case text.
	 */
	public static String toCamelCase(Locale locale,String... inputs){
		if(inputs==null){
			return null;
		}
		String resultString="";
		for(int i=0;i<inputs.length;i++){
			if(i>0){
				resultString+=" ";
			}
			String[] tempArray=inputs[i].split(" ");
			for(int j=0;j<tempArray.length;j++){
				if(j>0){
					resultString+=" ";
				}
				for (int k = 0; k < tempArray[j].length(); k++){  
					String next = tempArray[j].substring(k, k + 1);
					if (k == 0){  
						resultString += next.toUpperCase(locale);  
					} else {  
						resultString += next.toLowerCase(locale);  
					}  
				}
			}
		}
//		System.out.println(resultString);
		return resultString;  
	} 
	
	
	/**
	 * Returns the string, the first char lowercase.
	 * <br>
	 * Changes are made to same string.
	 * @param text -the {@link CharSequence} input.
	 * @return {@link String} -with first character as lowercase.
	 */
	public static String asLowerCaseFirstChar(CharSequence text) {
		if ((text == null) || (text.length() == 0)) {
			return text.toString();
		}
		return Character.toLowerCase(text.charAt(0))
				+ (text.length() > 1 ? ((String) text).substring(1) : "");
	}

	/**
	 * Returns the string, the first char uppercase.
	 * <br>
	 * Changes are made to same string.
	 * @param text -the {@link CharSequence} input.
	 * @return {@link String} -with first character as uppercase.
	 */
	public static String asUpperCaseFirstChar(String target) {

		if ((target == null) || (target.length() == 0)) {
			return target; // You could omit this check and simply live with an
							// exception if you like
		}
		return Character.toUpperCase(target.charAt(0))
				+ (target.length() > 1 ? target.substring(1) : "");
	}
	
	/**
	 * Converts given byte array to hex decimal string.
	 * 
	 * @param data -byte[] data.
	 * @return {@link String} hex decimal text.
	 */
	public static String convertToHex(byte[] data) {
		StringBuilder buf = new StringBuilder();
		for (byte b : data) {
			int halfbyte = (b >>> 4) & 0x0F;
			int two_halfs = 0;
			do {
				buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
				halfbyte = b & 0x0F;
			} while (two_halfs++ < 1);
		}
		return buf.toString();
	}
	
	/**
	 * Appends the provided {@link StringBuilder} with new-line character (<code>\n</code>).
	 * <br>
	 * Returns <code>null</code> if the parameter is <code>null</code>.
	 * @param builder -{@link StringBuilder}.
	 * @return {@link StringBuilder} -same builder object append.
	 */
	public static StringBuilder appendNewLine(StringBuilder builder){
		if(StringUtils.isNull(builder)){
			return builder;
		}
		return builder.append("\n");
	}
}	
	