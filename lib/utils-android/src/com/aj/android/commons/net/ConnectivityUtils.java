package com.aj.android.commons.net;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION_CODES;
import android.telephony.TelephonyManager;

import com.aj.android.commons.app.AppUtils;
import com.aj.android.commons.exception.PermissionNotDefinedException;
import com.aj.android.commons.java.StringUtils;

/**
 * Collection of utilities related to network connectivity and find network state .
 * @author Akhil Jain
 *
 */
public class ConnectivityUtils {
	
	/**
     * Get the {@link NetworkInfo} for active network.
     * 
     * @param context -{@link Context}.
     * @return {@link NetworkInfo} -for active network.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
     */
    public static NetworkInfo getActiveNetworkInfo(Context context){
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
		}
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
    
    /**
     * Checks if there is any Network or Internet Connectivity, whether it be any type of network.
     * <br>
     * Returns true if there is any connectivity.
     * 
     * @see {@link ConnectivityUtils#getActiveNetworkInfo(Context)}.
     * 
     * @param context -{@link Context}.
     * @return boolean -if there is any active network connectivity.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
     */   
    public static boolean isConnected(Context context){
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
		}
        NetworkInfo info = getActiveNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if the active network is  WiFi network.
     * <br>
     * Returns <code>false</code> if no active network is found.
     * 
     * @see {@link #isConnectedWifi(Context, NetworkInfo)}.
     * 
     * @param context -{@link Context}.
     * @return boolean -if the active network is WiFi.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
     */    
    public static boolean isConnectedWifi(Context context){
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
		}
        return isConnectedWifi(context,getActiveNetworkInfo(context));
     }
        
    /**
     * Check whether the give {@link NetworkInfo} is WiFi or not
     * <br>
     * @see {@link {@link ConnectivityManager#TYPE_WIFI}.
     * 
     * @param context -{@link Context}.
     * @param info -{@link NetworkInfo}.
     * @return boolean -true if the given network info is WiFi, else false.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
     */
    public static boolean isConnectedWifi(Context context,NetworkInfo info){
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
    	if(StringUtils.isNull(info)){
    		throw new NullPointerException("info cannot be null");
    	}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
		}
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if active network is a mobile network.
     * <br>
     * Returns <code>false</code> if no active network is found.
     * 
     * @see {@link #isConnectedMobile(Context, NetworkInfo)}.
     * 
     * @param context -{@link Context}.
     * @return boolean -true if the active network is mobile network, else false.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
     */    
    public static boolean isConnectedMobile(Context context){
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
		}
        return isConnectedMobile(context,getActiveNetworkInfo(context));
    }
    
    /**
     * Check whether the give {@link NetworkInfo} is mobile or not
     * <br>
     * @see {@link {@link ConnectivityManager#TYPE_MOBILE}.
     * 
     * @param context -{@link Context}.
     * @param info -{@link NetworkInfo}.
     * @return boolean -true if the given network info is mobile, else false.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
     */
    public static boolean isConnectedMobile(Context context,NetworkInfo info){
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
    	if(StringUtils.isNull(info)){
    		throw new NullPointerException("info cannot be null");
    	}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
		}
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Check whether active connection is fast connection or not.
     *  
     *  @see {@link #isConnectionFast(int, int)}.
     *  
     * @param context -{@link Context}.
     * @return boolean -true if the active network is fast, else false.
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
     */
    public static boolean isConnectedFast(Context context){
    	if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
		}
        NetworkInfo info = getActiveNetworkInfo(context);
        return (info != null && info.isConnected() && isConnectionFast(info.getType(),info.getSubtype()));
    }
    /**
     * Check if there give {@link NetworkInfo} is connected fast.
     *  @see {@link #isConnectionFast(int, int)}.
     *  
     * @param context -{@link Context}.
     * @return boolean -true if the active network is fast, else false.
	 * @throws NullPointerException if any of the parameters is null.
     */
    public static boolean isConnectedFast(NetworkInfo info){    	
    	if(StringUtils.isNull(info)){
    		throw new NullPointerException("info cannot be null");
    	}
        return (info != null && info.isConnected() && isConnectionFast(info.getType(),info.getSubtype()));
    }

