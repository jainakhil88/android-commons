package com.aj.android.commons.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.aj.android.commons.io.FileUtils;
import com.aj.android.commons.io.StreamUtils;
import com.aj.android.commons.java.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BitmapUtils 
{
	private static final int DEFAULT_BLUR_RADIUS = 12;

	/**
	 * Used to store Bitmap Details, width and height and use as and when needed.
	 * @author ajain
	 */
	public static class BitmapDetails{
		int width;
		int height;
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		@Override
		public String toString() {
			StringBuilder builder=new StringBuilder();
			builder.append("width="+this.width)
			.append("\n")
			.append("height="+this.height)
			.append("\n");
			/*	.append("resolution="+this.horizontalResolution)
			.append("\n");*/
			return builder.toString();
		}


	}

	/**
	 * Converts {@link Drawable} resource to {@link Bitmap}.
	 * 
	 * @param drawable - {@link Drawable}.
	 * @return {@link Bitmap} - bitmap.
	 **/
	public static Bitmap drawable2Bitmap(Drawable drawable){
		if(StringUtils.isNull(drawable)){
			throw new NullPointerException("drawable cannot be null");
		}
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Save Bitmap to file. Automatically create directory/ directories if not present.
	 * <br>
	 * If writing to external location, please make sure you have 
	 * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE} permission defined in manifest.
	 * 
	 * @param fileName - {@link String} path where image has to saved.
	 * @param bitmap - {@link Bitmap} image which has to be saved.
	 * @param format - {@link Bitmap.CompressFormat} format of the image in which bitmap needs to be saved.
	 * @param quality - int quality for the image.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IOException if any IO related exception occur.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static void saveBitmapToFile(String fileName,Bitmap bitmap,Bitmap.CompressFormat format, int quality) throws IOException{
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		if(StringUtils.isNull(fileName)){
			throw new NullPointerException("filename cannot be null");
		}
		if(StringUtils.isNull(format)){
			throw new NullPointerException("format cannot be null");
		}
		if(StringUtils.isEmpty(fileName)|| StringUtils.isBlank(fileName)){
			throw new IllegalArgumentException("fileName cannot be empty");
		}
		File f = new File(fileName);
		FileUtils.directoryCreator(f.getParent());
		f.createNewFile();
		FileOutputStream foutStream = null;
		try {
			foutStream = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			throw e;
		}
		bitmap.compress(format,quality, foutStream);
		try {
			foutStream.flush();
		} catch (IOException e) {
			throw e;
		}finally{
			StreamUtils.closeQuietly(foutStream);
		}
	}
	
	/**
	 * Save Bitmap to file. Automatically create directory/ directories if not present.
	 * <br>
	 * If writing to external location, please make sure you have 
	 * {@link android.Manifest.permission#WRITE_EXTERNAL_STORAGE} permission defined in manifest.
	 * 
	 * @param fileName - {@link String} path where image has to saved.
	 * @param bitmap - byte[] bitmap image which has to be saved.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 * @throws IOException if any IO related exception occur.
	 * @throws IllegalArgumentException if any {@link String} parameter is empty or null.
	 */
	public static void saveBitmapToFile(String fileName,byte[] bitmap) throws IOException{
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		if(StringUtils.isNull(fileName)){
			throw new NullPointerException("filename cannot be null");
		}
		if(StringUtils.isEmpty(fileName)|| StringUtils.isBlank(fileName)){
			throw new IllegalArgumentException("fileName cannot be empty");
		}
		File f = new File(fileName);
		f.createNewFile();
		FileOutputStream foutStream = null;
		try {
			foutStream = new FileOutputStream(f);
			foutStream.write(bitmap);
		} catch (FileNotFoundException e) {
			throw e;
		}
		try {
			foutStream.flush();
		} catch (IOException e) {
			throw e;
		}finally{
			StreamUtils.closeQuietly(foutStream);
		}
	}

	/**
	 * Converts {@link Bitmap} to byte-array.
	 * 
	 * @param bitmap - {@link Bitmap} image.
	 * @param format - {@link Bitmap.CompressFormat} format of the image in which bitmap needs to be saved.
	 * @param quality - int quality for the image.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static byte[] bitmapToByteArray(Bitmap bitmap, Bitmap.CompressFormat format, int quality) {
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		if(StringUtils.isNull(format)){
			throw new NullPointerException("format cannot be null");
		}
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		bitmap.compress(format, quality, os);
		return os.toByteArray();
	}
	
	/**
	 * Converts byte-array to {@link Bitmap}.
	 * 
	 * @param bitmap - byte-array {@link Bitmap} image.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap byteArrayToBitmap(byte[] bitmap) {
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		return BitmapFactory.decodeByteArray(bitmap , 0, bitmap .length);
	}

	/**
	 * Rotates a given {@link Bitmap} image by provided angle.
	 * 
	 * @param bitmap - {@link Bitmap} image which has to be rotated.
	 * @param angle - angle by which image has to be rotated.
	 * @param quality - int quality for the image.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap rotate(Bitmap bitmap, float angle) {
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		if ((angle % 360) == 0) {
			return bitmap;
		}

		final boolean dimensionsChanged = angle == 90 || angle == 270;
		final int oldWidth = bitmap.getWidth();
		final int oldHeight = bitmap.getHeight();
		final int newWidth = dimensionsChanged ? oldHeight : oldWidth;
		final int newHeight = dimensionsChanged ? oldWidth : oldHeight;

		Bitmap newBitmap = Bitmap.createBitmap(newWidth, newHeight, bitmap.getConfig());
		Canvas canvas = new Canvas(newBitmap);

		Matrix matrix = new Matrix();
		matrix.preTranslate((newWidth - oldWidth) / 2f, (newHeight - oldHeight) / 2f);
		matrix.postRotate(angle, newBitmap.getWidth() / 2f, newBitmap.getHeight() / 2);
		canvas.drawBitmap(bitmap, matrix, null);

		bitmap.recycle();

		return newBitmap;
	}

	/**
	 * Rotates a given {@link Bitmap} image drop shadow by given radius.
	 * 
	 * @param bitmap - {@link Bitmap} image.
	 * @param radius - distance from image where shadow to be dropped.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap drawShadow(Bitmap bitmap, int radius) {
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}

		BlurMaskFilter blurFilter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER);
		Paint shadowPaint = new Paint();
		shadowPaint.setMaskFilter(blurFilter);

		int[] offsetXY = new int[2];
		Bitmap shadowImage = bitmap.extractAlpha(shadowPaint, offsetXY);
		shadowImage = shadowImage.copy(Config.ARGB_8888, true);
		Canvas c = new Canvas(shadowImage);
		c.drawBitmap(bitmap, -offsetXY[0], -offsetXY[1], null);
		return shadowImage;
	}

	/**
	 * Rotates a given {@link Bitmap} image pixels have to be rounded
	 * 
	 * @param bitmap - {@link Bitmap} image.
	 * @param roundPx - pixels by which corners have to be rounded.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * Returns the {@link Bitmap} reflected of the original 
	 * 
	 * @param bitmap - {@link Bitmap} image.
	 * @param rate - reflection rate.
	 * @param recycleOriginalBitmap - true to recycle the original bitmap.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static Bitmap getReflectBitmap(Bitmap bitmap, float rate, boolean recycleOriginalBitmap) {
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		//The gap we want between the reflection and the original image
		final int reflectionGap = 0;


		int width = bitmap.getWidth();
		int height = bitmap.getHeight();


		//This will not scale but will flip on the Y axis
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		//Create a Bitmap with the flip matix applied to it.
		//We only want the bottom half of the image
		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height/2, width, height/2, matrix, false);


		//Create a new bitmap with same width but taller to fit reflection
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width 
				, (height + height/2), Config.ARGB_8888);

		//Create a new Canvas with the bitmap that's big enough for
		//the image plus gap plus reflection
		Canvas canvas = new Canvas(bitmapWithReflection);
		//Draw in the original image
		canvas.drawBitmap(bitmap, 0, 0, null);
		//Draw in the gap
		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
		//Draw in the reflection
		canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);

		//Create a shader that is a linear gradient that covers the reflection
		Paint paint = new Paint(); 
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, 
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, 
				TileMode.CLAMP); 
		//Set the paint to use this shader (linear gradient)
		paint.setShader(shader); 
		//Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); 
		//Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint); 
		if(recycleOriginalBitmap){    		
			if (!bitmap.isRecycled()) {
				bitmap.recycle();
			}
		}
		return bitmapWithReflection;
	}

	public static Bitmap getReflectBitmap(Bitmap originalImage, float rate) {
		return getReflectBitmap(originalImage, rate,false);
	}

	public static Bitmap getSquareBitmap(Bitmap src) {
		return getSquareBitmap(src, 0.1f);
	}

	public static Bitmap getSquareBitmap(Bitmap src, float rate) {
		if (src == null || src.isRecycled())
			return null;
		Bitmap ret = src;
		int w = src.getWidth();
		int h = src.getHeight();
		int min = Math.min(w, h);
		int max = Math.max(w, h);
		float r = (float) (max - min) / (float) min;
		if (w != h && r > rate) {
			max = Math.round((1.0f + rate) * min);
			if (w > h) {
				ret = Bitmap.createBitmap(src, (w - max) / 2, 0, max, min);
			} else {
				ret = Bitmap.createBitmap(src, 0, (h - max) / 2, min, max);
			}
		}
		return ret;
	}


	public static Bitmap clipCircleBitmap(Bitmap bitmap) {
		if (bitmap == null || bitmap.isRecycled())
			return null;

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top = 0, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		width = width - 2;
		height = height - 2;
		if (width <= height) {
			roundPx = width / 2;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap output = Bitmap.createBitmap(width,height, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);


		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);

		if (!bitmap.isRecycled()) {
			bitmap.recycle();
		}

		return output;
	}

	public static Bitmap getBluredBitmap(Bitmap bitmap){
		return getBluredBitmap(bitmap,DEFAULT_BLUR_RADIUS);
	}

	public static Bitmap getBluredBitmap(Bitmap sentBitmap, int radius) {

		// Stack Blur v1.0 from
		// http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
		//
		// Java Author: Mario Klingemann <mario at quasimondo.com>
		// http://incubator.quasimondo.com
		// created Feburary 29, 2004
		// Android port : Yahel Bouaziz <yahel at kayenko.com>
		// http://www.kayenko.com
		// ported april 5th, 2012

		// This is a compromise between Gaussian Blur and Box blur
		// It creates much better looking blurs than Box Blur, but is
		// 7x faster than my Gaussian Blur implementation.
		//
		// I called it Stack Blur because this describes best how this
		// filter works internally: it creates a kind of moving stack
		// of colors whilst scanning through the image. Thereby it
		// just has to addPart one new block of color to the right side
		// of the stack and remove the leftmost color. The remaining
		// colors on the topmost layer of the stack are either added on
		// or reduced by one, depending on if they are on the right or
		// on the left side of the stack.
		//
		// If you are using this algorithm in your code please addPart
		// the following line:
		//
		// Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

		Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

		if (radius < 1) {
			return (null);
		}

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		int[] pix = new int[w * h];
		bitmap.getPixels(pix, 0, w, 0, 0, w, h);

		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;

		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];

		int divsum = (div + 1) >> 1;
		divsum *= divsum;
		int dv[] = new int[256 * divsum];
		for (i = 0; i < 256 * divsum; i++) {
			dv[i] = (i / divsum);
		}

		yw = yi = 0;

		int[][] stack = new int[div][3];
		int stackpointer;
		int stackstart;
		int[] sir;
		int rbs;
		int r1 = radius + 1;
		int routsum, goutsum, boutsum;
		int rinsum, ginsum, binsum;

		for (y = 0; y < h; y++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				sir = stack[i + radius];
				sir[0] = (p & 0xff0000) >> 16;
			sir[1] = (p & 0x00ff00) >> 8;
		sir[2] = (p & 0x0000ff);
		rbs = r1 - Math.abs(i);
		rsum += sir[0] * rbs;
		gsum += sir[1] * rbs;
		bsum += sir[2] * rbs;
		if (i > 0) {
			rinsum += sir[0];
			ginsum += sir[1];
			binsum += sir[2];
		} else {
			routsum += sir[0];
			goutsum += sir[1];
			boutsum += sir[2];
		}
			}
			stackpointer = radius;

			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				rsum -= routsum;
				gsum -= goutsum;
				bsum -= boutsum;

				stackstart = stackpointer - radius + div;
				sir = stack[stackstart % div];

				routsum -= sir[0];
				goutsum -= sir[1];
				boutsum -= sir[2];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
				}
				p = pix[yw + vmin[x]];

				sir[0] = (p & 0xff0000) >> 16;
			sir[1] = (p & 0x00ff00) >> 8;
			sir[2] = (p & 0x0000ff);

			rinsum += sir[0];
			ginsum += sir[1];
			binsum += sir[2];

			rsum += rinsum;
			gsum += ginsum;
			bsum += binsum;

			stackpointer = (stackpointer + 1) % div;
			sir = stack[(stackpointer) % div];

			routsum += sir[0];
			goutsum += sir[1];
			boutsum += sir[2];

			rinsum -= sir[0];
			ginsum -= sir[1];
			binsum -= sir[2];

			yi++;
			}
			yw += w;
		}
		for (x = 0; x < w; x++) {
			rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;

				sir = stack[i + radius];

				sir[0] = r[yi];
				sir[1] = g[yi];
				sir[2] = b[yi];

				rbs = r1 - Math.abs(i);

				rsum += r[yi] * rbs;
				gsum += g[yi] * rbs;
				bsum += b[yi] * rbs;

				if (i > 0) {
					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];
				} else {
					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];
				}

				if (i < hm) {
					yp += w;
				}
			}
			yi = x;
			stackpointer = radius;
			for (y = 0; y < h; y++) {
				// Preserve alpha channel: ( 0xff000000 & pix[yi] )
				pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

			rsum -= routsum;
			gsum -= goutsum;
			bsum -= boutsum;

			stackstart = stackpointer - radius + div;
			sir = stack[stackstart % div];

			routsum -= sir[0];
			goutsum -= sir[1];
			boutsum -= sir[2];

			if (x == 0) {
				vmin[y] = Math.min(y + r1, hm) * w;
			}
			p = x + vmin[y];

			sir[0] = r[p];
			sir[1] = g[p];
			sir[2] = b[p];

			rinsum += sir[0];
			ginsum += sir[1];
			binsum += sir[2];

			rsum += rinsum;
			gsum += ginsum;
			bsum += binsum;

			stackpointer = (stackpointer + 1) % div;
			sir = stack[stackpointer];

			routsum += sir[0];
			goutsum += sir[1];
			boutsum += sir[2];

			rinsum -= sir[0];
			ginsum -= sir[1];
			binsum -= sir[2];

			yi += w;
			}
		}
		bitmap.setPixels(pix, 0, w, 0, 0, w, h);
		return (bitmap);
	}


	/* Convert drawable resource to bitmap
	 *
	 * @param context  Application context
	 * @param drawable drawable resource to be converted
	 * @return a bitmap
	 */
	public static Bitmap convertImageResourceToBitmap(Context context, int drawable) {
		return BitmapFactory.decodeResource(context.getResources(), drawable);
	}

	/**
	 * Convert drawable to bitmap
	 *
	 * @param drawable drawable to be converted
	 * @return a bitmap
	 */
	public static Bitmap convertDrawableToBitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		int width = drawable.getIntrinsicWidth();
		width = width > 0 ? width : 1;
		int height = drawable.getIntrinsicHeight();
		height = height > 0 ? height : 1;

		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(width, height, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);

		return bitmap;
	}

	//get resized bitmap, calculate sample size and all

	// get increased bitmap

	/**
	 * Gets {@link BitmapDetails} for given bitmap;
	 * @param bitmap -{@link Bitmap} image.
	 * @return - {@link BitmapDetails} details object.
	 * 
	 * @throws NullPointerException if any of the parameters is null.
	 */
	public static BitmapDetails getBitmapDetails(Bitmap bitmap) {
		BitmapDetails details=null;
		if(StringUtils.isNull(bitmap)){
			throw new NullPointerException("bitmap cannot be null");
		}
		details =new BitmapDetails();
		details.setHeight(bitmap.getHeight());
		details.setWidth(bitmap.getWidth());
		return details;
	}



}
