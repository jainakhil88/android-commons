package com.aj.android.commons.java;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public class MathUtils {

	private static final float DEG_TO_RAD = 3.1415926f / 180.0f;
	private static final float RAD_TO_DEG = 180.0f / 3.1415926f;

	/**
	 * Checks whether the provide number is positive(>=0) or not.
	 * 
	 * @param number - Integer value that needs to be checked.
	 * @return boolean- true if number is greater than equal to 0, else false.
	 */
	public static boolean isPositive(long number){
		boolean isPositive=false;
		if(number>=0){
			isPositive=false;
		}
		return isPositive;
	}
	
	/**
	 * Checks whether the provide number is positive(>=0) or not.
	 * 
	 * @param number -point precision which needs to be checked. 
	 * @return boolean- true if number is greater than equal to 0, else false.
	 */
	public static boolean isPositive(double number){
		boolean isPositive=false;
		if(number>=0){
			isPositive=false;
		}
		return isPositive;
	}
	
	/**
	 * Calculate percentage, based on total and current value.
	 * 
	 * <pre>
	 * computePercent(8508,9689)          =87
	 * computePercent(2468,2982)          =82
	 * computePercent(1220,6140)          =19
	 * computePercent(5071,9374)          =54
	 * computePercent(4863,8784)          =55
	 * computePercent(740,0)              =0
	 * computePercent(1053,1977)          =53
	 * computePercent(3295,4451)          =74
	 * computePercent(142,323)            =43
	 * computePercent(1561,3755)          =41
	 * </pre>
	 * @param current
	 * @param total
	 * @return
	 */
	public static int computePercent(int current,int total){
    	int percent = 0;
    	if (total > 0)
    		percent = current * 100 / total;
    	return percent;
     }
	
	/**
	 * <pre>
	 * computeExactPercent(135,1036)      =13.03088803088803
	 * computeExactPercent(7020,9619)     =72.98055930969956
	 * computeExactPercent(5039,9576)     =52.62113617376775
	 * computeExactPercent(675,3893)      =17.338813254559465
	 * computeExactPercent(4402,8859)     =49.68958121684163
	 * computeExactPercent(96,0)          =5.9405940594059405
	 * computeExactPercent(1131,1656)     =68.29710144927536
	 * computeExactPercent(1426,4549)     =31.347548911848758
	 * computeExactPercent(1521,2109)     =72.11948790896159
	 * computeExactPercent(1380,2257)     =61.143110323438194
	 * </pre>
	 * @param current -value out of total
	 * @param total -total
	 * @return double -exact percentage
	 */
	public static double computeExactPercent(int current,int total){
    	double percent = 0;
    	double t=total;
    	double c=current;
    	if (total > 0)
    		percent = c * 100 / t;
    	return percent;
     }

	/**
	 * Rounds a double value to a certain number of digits
	 * <pre>
	 * round(-98652.11455656544,1,Locale.US)   =-98652.1
	 * round(-56022.7964147592,8,Locale.US)    =-56022.79641476
	 * round(-45902.95632021122,1,Locale.US)   =-45903.0
	 * round(-27835.593712905553,4,Locale.US)  =-27835.5937
	 * round(-5048.7512728658185,2,Locale.US)  =-5048.75
	 * round(-25437.705316670646,4,Locale.US)  =-25437.7053
	 * round(-87840.23633544882,3,Locale.US)   =-87840.236
	 * round(-76992.53986309195,7,Locale.US)   =-76992.5398631
	 * round(96473.79885775055,9,Locale.US)    =96473.798857751
	 * round(70704.19819820565,4,Locale.US)    =70704.1982
	 * </pre>
	 * @param toBeRounded -number to be rounded
	 * @param digits -number of digits to be rounded
	 * @param locale -{@link Locale}
	 * @return double -rounded double 
	 */
	public static double round(double toBeRounded, int digits,Locale locale) {
		if (digits < 0) {
			return 0;
		}
		String formater = "";
		for (int i = 0; i < digits; i++) {
			formater += "#";
		}

		DecimalFormat twoDForm = new DecimalFormat("#." + formater, new DecimalFormatSymbols(locale));
		return Double.valueOf(twoDForm.format(toBeRounded));
	}

	/**
	 * Returns a random integer between 0 (Zero) inclusive and MAX inclusive.
	 * <br>
	 * If negative value is specified, then range is between [n, 0].
	 * <br>
	 * Internally calls {@link #getRandomIntegerWithinRange(int, int)}.
	 * <br>
	 *  E.g., 
	 * <pre>
	 * getRandomInteger(95901)  =37196
	 * getRandomInteger(87871)  =74819
	 * getRandomInteger(-11760) =-8015
	 * getRandomInteger(-39833) =-34292
	 * getRandomInteger(-22388) =-17231
	 * getRandomInteger(-37682) =-32438
	 * getRandomInteger(-21555) =-1
	 * getRandomInteger(-97820) =-16802
	 * getRandomInteger(10865)  =779
	 * getRandomInteger(-55538) =-31878
	 * </pre>
	 * 
	 * @param max - value inclusive
	 * @return an int between 0 inclusive and MAX inclusive.
	 */
	public static int getRandomInteger(int max) {
		int min=0;
		if(max<0){
			int temp=max;
			max=0;
			min=temp;
		}
		return getRandomIntegerWithinRange(min, max);
	}
	
	/**
	 * Returns a random number between MIN inclusive and MAX inclusive.
	 * <br>
	 * E.g., 
	 * <pre>
	 * getRandomIntegerWithinRange(-31044,48047)    =30677
	 * getRandomIntegerWithinRange(6747,-14784)     =-249
	 * getRandomIntegerWithinRange(55909,11074)     =34610
	 * getRandomIntegerWithinRange(75063,-199)      =59178
	 * getRandomIntegerWithinRange(-91353,-85776)   =-87712
	 * getRandomIntegerWithinRange(-44204,73850)    =-3498
	 * getRandomIntegerWithinRange(-87584,54808)    =-52963
	 * getRandomIntegerWithinRange(-69446,61433)    =-60328
	 * getRandomIntegerWithinRange(-93646,47771)    =-3123
	 * getRandomIntegerWithinRange(29507,-75727)    =-25908
	 *</pre>
	 * @param min
	 *            value inclusive
	 * @param max
	 *            value inclusive
	 * @return an int between MIN inclusive and MAX exclusive.
	 */
	public static int getRandomIntegerWithinRange(int min, int max) {
		if(min==max){
			return max;
		}
		if(max<min){
			int temp=max;
			max=min;
			min=temp;
		}
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}
	
	/**
	 * Returns a random long number in the given range [0, n], with both inclusive.
	 * <br>
	 * If negative value is specified, then range is between [n, 0].
	 * <br>
	 * Internally calls {@link #getRandomLongWithinRange(long, long)}.
	 * <br>
	 * E.g., 
	 * <pre>
	 * getRandomLong(61582)     =18533
	 * getRandomLong(-76668)    =-1814
	 * getRandomLong(-31559)    =-12808
	 * getRandomLong(69131)     =59067
	 * getRandomLong(-36603)    =-36112
	 * getRandomLong(61790)     =20328
	 * getRandomLong(-82233)    =-46459
	 * getRandomLong(-25535)    =-16728
	 * getRandomLong(77116)     =64357
	 * getRandomLong(50985)     =25514
	 * </pre>
	 * 
	 * @param max
	 * @return
	 */
	public static long getRandomLong(long max) {
		long min=0;
		if(max<0){
			long temp=max;
			max=0;
			min=temp;
		}
		return getRandomLongWithinRange(min, max);
	}
	
	/**
	 * <pre>
	 * getRandomLongWithinRange(92018,-25350)       =63884
	 * getRandomLongWithinRange(8800,-78355)        =-54732
	 * getRandomLongWithinRange(72743,73588)        =73067
	 * getRandomLongWithinRange(-59992,-65874)      =-61048
	 * getRandomLongWithinRange(75188,-89480)       =-8856
	 * getRandomLongWithinRange(-20925,-21257)      =-21215
	 * getRandomLongWithinRange(-18314,-2266)       =-10541
	 * getRandomLongWithinRange(93055,-87301)       =-56737
	 * getRandomLongWithinRange(-72457,-29425)      =-59096
	 * getRandomLongWithinRange(83775,12505)        =77052
	 * </pre>
	 * @param min
	 * @param max
	 * @return
	 */
	public static long getRandomLongWithinRange(long min,long max) {
		if(min==max){
			return max;
		}
		if(max<min){
			long temp=max;
			max=min;
			min=temp;
		}
		Random r = new Random();
		double randomFactor=r.nextDouble();
		return (long) (min +((max - min+1) *randomFactor));
	}

	/**
	 * Returns a random double between 0 (Zero) inclusive and MAX inclusive. <br/>
	 * Same as {@code getRandomDouble(0, max);} <br/>
	 * See {@see RandomUtil#getRandomDouble(double, double)}
	 * <br>
	 * <pre>
	 * getRandomDouble(90642.35133634694)      =39301.97625505765
	 * getRandomDouble(14220.623751592182)     =2718.9773743657543
	 * getRandomDouble(86464.91587495219)      =51659.94892347659
	 * getRandomDouble(-21813.076403603045)    =-8132.03926435171
	 * getRandomDouble(-97595.26888578205)     =-76560.69171801352
	 * getRandomDouble(-75992.24447830633)     =-46136.017254882776
	 * getRandomDouble(29675.576517748195)     =22238.0418219805
	 * getRandomDouble(20647.127175101836)     =15448.426399252865
	 * getRandomDouble(-91623.11811167543)     =-8227.434590156015
	 * getRandomDouble(40851.831219784566)     =12606.04569340369
	 * </pre>
	 * @param max
	 *            value exclusive
	 * @return an int between 0 inclusive and MAX inclusive.
	 */
	public static double getRandomDouble(double max) {
		double min=0;
		if(max<0){
			double temp=max;
			max=0;
			min=temp;
		}
		return getRandomDoubleWithinRange(min, max);
	}
	
	/**
	 * Returns a random double between MIN inclusive and MAX inclusive.
	 * <pre>
	 * getRandomDoubleWithinRange(-67079.52093043012,38590.127225954115)     =-46406.57462194614
	 * getRandomDoubleWithinRange(83519.72728972032,-21838.895211522875)     =-20888.57391556023
	 * getRandomDoubleWithinRange(-19438.463687148716,4376.778657883522)     =-15438.56924854897
	 * getRandomDoubleWithinRange(55555.30262127341,-17707.9083457027)       =44996.65491249181
	 * getRandomDoubleWithinRange(3634.6845627512375,99490.19081387305)      =73107.8444818787
	 * getRandomDoubleWithinRange(-85651.0196247493,52425.87053442275)       =-82670.98466897203
	 * getRandomDoubleWithinRange(-51928.18332718443,-20437.13643617982)     =-24213.577759700787
	 * getRandomDoubleWithinRange(28390.327055048227,76527.79137449738)      =41866.381132394425
	 * getRandomDoubleWithinRange(-13646.434494596353,33749.60581828328)     =27951.185972543928
	 * getRandomDoubleWithinRange(87023.71784671245,60555.784390894405)      =66440.11341157835
	 * </pre>
	 * @param min
	 *            value inclusive
	 * @param max
	 *            value inclusive
	 * @return an int between 0 inclusive and MAX exclusive.
	 */
	public static double getRandomDoubleWithinRange(double min, double max) {
		if(min==max){
			return max;
		}
		if(max<min){
			double temp=max;
			max=min;
			min=temp;
		}
		Random r = new Random();
		return min + (max - min) * r.nextDouble();
	}

	/**
	 * Degrees to radians
	 * 
	 * @param degrees
	 * @return the converted value
	 */
	public static float degreesToRadians(float degrees) {
		return degrees * DEG_TO_RAD;
	}

	/**
	 * Radians to degrees
	 * 
	 * @param degrees
	 * @return the converted value
	 */
	public static float radiansToDegrees(float radians) {
		return radians * RAD_TO_DEG;
	}

	/**
	 * Arc cosine
	 * 
	 * @param value
	 * @return Returns the closest double approximation of the arc cosine of the
	 *         argument within the range [0..pi]. The returned result is within
	 *         1 ulp (unit in the last place) of the real result.
	 */
	public static float acos(float value) {
		return (float) Math.acos(value);
	}

	/**
	 * Arc sine
	 * 
	 * @param value
	 * @return Returns the closest double approximation of the arc sine of the
	 *         argument within the range [-pi/2..pi/2]. The returned result is
	 *         within 1 ulp (unit in the last place) of the real result.
	 */
	public static float asin(float value) {
		return (float) Math.asin(value);
	}

	/**
	 * Arc tangent
	 * 
	 * @param value
	 * @return Returns the closest double approximation of the arc tangent of
	 *         the argument within the range [-pi/2..pi/2]. The returned result
	 *         is within 1 ulp (unit in the last place) of the real result.
	 */
	public static float atan(float value) {
		return (float) Math.atan(value);
	}

	/**
	 * Arc tangent of y/x within the range [-pi..pi]
	 * 
	 * @param a
	 * @param b
	 * @return Returns the closest double approximation of the arc tangent of
	 *         y/x within the range [-pi..pi]. This is the angle of the polar
	 *         representation of the rectangular coordinates (x,y). The returned
	 *         result is within 2 ulps (units in the last place) of the real
	 *         result.
	 */
	public static float atan2(float a, float b) {
		return (float) Math.atan2(a, b);
	}

	/**
	 * Tangent of an angle
	 * 
	 * @param angle
	 *            angle
	 * @return the tangent
	 */
	public static float tan(float angle) {
		return (float) Math.tan(angle);
	}

	/**
	 * Absolute value
	 * 
	 * @param v
	 *            value
	 * @return returns the absolute value
	 */
	public static float abs(float v) {
		return v > 0 ? v : -v;
	}

	/**
	 * Number's logarithm <br>
	 * Special cases:
	 * 
	 * <li>log(+0.0) = -infinity</li> <li>log(-0.0) = -infinity</li><li>
	 * log((anything < 0) = NaN</li> <li>log(+infinity) = +infinity</li><li>
	 * log(-infinity) = NaN</li><li>log(NaN) = NaN</li>
	 * 
	 * 
	 * @param number
	 * @return Returns the closest double approximation of the natural logarithm
	 *         of the argument. The returned result is within 1 ulp (unit in the
	 *         last place) of the real result.
	 */
	public static float logarithm(float number) {
		return (float) Math.log(number);
	}

	/**
	 * Number's Exponential
	 * 
	 * @param number
	 *            float number
	 * @return Returns the closest double approximation of the natural logarithm
	 *         of the argument. The returned result is within 1 ulp (unit in the
	 *         last place) of the real result.
	 */
	public static float exponential(float number) {
		return (float) Math.exp(number);
	}

	/**
	 * Gets the higher number
	 * 
	 * @param a
	 *            float number
	 * @param b
	 *            float number
	 * @return the higher number between a and b
	 */
	public static float max(float a, float b) {
		return a > b ? a : b;
	}

	/**
	 * Gets the higher number
	 * 
	 * @param a
	 *            int number
	 * @param b
	 *            int number
	 * @return the higher number between a and b
	 */
	public static int max(int a, int b) {
		return a > b ? a : b;
	}

	/**
	 * Gets the lower number
	 * 
	 * @param a
	 *            float number
	 * @param b
	 *            float number
	 * @return the lower number between a and b
	 */
	public static float min(float a, float b) {
		return a < b ? a : b;
	}

	/**
	 * Gets the lower number
	 * 
	 * @param a
	 *            float number
	 * @param b
	 *            float number
	 * @return the lower number between a and b
	 */
	public static int min(int a, int b) {
		return a < b ? a : b;
	}

	/**
	 * Check if a number is Odd
	 * 
	 * @param num
	 *            int number
	 * @return true if the num is odd and false if it's even
	 */
	public static boolean isOdd(int num) {
		return !isEven(num);
	}

	/**
	 * Check if a number is Even
	 * 
	 * @param num
	 *            int number
	 * @return true if the num is even and false if it's odd
	 */
	public static boolean isEven(int num) {
		return (num % 2 == 0);
	}
	
	/**
	 * Truncates a value
	 * @param value - value to be truncated
	 * @param places - decimal places
	 * @return
	 */
	public static double truncate(double value, int places) {
	    if (places < 0) {
	        throw new IllegalArgumentException();
	    }

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = (long) value;
	    return (double) tmp / factor;
	}

	
	public static Long parseLong(String value)
	{
		Long parseValue=null;
		try
		{
			parseValue=Long.parseLong(value);
		}catch(Exception e)
		{
			//ignored
			parseValue=null;
		}
		return parseValue;
	}
	
	/**
	 * Parse the string to integer, does not throw exception, return null.
	 * <br>
	 * 
	 * @param value -{@link String}
	 * @return
	 */
	public static Integer parseInteger(String value)
	{
		Integer parseValue=null;
		try
		{
			parseValue=Integer.parseInt(value);
		}catch(Exception e)
		{
			//ignored
			parseValue=null;
		}
		return parseValue;
	}
	
}