    /**
     * Check if the connection is fast for the given type and subtype.
     * <br>
     * <b>The method will only work above Android API Level 7 / {@link VERSION_CODES#ECLAIR_MR1}</b>.
     * 
     * @param type -int which can be either {@link ConnectivityManager#TYPE_WIFI} or {@link ConnectivityManager#TYPE_MOBILE}.
     * @param subType -int various types of {@link TelephonyManager} network type.
     * @return boolean- true if the given type and subtype have fast network connectivity, else false.
     */
    public static boolean isConnectionFast(int type, int subType)
    {
    	if(type==ConnectivityManager.TYPE_WIFI){
    		return true;
    	}
    	else if(type==ConnectivityManager.TYPE_MOBILE){
    		switch(subType){
    		case TelephonyManager.NETWORK_TYPE_1xRTT:
    			return false; // ~ 50-100 kbps
    		case TelephonyManager.NETWORK_TYPE_CDMA:
    			return false; // ~ 14-64 kbps
    		case TelephonyManager.NETWORK_TYPE_EDGE:
    			return false; // ~ 50-100 kbps
    		case TelephonyManager.NETWORK_TYPE_EVDO_0:
    			return true; // ~ 400-1000 kbps
    		case TelephonyManager.NETWORK_TYPE_EVDO_A:
    			return true; // ~ 600-1400 kbps
    		case TelephonyManager.NETWORK_TYPE_GPRS:
    			return false; // ~ 100 kbps
    		case TelephonyManager.NETWORK_TYPE_HSDPA:
    			return true; // ~ 2-14 Mbps
    		case TelephonyManager.NETWORK_TYPE_HSPA:
    			return true; // ~ 700-1700 kbps
    		case TelephonyManager.NETWORK_TYPE_HSUPA:
    			return true; // ~ 1-23 Mbps
    		case TelephonyManager.NETWORK_TYPE_UMTS:
    			return true; // ~ 400-7000 kbps
    			/*
    			 * Above API level 7, make sure to set android:targetSdkVersion 
    			 * to appropriate level to use these
    			 */
    		case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11 
    			return true; // ~ 1-2 Mbps
    		case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
    			return true; // ~ 5 Mbps
    		case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
    			return true; // ~ 10-20 Mbps
    		case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
    			return false; // ~25 kbps 
    		case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
    			return true; // ~ 10+ Mbps
    			// Unknown
    		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
    		default:
    			return false;
    		}
    	}else{
    		return false;
    	}
    }

