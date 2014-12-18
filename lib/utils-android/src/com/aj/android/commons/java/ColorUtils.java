package com.aj.android.commons.java;

import java.util.Random;

import android.graphics.Color;

/**
 * Collection of {@link Color} related utility methods.
 * @author ajain
 *
 */
public class ColorUtils {

	private static final String HEX_DECIMAL_COLOR_FORMAT="#%06X";
	
	/**
	 * Gets the integer value for the given {@link String} hex-decimal color.
	 * <br>
	 * Returns -1 if the provided {@link String} is empty.
	 * 
	 * @see {@link Color#parseColor(String)}.
	 * 
	 * @param hexDecimalColor -{@link String} hex decimal color.
	 * @return int -parsed integer color code. 
	 */
	public static int getColor(String hexDecimalColor){
		if(StringUtils.isEmpty(hexDecimalColor)){
			return -1;
		}
		return Color.parseColor(hexDecimalColor);
	}
	
	/**
	 * Returns the hex-decimal color value for the provided int-color code.
	 * 
	 * @param colorCode -int for the color code.
	 * @return {@link String} -hex-deximal color.
	 */
	public static String getHexDecimalColor(int colorCode){
		return String.format(HEX_DECIMAL_COLOR_FORMAT, (0xFFFFFF & colorCode));
	}
	
	/**
	 * Check whether the provide {@link Color} is opaque or not.
	 * 
	 * @param colorCode -int for the color code.
	 * @return boolean - returns true if color is opaque, else false.
	 */
	public static boolean isOpaque(int colorCode) {
        return colorCode >>> 24 == 0xFF;
    }
	
	/**
	 * Returns less saturated color for the provided color code.
	 * <br>
	 * The color is 10% lighter than provided color code.
	 * 
	 * @param originalColor - int color code.
	 * @return int -desaturated /less saturated color.
	 */
	public static int getDesaturatedColor(int originalColor){
		int saturatedColor=0;

		float hsv[]=new float[3];
		int red=Color.red(originalColor);
		int blue=Color.blue(originalColor);
		int green=Color.green(originalColor);

		Color.RGBToHSV(red, green, blue, hsv);

		float decreasedSaturation = hsv[1] - 0.10f;
		if(decreasedSaturation > 1.0){
			decreasedSaturation = 0.0f;
		}else if(decreasedSaturation < 0.0){
			decreasedSaturation = 1.0f;
		}
		
		hsv[1]=decreasedSaturation;
		saturatedColor=Color.HSVToColor(hsv);
		
		return saturatedColor;
	}
	
	/**
	 * Returns more saturated color for the provided color code.
	 * <br>
	 * The color is 10% saturated than provided color code.
	 * 
	 * @param originalColor - int color code.
	 * @return int -saturated /more saturated color.
	 */
	public static int getSaturatedColor(int originalColor){
		int saturatedColor=0;

		float hsv[]=new float[3];
		int red=Color.red(originalColor);
		int blue=Color.blue(originalColor);
		int green=Color.green(originalColor);

		Color.RGBToHSV(red, green, blue, hsv);

		float increasedSaturation = hsv[1] + 0.10f;
		if(increasedSaturation > 1.0){
			increasedSaturation = 1.0f;
		}else if(increasedSaturation < 0.0){
			increasedSaturation = 0.0f;
		}
		
		hsv[1]=increasedSaturation;
		saturatedColor=Color.HSVToColor(hsv);
		
		return saturatedColor;
	}
	
	
	/**
	 * Returns random color code.
	 **/
	public static int getRandomColor() {
		Random random = new Random();
		int red = random.nextInt(255);
		int green = random.nextInt(255);
		int blue = random.nextInt(255);

		return Color.argb(255, red, green, blue );
	}
}
