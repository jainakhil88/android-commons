package com.akhil.android8;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.aj.android.commons.app.AssetUtils;
import com.aj.android.commons.io.StreamUtils;

public class AssetUtilActivity extends Activity implements OnItemSelectedListener{

	private static final String TAG = AssetUtilActivity.class.getName();
	TextView txvAssetInfo;
	Spinner spnAssetOptions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.assetutil);
		init();
	}
	
	private void init() {
		txvAssetInfo=(TextView)findViewById(R.id.txvAssetInfo);
		spnAssetOptions=(Spinner)findViewById(R.id.spnAssetOptions);
		spnAssetOptions.setOnItemSelectedListener(this);
		Log.d(TAG,"R class "+R.raw.class.getName());
		
		ArrayList<Integer> files=AssetUtils.getListOfIdOfAllRawFileName(this);
		if(files.size()>0){
			for(int i=0;i<files.size();i++){
				String data=null;
				try {
					data = AssetUtils.getFileFromRaw(this, files.get(i));
					txvAssetInfo.setText(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void printList() {
		try {
			ArrayList<String> rawList=(ArrayList<String>) AssetUtils.getFileToListFromRaw(this, R.raw.myfile);
			ArrayList<String> assetList=(ArrayList<String>) AssetUtils.getFileToListFromAssets(this, "my_file.txt");//(this, R.raw.myfile);
			
			StringBuilder builder =new StringBuilder();
			builder.append("rawList-");
			builder.append("\n");
			
			for(String each:rawList){
//				System.out.println("each raw "+each);
				builder.append(""+each);
				builder.append("\n");
			}
			
			builder.append("assetList");
			builder.append("\n");
			
			for(String each:assetList){
//				System.out.println("each asset "+each);
				builder.append(""+each);
				builder.append("\n");
			}
			
			txvAssetInfo.setText(builder);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeToFile() {
		try {
			AssetUtils.copyFileFromAsset(this, "my_file.txt", new File(Environment.getExternalStorageDirectory()+"\\asad"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getResourceName() {
		String fileName=AssetUtils.getFileName(this, R.raw.myfile);
		txvAssetInfo.setText(fileName);
		Log.d(TAG, "getResourceName for R.raw.myfile "+fileName);
	}

	private void getStringFromAsset() {
		try {
			String data=AssetUtils.getAssetToString(this, "my_file.txt");
			txvAssetInfo.setText(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readUsingStream() {
		InputStream is=null;
		try {
			is= AssetUtils.getAsset(this, "my_file.txt");
			String data=StreamUtils.convertStreamToString(is);
			txvAssetInfo.setText(data);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			StreamUtils.closeQuietly(is);
		}
		
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,long arg3) {
			switch(pos){
			case 0:writeToFile();break;
			case 1:getStringFromAsset();break;
			case 2:getResourceName();break;
			case 3:printList();break;
			case 4:readUsingStream();break;
			}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

}
