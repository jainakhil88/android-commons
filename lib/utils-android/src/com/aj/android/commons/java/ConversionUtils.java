package com.aj.android.commons.java;

public class ConversionUtils {
	
	/**
	 * Converts pounds to kilograms.
	 * 
	 * @param weight to be converted.
	 * @return value converted to punds.
	 */
	public static double poundsToKg(double weight) {
		return weight / 2.2;
	}

	/**
	 * Converts kilograms to pounds.
	 * 
	 * @param weight  to be converted.
	 * @return value converted to kgs.
	 */
	public static double kgToPounds(double weight) {
		return weight * 2.2;
	}

	/**
	 * Converts inches to centi-meters.
	 * 
	 * @param inches to be converted.
	 * @return value converted to centi-meters.
	 */
	public static double inchesToCm(double inches) {
		return inches * 2.54;
	}

	/**
	 * Converts centi-meters to inches.
	 * 
	 * @param cm to be converted.
	 * @return value converted to inches.
	 */
	public static double cmToInches(double cm) {
		return cm / 2.54;
	}
	
	
}
