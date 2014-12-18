package com.aj.android.commons.app;

import android.os.Build;
import android.os.Build.VERSION_CODES;

/**
 * Utilities for most common Android Device version related functionality.
 * @author Akhil Jain 
 *
 */
public class VersionUtils {
	
	/**
	 * Gets the device Android API Level.
	 * 
	 * @return int -API Level of the device.
	 */
	public static int getApiLevel(){
		return Build.VERSION.SDK_INT;
	}
	
	/**
	 * Return true if Device API is Level 8 / {@link VERSION_CODES#FROYO}
	 * @return boolean 
	 */
	public static boolean isFroyo() {
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.FROYO;
	}
	
	/**
	 * Return true if Device API is Level 9 / {@link VERSION_CODES#GINGERBREAD}
	 * @return boolean 
	 */
	public static boolean isGingerbread() {
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.GINGERBREAD;
	}

	/**
	 * Return true if Device API is Level 10 / {@link VERSION_CODES#GINGERBREAD_MR1}
	 * @return boolean 
	 */
	public static boolean isGingerbreadMR1() {
		return Build.VERSION.SDK_INT ==  Build.VERSION_CODES.GINGERBREAD_MR1;
	}
	
	/**
	 * Return true if Device API is Level 11 / {@link VERSION_CODES#HONEYCOMB}
	 * @return boolean 
	 */
	public static boolean isHoneycomb(){
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.HONEYCOMB;
	}
	
	/**
	 * Return true if Device API is Level 12 / {@link VERSION_CODES#HONEYCOMB_MR1}
	 * @return boolean 
	 */
	public static boolean isHoneycombMR1(){
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	/**
	 * Return true if Device API is Level 13 / {@link VERSION_CODES#HONEYCOMB_MR2}
	 * @return boolean 
	 */
	public static boolean isHoneycombMR2(){
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.HONEYCOMB_MR2;
	}
	
	/**
	 * Return true if Device API is Level 14 / {@link VERSION_CODES#ICE_CREAM_SANDWICH}
	 * @return boolean 
	 */
	public static boolean isIceCreamSandwich() {
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}
	
	/**
	 * Return true if Device API is Level 15 / {@link VERSION_CODES#ICE_CREAM_SANDWICH_MR1}
	 * @return boolean 
	 */
	public static boolean isIceCreamSandwichMR1() {
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
	}

	/**
	 * Return true if Device API is Level 16 / {@link VERSION_CODES#JELLY_BEAN}
	 * @return boolean 
	 */
	public static boolean isJellyBean() {
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN;
	}
	
	/**
	 * Return true if Device API is Level 17 / {@link VERSION_CODES#JELLY_BEAN_MR1}
	 * @return boolean 
	 */
	public static boolean isJellyBeanMR1() {
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	/**
	 * Return true if Device API is Level 18 / {@link VERSION_CODES#JELLY_BEAN_MR2}
	 * @return boolean 
	 */
	public static boolean isJellyBeanMR2() {
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2;	
	}

	/**
	 * Return true if Device API is Level 19 / {@link VERSION_CODES#KITKAT}
	 * @return boolean 
	 */
	public static boolean isKitKat() {
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
	}
	
	/**
	 * Return true if Device API is Level 20/{@link VERSION_CODES#KITKAT_WATCH}
	 * @return boolean 
	 */
	/*public static boolean isKitKatWatch() {
		return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH;
	}*/
	
	/*----------------PRE METHODS----------------*/
	/**
	 * Return true if Device API is before Level 8 / {@link VERSION_CODES#FROYO}
	 * @return boolean 
	 */
	public static boolean isPreFroyo() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO;
	}
	
	/**
	 * Return true if Device API is before Level 9 / {@link VERSION_CODES#GINGERBREAD}
	 * @return boolean
	 */
	public static boolean isPreGingerbread() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD;
	}
	
