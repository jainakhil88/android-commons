package com.aj.android.commons.app;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

import com.aj.android.commons.exception.PermissionNotDefinedException;
import com.aj.android.commons.java.StringUtils;

/**
 * Collection of utilities to get Android Device related information.
 * 
 * @author Akhil Jain
 *
 */
public class DeviceUtils {

	/**
	 * Gets the IMEI number for the phone.<br>
	 * 
	 * @see  {@link TelephonyManager#getDeviceId()}.
	 * 
	 * @param context -{@link Context}.
	 * @return {@link String} -IMEI no of phone.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission #READ_PHONE_STATE} is not defined in manifest. 
	 */
	public static String getIMEINumber(Context context){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.READ_PHONE_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.READ_PHONE_STATE);
		}
			
		TelephonyManager telephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyMgr.getDeviceId();
	}

	/**
	 * Gets the Device ID/ {@link Secure#ANDROID_ID} for the phone.<br>
	 *
	 * @see {@link Secure#getString(android.content.ContentResolver, String)}.
	 * @see {@link Secure#ANDROID_ID}.
	 * 
	 * @param context -{@link Context}.
	 * @return {@link String} -device id of phone.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */	
	public static String getDeviceId(Context context){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID); 
	}

	/**
	 * Gets the WIFI MAC address for the WIFI.<br>
	 * 
	 * @see {@link WifiManager#getConnectionInfo()}.
	 * @see {@link WifiInfo#getMacAddress()}.
	 * 
	 * @param context -{@link Context}.
	 * @return {@link String} MAC Address of WIFI.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission #ACCESS_WIFI_STATE} is not defined in manifest.
	 */	
	public static String getWLANMACAddress(Context context){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_WIFI_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_WIFI_STATE);
		}
		WifiManager wm = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		return wm.getConnectionInfo().getMacAddress();
	}
	
	/**
	 * Gets the Bluetooth MAC address for the Bluetooth.<br>
	 * 
	 * @see {@link BluetoothAdapter#getAddress()}.
	 * 
	 * @param context -{@link Context}.
	 * @return {@link String} MAC Address of Bluetooth.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission #BLUETOOTH} is not defined in manifest.
	 */	
	public static String getBTMACAddress(Context context){
		if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.BLUETOOTH)){
			throw new PermissionNotDefinedException(android.Manifest.permission.BLUETOOTH);
		}
		BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	return bluetoothAdapter.getAddress();
	}
	
	/***
	 * Gets Pseudo ID of the Device.<br>
	 * 
	 * @return {@link String} Pseudo ID of the device.
	 */
	public static String getPseudoUnqiueID(){
		String pseudoUniqueID = "35" + //we make this look like a valid IMEI
	        	Build.BOARD.length()%10+ Build.BRAND.length()%10 + 
	        	Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 + 
	        	Build.DISPLAY.length()%10 + Build.HOST.length()%10 + 
	        	Build.ID.length()%10 + Build.MANUFACTURER.length()%10 + 
	        	Build.MODEL.length()%10 + Build.PRODUCT.length()%10 + 
	        	Build.TAGS.length()%10 + Build.TYPE.length()%10 + 
	        	Build.USER.length()%10 ; //13 digits
		return pseudoUniqueID;
	}
	
	/**
     * Checks camera availability based on API level.
     *<br>
     * Change "android.hardware.camera.front" and "android.hardware.camera.any" to
     *     {@link PackageManager#FEATURE_CAMERA_FRONT} and {@link PackageManager#FEATURE_CAMERA_ANY},
     *     respectively, once they become accessible or minSdk version is incremented.
     *
     *@see {@link PackageManager#hasSystemFeature(String)}.
     *
	 * @param context -{@link Context}.
     * @return boolean - true If the device has camera, else false.
     * 
	 * @throws NullPointerException if any of the parameters is null.
     */
    @SuppressLint("InlinedApi")
	public static boolean hasCamera(Context context) {
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
    	PackageManager pm = context.getPackageManager();
        if (VersionUtils.isPreJellyBeanMR1()) {
            return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
//                    || pm.hasSystemFeature("android.hardware.camera.front");
        }
        //above 17
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    
    /***
     * Checks for hardware feature GPS is available in device.
     * 
     * @see {@link PackageManager#hasSystemFeature(String)}.
     * 
	 * @param context -{@link Context}.
     * @return boolean - true If the device has GPS, else false.
     * 
	 * @throws NullPointerException if any of the parameters is null.
     */
    public static boolean hasGPS(Context context) {
    	if(StringUtils.isNull(context)){
    		throw new NullPointerException("context cannot be null");
    	} 
    	PackageManager pm = context.getPackageManager();
    	return pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }
    
    /***
     * Checks for hardware feature NFC is available in device.<br>
     * Will always return false, if the device is API level 8 or {@link Build.VERSION_CODES#FROYO}.
     * 
     * @see {@link PackageManager#hasSystemFeature(String)}.
     * 
	 * @param context -{@link Context}.
     * @return boolean - true If the device has NFC, else false.
     * 
	 * @throws NullPointerException if any of the parameters is null.
     */
    @SuppressLint("InlinedApi") 
    public static boolean hasNFC(Context context) {
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
    	if(VersionUtils.isFroyo()){
    		return false;
    	}
    	PackageManager pm = context.getPackageManager();
    	return pm.hasSystemFeature(PackageManager.FEATURE_NFC);
    }
    
    /***
     * Checks for hardware feature front camera is available in device.<br>
     * Will always return false, if the device API level 8 or {@link Build.VERSION_CODES#FROYO}.
     * 
     * @see {@link PackageManager#hasSystemFeature(String)}.
     * 
	 * @param context -{@link Context}.
     * @return boolean - true If the device has front camera, else false.
     * 
	 * @throws NullPointerException if any of the parameters is null.
     */
    @SuppressLint("InlinedApi") 
    public static boolean hasFrontCamera(Context context) {
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
    	if(VersionUtils.isFroyo()){
    		return false;
    	}
    	PackageManager pm = context.getPackageManager();
    	return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }
    
    /***
     * Checks for hardware feature Bluetooth is available in device.<br>
     * Will always return false, if the device API level 8 or {@link Build.VERSION_CODES#FROYO}.
     * 
     * @see {@link PackageManager#hasSystemFeature(String)}.
     * 
	 * @param context -{@link Context}.
     * @return boolean - true If the device has front camera, else false.
     * 
	 * @throws NullPointerException if any of the parameters is null.
     */
    public static boolean hasBluetooth(Context context) {
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		} 
    	PackageManager pm = context.getPackageManager();
      	 return pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
    } 
    
}