    /**
     * Check whether Wi-Fi is connected or not.
     * 
     * @param context -{@link Context}.
     * @throws NullPointerException if any of the parameters is null.
     * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
     */ 
   public static boolean isWifiConnected(Context context) {
	   if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
		}
       ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
       return wifiNetworkInfo != null && wifiNetworkInfo.isConnected();
   }
   
   /**
    * Gets an {@link ArrayList} of all {@link NetworkInfo}.
    * 
    * @see {@link ConnectivityManager#getAllNetworkInfo()}
    * 
    * @param context -{@link Context}.
    * @return {@link ArrayList <{@link NetworkInfo}>} for all the networks.
    * @throws NullPointerException if any of the parameters is null.
    * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
    */
   public static ArrayList<NetworkInfo> getAllNetworkInfo(Context context){
	   if(StringUtils.isNull(context)){
			throw new NullPointerException("context cannot be null");
		}
		if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
			throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
		}
	   ArrayList<NetworkInfo> allNetworkInfo=new ArrayList<>();
	   ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	   
	   for(NetworkInfo info:connectivityManager.getAllNetworkInfo()){
		   allNetworkInfo.add(info);
	   }
	   return allNetworkInfo;
   }
   
   /**
    * Gets an {@link ArrayList} of all active {@link NetworkInfo}.
    * <br>
    * A network is active, if its connected or connecting.
    * 
    * @see {@link #getAllNetworkInfo(Context)}.
    * 
    * @param context -{@link Context}.
    * @return {@link ArrayList <{@link NetworkInfo}>} for all the active networks.
    * @throws NullPointerException if any of the parameters is null.
    * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
    */
   public static ArrayList<NetworkInfo> getAllActiveNetworkInfo(Context context){
	   if(StringUtils.isNull(context)){
		   throw new NullPointerException("context cannot be null");
	   }
	   if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
		   throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
	   }
	   ArrayList<NetworkInfo> allNetworkInfo=getAllNetworkInfo(context);
	   ArrayList<NetworkInfo> allActiveNetworkInfo=new ArrayList<NetworkInfo>();
	   
	   for(NetworkInfo info:allNetworkInfo){
		   if(info.isConnectedOrConnecting()){
			   allActiveNetworkInfo.add(info);
		   }
	   }
	   return allNetworkInfo;
   }
   
   /**
    * Gets name of all network in an {@link ArrayList} of all {@link String}.
    * 
    * @param context -{@link Context}.
    * @return {@link ArrayList <{@link String}>} with name of all networks.
    * @throws NullPointerException if any of the parameters is null.
    * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
    */
   public static ArrayList<String> getAllNetworkName(Context context){
	   if(StringUtils.isNull(context)){
		   throw new NullPointerException("context cannot be null");
	   }
	   if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
		   throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
	   }
	   ArrayList<NetworkInfo> allNetworkInfo=getAllActiveNetworkInfo(context);
	   ArrayList<String> allNetworkName=new ArrayList<>();
	   
	   for(NetworkInfo info:allNetworkInfo){
		   allNetworkName.add(info.getTypeName());
	   }
	   return allNetworkName;
   }
   
   /**
    * Gets a {@link String} with necessary information about the network separated by new line character <code>\n</code>.
    * <br>
    * Information such as network type id, type and sub-type name, state of the network and etc.
    *  
    * @param info -{@link NetworkInfo}.
    * @return {@link String} -information about the network.
    * @throws NullPointerException if any of the parameters is null.
    */
   public static String getNetworkInfo(NetworkInfo info){
	   if(StringUtils.isNull(info)){
		   throw new NullPointerException("info cannot be null");
	   }
	   StringBuilder builder=new StringBuilder();
	   
	   builder.append("Type id="+info.getType());
	   StringUtils.appendNewLine(builder);
	   
	   builder.append("Type name="+info.getTypeName());
	   StringUtils.appendNewLine(builder);
	   
	   builder.append("Subtype id="+info.getSubtype());
	   StringUtils.appendNewLine(builder);
	   
	   builder.append("Subtype name="+info.getSubtypeName());
	   StringUtils.appendNewLine(builder);
	   
	   builder.append("State="+info.getState().toString());
	   StringUtils.appendNewLine(builder);
	   
	   builder.append("Detailed state="+info.getDetailedState().toString());
	   StringUtils.appendNewLine(builder);
	   
	   builder.append("Extra info="+info.getExtraInfo());
	   StringUtils.appendNewLine(builder);
	   return builder.toString();
   }
    
   /**
    * Check if the active network is low bandwidth network.
    * <br>
    * A network is low bandwidth if its {@link TelephonyManager#NETWORK_TYPE_GPRS} or {@link TelephonyManager#NETWORK_TYPE_EDGE}.
    * 
    * @param context -{@link Context}.
    * @return boolean -if active network is low bandwidth then true ,else false. 
    * @throws NullPointerException if any of the parameters is null.
    * @throws PermissionNotDefinedException if {@link android.Manifest.permission#ACCESS_NETWORK_STATE} permission is not defined.
    */
   public static boolean isLowBandwidthNetwork(Context context) {
	   if(StringUtils.isNull(context)){
		   throw new NullPointerException("context cannot be null");
	   }
	   if(!AppUtils.hasPermission(context, android.Manifest.permission.ACCESS_NETWORK_STATE)){
		   throw new PermissionNotDefinedException(android.Manifest.permission.ACCESS_NETWORK_STATE);
	   }
	   //check for wifi
	   ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
	   android.net.NetworkInfo wifi = connMgr.getNetworkInfo( ConnectivityManager.TYPE_WIFI );

	   if (!wifi.isConnectedOrConnecting()) {
		   //if no wifi, check if we are on GPRS or EDGE
		   TelephonyManager tm = (TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE );
		   if (tm != null && (tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_EDGE
				   || tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_GPRS)) {
			   return true;
		   }
	   }
	   return false;
   }

   /**
    * Check state of mobile data connectivity (on/off).
    * <br>It  only checks if mobile data is turned on/off.
    * 
    * @param context -{@link Context}.
    * @return boolean -true if enabled, false if disabled.
    * @throws NullPointerException if any of the parameters is null.
	* @throws Exception General purpose for safe  case.
    */
   @SuppressWarnings("rawtypes")
   public static boolean checkMobileDataState(Context context) throws Exception{
	   if(StringUtils.isNull(context)){
		   throw new NullPointerException("context cannot be null");
	   }
	   try{
		   ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		   Class connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
		   Method getMobileDataEnabled = connectivityManagerClass.getDeclaredMethod("getMobileDataEnabled");
		   getMobileDataEnabled.setAccessible(true);
		   return (Boolean) getMobileDataEnabled.invoke(connectivityManager);
	   }catch(Exception e){
		   throw new Exception("Unexpected exception. Please check stack trace");
	   }
   }

}