	/**
	 * Return true if Device API is before Level 10 / {@link VERSION_CODES#GINGERBREAD_MR1}
	 * @return boolean
	 */
	public static boolean isPreGingerbreadMR1() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1;
	}
	
	/**
	 * Return true if Device API is before Level 11 / {@link VERSION_CODES#HONEYCOMB}
	 * @return boolean
	 */
	public static boolean isPreHoneycomb() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;
	}
	
	/**
	 * Return true if Device API is before Level 12 / {@link VERSION_CODES#HONEYCOMB_MR1}
	 * @return boolean
	 */
	public static boolean isPreHoneycombMR1() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1;
	}
	
	/**
	 * Return true if Device API is before Level 13 / {@link VERSION_CODES#GINGERBREAD_MR2}
	 * @return boolean
	 */
	public static boolean isPreHoneycombMR2() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2;
	}
	
	/**
	 * Return true if Device API is before Level 14 / {@link VERSION_CODES#ICE_CREAM_SANDWICH}
	 * @return boolean
	 */
	public static boolean isPreIceCreamSandwich() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}
	
	/**
	 * Return true if Device API is before Level 15 / {@link VERSION_CODES#ICE_CREAM_SANDWICH_MR1}
	 * @return boolean
	 */
	public static boolean isPreIceCreamSandwichMR1() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
	}
	
	/**
	 * Return true if Device API is before Level 16 / {@link VERSION_CODES#JELLY_BEAN}
	 * @return boolean
	 */
	public static boolean isPreJellyBean() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN;
	}
	
	/**
	 * Return true if Device API is before Level 17 / {@link VERSION_CODES#JELLY_BEAN_MR1}
	 * @return boolean
	 */
	public static boolean isPreJellyBeanMR1() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1;
	}
	
	/**
	 * Return true if Device API is before Level 18 / {@link VERSION_CODES#GINGERBREAD_MR2}
	 * @return boolean
	 */
	public static boolean isPreJellyBeanMR2() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2;
	}
	
	/**
	 * Return true if Device API is before Level 19 / {@link VERSION_CODES#KITKAT}
	 * @return boolean
	 */
	public static boolean isPreKitKat() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT;
	}	
	
	/*----------------isMimimum or higher METHODS----------------*/
	/**
	 * Return true if Device API is Level 8 or higher / {@link VERSION_CODES#FROYO}
	 * @return boolean
	 */
	public static boolean isFroyoOrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.FROYO;
	}
	
	/**
	 * Return true if Device API is Level 9 or higher / {@link VERSION_CODES#GINGERBREAD}
	 * @return boolean
	 */
	public static boolean isGingerbreadOrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD;
	}
	
	/**
	 * Return true if Device API is Level 10 or higher / {@link VERSION_CODES#GINGERBREAD_MR1}
	 * @return boolean
	 */
	public static boolean isGingerbreadMR1OrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD_MR1;
	}
	
	/**
	 * Return true if Device API is Level 11 or higher / {@link VERSION_CODES#HONEYCOMB}
	 * @return boolean
	 */
	public static boolean isHoneycombOrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB;
	}
	
	/**
	 * Return true if Device API is Level 12 or higher / {@link VERSION_CODES#HONEYCOMB_MR1}
	 * @return boolean
	 */
	public static boolean isHoneycombMR1OrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB_MR1;
	}
	
	/**
	 * Return true if Device API is Level 13 or higher / {@link VERSION_CODES#HONEYCOMB_MR2}
	 * @return boolean
	 */
	public static boolean isHoneycombMR2OrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB_MR2;
	}
	
	/**
	 * Return true if Device API is Level 14 or higher / {@link VERSION_CODES#ICE_CREAM_SANDWICH}
	 * @return boolean
	 */
	public static boolean isIceCreamSandwichOrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}
	
	/**
	 * Return true if Device API is Level 15 or higher / {@link VERSION_CODES#ICE_CREAM_SANDWICH_MR1}
	 * @return boolean
	 */
	public static boolean isIceCreamSandwichMR1OrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
	}
	
	/**
	 * Return true if Device API is Level 16 or higher / {@link VERSION_CODES#JELLY_BEAN}
	 * @return boolean
	 */
	public static boolean isJellyBeanOrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN;
	}
	
	/**
	 * Return true if Device API is Level 17 or higher / {@link VERSION_CODES#JELLY_BEAN_MR1}
	 * @return boolean
	 */
	public static boolean isJellyBeanMR1OrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1;
	}
	
	/**
	 * Return true if Device API is Level 18 or higher / {@link VERSION_CODES#JELLY_BEAN_MR2}
	 * @return boolean
	 */
	public static boolean isJellyBeanMR2OrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR2;
	}
	
	/**
	 * Return true if Device API is Level 19 or higher / {@link VERSION_CODES#KITKAT}
	 * @return boolean
	 */
	public static boolean isKitkatOrHigher(){
		return Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT;
	}
	
	/*----------------Variant METHODS----------------*/
	/**
	 * Return true if Device API is Level 9 or 10  / {@link VERSION_CODES#GINGERBREAD} or {@link VERSION_CODES#GINGERBREAD_MR1} 
	 * @return boolean
	 */
	public static boolean isGingerbreadVariant(){
		return isGingerbread()||isGingerbreadMR1();
	}
	
	/**
	 * Return true if Device API is Level 11,12 or 13  / {@link VERSION_CODES#HONEYCOMB}, {@link VERSION_CODES#HONEYCOMB_MR1}
	 *  or {@link VERSION_CODES#HONEYCOMB_MR2} 
	 * @return boolean
	 */
	public static boolean isHoneycombVariant(){
		return isHoneycomb()||isHoneycombMR1()||isHoneycombMR2();
	}
	
	/**
	 * Return true if Device API is Level 14 or 15  / {@link VERSION_CODES#ICE_CREAM_SANDWICH} or {@link VERSION_CODES#ICE_CREAM_SANDWICH_MR1}
	 * @return boolean
	 */
	public static boolean isIceCreamSandwichVariant(){
		return isIceCreamSandwich()||isIceCreamSandwichMR1();
	}
	
	/**
	 * Return true if Device API is Level 16,17 or 18  / {@link VERSION_CODES#JELLY_BEAN}, {@link VERSION_CODES#JELLY_BEAN_MR1}
	 *  or {@link VERSION_CODES#JELLY_BEAN_MR2} 
	 * @return boolean

	 */
	public static boolean isJellyBeanVariant(){
		return isJellyBean()||isJellyBeanMR1()||isJellyBeanMR2();
	}
}