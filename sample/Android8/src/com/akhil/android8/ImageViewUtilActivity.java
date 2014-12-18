package com.akhil.android8;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aj.android.commons.app.ContentProviderUtils;
import com.aj.android.commons.view.EditTextUtils;
import com.aj.android.commons.view.ImageViewUtils;
import com.aj.android.commons.view.ImageViewUtils.ScalingOptions;
import com.aj.android.commons.view.ViewUtils;

public class ImageViewUtilActivity extends Activity implements OnClickListener, OnItemSelectedListener{

	private static final String TAG=ImageViewUtilActivity.class.getName();
	private static final int GET_IMAGE_REQUEST_CODE=100;

	Button btnOpenImage;
	EditText edtFilePath;
	ImageView imgOriginal;
	TextView txvImageInfo;
	Spinner spnScalingOptions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.imageviewutil);
		init();
	}

	
	private void init() {
		edtFilePath=(EditText)findViewById(R.id.edtFilePath);
		btnOpenImage=(Button)findViewById(R.id.btnOpenImage);
		imgOriginal=(ImageView)findViewById(R.id.imgOriginal);
		txvImageInfo=(TextView)findViewById(R.id.txvImageInfo);
		spnScalingOptions=(Spinner)findViewById(R.id.spnScalingOptions);
		
		btnOpenImage.setOnClickListener(this);
		spnScalingOptions.setOnItemSelectedListener(this);
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnOpenImage:showImageChooser();break;
		}
	}
	
	private void showImageChooser() {
		Intent chooseFile;
		Intent intent;
		chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
		chooseFile.setType("image/*");
		intent = Intent.createChooser(chooseFile, "Choose Photo");
		startActivityForResult(intent, GET_IMAGE_REQUEST_CODE);		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==GET_IMAGE_REQUEST_CODE){
			if(resultCode==RESULT_OK){
				Uri selectedImageUri = data.getData();
				String filePath = null;
				if(selectedImageUri!=null){
					Log.d(TAG, "uri path "+selectedImageUri);
					filePath=ContentProviderUtils.getFilePathFromUri(this,selectedImageUri);
/*					if(VersionUtils.isPreKitKat()){
						if(selectedImageUri.getScheme().contains("content")){
						}
						else{
							filePath=new File(URI.create(selectedImageUri.toString())).getAbsolutePath();
						}
					}else{
						//for kitkat or above
						filePath=ContentProviderUtils.getFilePathFromUri(this, selectedImageUri);
						Log.d(TAG, "file path when kitkat or higher "+filePath);
					}
*/				}
				if(filePath!=null){
					edtFilePath.setText(filePath);
					setBitmap();
				}
			}
		}
	}


	private CharSequence setImageInfo() {
		StringBuilder builder =new StringBuilder();
		Point point=ImageViewUtils.getImageViewDimensions(imgOriginal);
		builder.append("Layout Dimension x="+getStringDimension(point.x));
		builder.append(",y="+getStringDimension(point.y));
		builder.append("\n");
		int p[]=ViewUtils.getViewSize(imgOriginal);
		builder.append("ViewSize width="+p[0]+" height="+p[1]);
		
		return builder;
	}

	String getStringDimension(int x){
		if(x==1){
			return "MATCH_PARENT";
		}else if(x==-1){
			return "WRAP_CONTENT";
		}else{
			return String.valueOf(x);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		setBitmap();
	}


	private void setBitmap() {
		if(!EditTextUtils.isEditTextEmpty(edtFilePath)){
			switch(spnScalingOptions.getSelectedItemPosition()){
			case 0:imgOriginal.setImageBitmap(ImageViewUtils.getScaledBitmap(EditTextUtils.getString(edtFilePath), 500, 500));break;
			case 1:imgOriginal.setImageBitmap(ImageViewUtils.getExactScaledBitmap(EditTextUtils.getString(edtFilePath), 500, 500));break;
			case 2:imgOriginal.setImageBitmap(ImageViewUtils.getBitmapBasedOnScalingOptions(EditTextUtils.getString(edtFilePath), 500, 500,ScalingOptions.MATCH_TO_SMALLER_DIMENSION));break;
			case 3:imgOriginal.setImageBitmap(ImageViewUtils.getBitmapBasedOnScalingOptions(EditTextUtils.getString(edtFilePath), 500, 500,ScalingOptions.MATCH_TO_LARGER_DIMENSION));break;
			case 4:imgOriginal.setImageBitmap(ImageViewUtils.getBitmapBasedOnScalingOptions(EditTextUtils.getString(edtFilePath), 500, 500,ScalingOptions.LARGER_THAN_VIEW_OR_FULL_SIZE));break;
			case 5:imgOriginal.setImageBitmap(ImageViewUtils.getBitmapBasedOnScalingOptions(EditTextUtils.getString(edtFilePath), 500, 500,ScalingOptions.ROUND_TO_CLOSEST_MATCH));break;
			case 6:imgOriginal.setImageBitmap(ImageViewUtils.getBitmapBasedOnScalingOptions(EditTextUtils.getString(edtFilePath), 500, 500,ScalingOptions.MATCH_TO_SMALLER_DIMENSION));break;
			case 7:imgOriginal.setImageBitmap(ImageViewUtils.getBitmapBasedOnScalingOptions(EditTextUtils.getString(edtFilePath), 500, 500,ScalingOptions.SMALLER_THAN_VIEW));break;
			case 8:imgOriginal.setImageBitmap(ImageViewUtils.getScaledBitmapFromBitmap(ImageViewUtils.getScaledBitmap(EditTextUtils.getString(edtFilePath), 500, 500),CompressFormat.PNG,0,500,500));break;
			case 9:imgOriginal.setImageBitmap(ImageViewUtils.getExactScaledBitmapFromBitmap(ImageViewUtils.getScaledBitmap(EditTextUtils.getString(edtFilePath), 500, 500),CompressFormat.PNG,0,500,500));break;
			case 10:imgOriginal.setImageBitmap(ImageViewUtils.getBitmapBasedOnScalingOptionsFromBitmap(ImageViewUtils.getScaledBitmap(EditTextUtils.getString(edtFilePath), 500, 500),CompressFormat.PNG,0,500,500,ScalingOptions.MATCH_TO_SMALLER_DIMENSION));break;
			}
			txvImageInfo.setText(setImageInfo());
		}
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
