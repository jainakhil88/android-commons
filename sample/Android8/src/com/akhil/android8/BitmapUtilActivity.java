package com.akhil.android8;

import java.io.IOException;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.aj.android.commons.image.BitmapUtils;

public class BitmapUtilActivity extends Activity implements OnClickListener{

	private static final String TAG = BitmapUtilActivity.class.getName();

	ImageView imageView,imgRotate2Bitmap,imgShadow2Bitmap,imgBlur2Bitmap,imgReflect2Bitmap;
	
	ImageView imgCircle2Bitmap,imgSquare2Bitmap;
	Button btnSaveBitmap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bitmaputil);
		initialization();
	}
	
	private void initialization() {
		imageView=(ImageView)findViewById(R.id.imgDrawable2Bitmap);
		btnSaveBitmap=(Button)findViewById(R.id.btnSaveBitmap);
		imgRotate2Bitmap=(ImageView)findViewById(R.id.imgRotate2Bitmap);
		imgShadow2Bitmap=(ImageView)findViewById(R.id.imgShadow2Bitmap);
		imgBlur2Bitmap=(ImageView)findViewById(R.id.imgBlur2Bitmap);
		imgReflect2Bitmap=(ImageView)findViewById(R.id.imgReflect2Bitmap);
		imgSquare2Bitmap=(ImageView)findViewById(R.id.imgSquare2Bitmap);
		imgCircle2Bitmap=(ImageView)findViewById(R.id.imgCircle2Bitmap);
		
		btnSaveBitmap.setOnClickListener(this);
		
//		Bitmap bitmap=BitmapUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.ic_android));
		
		Bitmap bitmap=BitmapUtils.convertDrawableToBitmap(getResources().getDrawable(R.drawable.ic_android));
		
		Log.d(TAG, BitmapUtils.getBitmapDetails(bitmap).toString());
		imageView.setImageBitmap(bitmap);
		
		imgRotate2Bitmap.setImageBitmap(BitmapUtils.rotate(BitmapUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.ic_android)), 104.0f));
		
		
//		imgShadow2Bitmap.setImageBitmap(BitmapUtils.drawShadow(bitmap, 5));
//		
//		imgBlur2Bitmap.setImageBitmap(BitmapUtils.getBluredBitmap(bitmap));
		
		imgReflect2Bitmap.setImageBitmap(BitmapUtils.getReflectBitmap(bitmap,.1f));
		
		
		imgSquare2Bitmap.setImageBitmap(BitmapUtils.getSquareBitmap(BitmapUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.ic_android)), 30f));
		
		imgCircle2Bitmap.setImageBitmap(BitmapUtils.getRoundedCornerBitmap(BitmapUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.ic_android)),35f));
	}

	@Override
	public void onClick(View v) {
		try {
			BitmapUtils.saveBitmapToFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"android.jpg", BitmapUtils.drawable2Bitmap(getResources().getDrawable(R.drawable.ic_android)),Bitmap.CompressFormat.JPEG,100);
			Toast.makeText(this, "File saved successfully in ", Toast.LENGTH_LONG).show();
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
