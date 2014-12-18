package com.akhil.android8;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aj.android.commons.app.AssetUtils;
import com.aj.android.commons.app.VersionUtils;

public class MainActivity extends Activity implements OnClickListener 
{
	public static final String TAG=MainActivity.class.getName();
	
	Button btnAppUtil,btnAssetUtil,btnBitmapUtil;
	Button btnColorUtil,btnConnectivityUtil;
	Button btnDateUtil,btnDeviceUtil;
	Button btnDisplayUtil,btnEditUtil;
	Button btnFileUtil,btnImageViewUtil;
	Button btnSdCardUtil,btnStreamUtil;
	Button btnNetworkUtil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		intialization();
//		methodCalling();
	}
	
	private void intialization() {
		btnAppUtil=(Button)findViewById(R.id.btnAppUtil);
		btnAssetUtil=(Button)findViewById(R.id.btnAssetUtil);
		btnBitmapUtil=(Button)findViewById(R.id.btnBitmapUtil);	
		btnColorUtil=(Button)findViewById(R.id.btnColorUtil);
		btnConnectivityUtil=(Button)findViewById(R.id.btnConnectivityUtil);
		btnDeviceUtil=(Button)findViewById(R.id.btnDeviceUtil);
		btnDisplayUtil=(Button)findViewById(R.id.btnDisplayUtil);
		btnDateUtil=(Button)findViewById(R.id.btnDateUtil);
		btnEditUtil=(Button)findViewById(R.id.btnEditUtil);
		btnFileUtil=(Button)findViewById(R.id.btnFileUtil);
		btnImageViewUtil=(Button)findViewById(R.id.btnImageViewUtil);
		btnNetworkUtil=(Button)findViewById(R.id.btnNetworkUtil);
		btnSdCardUtil=(Button)findViewById(R.id.btnSdCardUtil);
		btnStreamUtil=(Button)findViewById(R.id.btnStreamUtil);
		
		
		btnAppUtil.setOnClickListener(this);
		btnAssetUtil.setOnClickListener(this);
		btnBitmapUtil.setOnClickListener(this);
		btnColorUtil.setOnClickListener(this);
		btnConnectivityUtil.setOnClickListener(this);
		btnDeviceUtil.setOnClickListener(this);
		btnDisplayUtil.setOnClickListener(this);
		btnDateUtil.setOnClickListener(this);
		btnEditUtil.setOnClickListener(this);
		btnFileUtil.setOnClickListener(this);
		btnImageViewUtil.setOnClickListener(this);
		btnSdCardUtil.setOnClickListener(this);
		btnStreamUtil.setOnClickListener(this);
		btnNetworkUtil.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		Intent intent=null;
		switch(view.getId()){
		case R.id.btnAppUtil:intent=new Intent(this,AppUtilActivity.class);break;
		case R.id.btnAssetUtil:intent=new Intent(this,AssetUtilActivity.class);break;
		case R.id.btnBitmapUtil:intent=new Intent(this,BitmapUtilActivity.class);break;
		case R.id.btnColorUtil:intent=new Intent(this,ColorUtilActivity.class);break;
		case R.id.btnConnectivityUtil:intent=new Intent(this,ConnectivityUtilActivity.class);break;
		case R.id.btnDeviceUtil:intent=new Intent(this,DeviceUtilActivity.class);break;
		case R.id.btnDisplayUtil:intent=new Intent(this,DisplayUtilActivity.class);break;
		case R.id.btnDateUtil:intent=new Intent(this,DateUtilActivity.class);break;
		case R.id.btnEditUtil:intent=new Intent(this,EditTextUtilsActivity.class);break;
		case R.id.btnFileUtil:intent=new Intent(this,FileUtilActivity.class);break;
		case R.id.btnImageViewUtil:intent=new Intent(this,ImageViewUtilActivity.class);break;
		case R.id.btnNetworkUtil:intent=new Intent(this,NetworkUtilActivity.class);break;
		case R.id.btnSdCardUtil:intent=new Intent(this,SdCardUtilActivity.class);break;
		case R.id.btnStreamUtil:intent=new Intent(this,StreamUtilActivity.class);break;
		
		}
		if(intent!=null){
			startActivity(intent);
		}
	}
	
	
	
	void methodCalling(){
//		checkExternalStoragePermission();
//		checkForVersion();

//		sqlRelated();
		assetRelated();
	}

	private void assetRelated() {
		String myFile=AssetUtils.getFileName(this, R.raw.myfile);
		Log.d(TAG, "raw file name "+myFile);
		
		/*myFile=AssetUtils.getFileName(this, 13);
		Log.d(TAG, "random file name "+myFile);*/
		
		try {
			String assetFile=AssetUtils.getAssetToString(this, "my_File.txt");
			Log.d(TAG, "asset string "+assetFile);
			assetFile=AssetUtils.getAssetToString(this, "my.txt");
			Log.d(TAG, "asset string "+assetFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void sqlRelated() {
		Mysql sql=new Mysql(this, null, null, 0);
		
		/*ContentValues values=new ContentValues();
		values.put(Mysql.COLUMN_AREA_ID	, 1);
		values.put(Mysql.COLUMN_AREA_NAME, "A");
		values.put(Mysql.COLUMN_AREA_CODE, "a");
		sql.save(values);
		
		values.put(Mysql.COLUMN_AREA_ID	, 2);
		values.put(Mysql.COLUMN_AREA_NAME, "B");
		values.put(Mysql.COLUMN_AREA_CODE, "b");
		sql.save(values);
		
		values.put(Mysql.COLUMN_AREA_ID	, 3);
		values.put(Mysql.COLUMN_AREA_NAME, "C");
		values.put(Mysql.COLUMN_AREA_CODE, "c");
		sql.save(values);
		
		values.put(Mysql.COLUMN_AREA_ID	, 4);
		values.put(Mysql.COLUMN_AREA_NAME, "D");
		values.put(Mysql.COLUMN_AREA_CODE, "d");
		sql.save(values);
		
		values.put(Mysql.COLUMN_AREA_ID	, 5);
		values.put(Mysql.COLUMN_AREA_NAME, "E");
		values.put(Mysql.COLUMN_AREA_CODE, "e");
		sql.save(values);*/
		
		sql.getRecords();
	}

	private void checkForVersion() 
	{
		Log.d(TAG, "getApiLevel => "+VersionUtils.getApiLevel());
		Log.d(TAG, "isFroyo => "+VersionUtils.isFroyo());
		Log.d(TAG, "isGingerbread => "+VersionUtils.isGingerbread());
		Log.d(TAG, "isPreHoneycomb => "+VersionUtils.isPreHoneycomb());
	}

	private void checkExternalStoragePermission() {
		String data="check";
		try{
//			new SDCardUtils(this).writeBytesToFile(Environment.getExternalStorageDirectory()+"\\check", data.getBytes());
//		} catch (IOException e) {
//			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}
