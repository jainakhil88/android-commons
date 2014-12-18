package com.aj.android.commons.io;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.StatFs;

import com.aj.android.commons.app.VersionUtils;

public class SDCardUtils 
{
	
	/**
	 * Returns true if the device has SD Card.
	 * @return boolean -true if the sd card is available, else false.
	 */
	public static boolean hasSdCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
	
	/**
     * Check if free size is available for the given long bytes. Returns if the free space is more than required space.
     *
     * @param needSize - how much space is required.
     * @return boolean -true if has enough space is available on SD Card.
     */
    public static boolean hasFreeSpace(long needSize) {
        long freeSpace = getFreeSpace();
        return freeSpace > needSize;
    }

    /**
     * Get the free space available on the device in SD Card.
     * @return long- returns free space available on device SD Card.
     */
    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
    public static long getFreeSpace(){
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize=0;
        long availableBlocks=0;
        if(VersionUtils.isPreJellyBeanMR2()){
        	blockSize = stat.getBlockSize();
        	availableBlocks = stat.getAvailableBlocks();
        }
        else
        {
        	blockSize=stat.getBlockSizeLong();
        	availableBlocks=stat.getAvailableBlocksLong();
        }
        return availableBlocks * blockSize;
    }
    
  
    /**
     * Check if the SD Card is writable.
     *
     * @return boolean -true if the SD Card is writable ,else false if it is not.
     */
    public static boolean isSDCardWritable() {

        boolean mExternalStorageWriteable = false;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media

            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states,
            // but all we need
            // to know is we can neither read nor write
            mExternalStorageWriteable = false;
        }

        return mExternalStorageWriteable;

    }
 
}
