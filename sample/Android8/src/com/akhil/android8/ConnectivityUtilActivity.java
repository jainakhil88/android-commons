package com.akhil.android8;

import java.util.ArrayList;

import android.app.Activity;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.aj.android.commons.net.ConnectivityUtils;

public class ConnectivityUtilActivity extends Activity implements OnItemSelectedListener, OnCheckedChangeListener{

	private static final String TAG=ConnectivityUtilActivity.class.getName();

	Spinner spnNetworkInfo;
	RadioGroup rdgNetworkInfo;
	TextView txvNwInfo;
	ArrayList<NetworkInfo>networkInfoList;
//	ConnectivityUtils connectivityUtils;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connectivityutil);
		
		init();
		setNetworkList();		
	}

	private void init() {
		spnNetworkInfo=(Spinner)findViewById(R.id.spnNetworkInfo);
		rdgNetworkInfo=(RadioGroup)findViewById(R.id.rdgNetworkOptions);
		txvNwInfo=(TextView)findViewById(R.id.txvNwInfo);
		
		
		rdgNetworkInfo.setOnCheckedChangeListener(this);
		spnNetworkInfo.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		setNetworkInfo();
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
	
	private void setNetworkInfo() {
		Log.d(TAG, "setNetworkInfo");
		int position=spnNetworkInfo.getSelectedItemPosition();
		Log.d(TAG, "after postion="+position);
		if(position!=-1){
			Log.d(TAG, "inside if="+position);
			NetworkInfo info=networkInfoList.get(position);
			Log.d(TAG, "info="+info);
			//getting the network info and setting to text view
			txvNwInfo.setText(ConnectivityUtils.getNetworkInfo(info));
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		Log.d(TAG, "onCheckedChanged ");
		setNetworkList();
	}

	
	private void setNetworkList() {
		Log.d(TAG, "setNetworkList ");
		ArrayList<String> networkNames=new ArrayList<String>();
		
		if(rdgNetworkInfo.getCheckedRadioButtonId()==R.id.rdbAllNetwork){
			networkInfoList=ConnectivityUtils.getAllNetworkInfo(this);
		}else if(rdgNetworkInfo.getCheckedRadioButtonId()==R.id.rdbActiveNetwork){
			networkInfoList=ConnectivityUtils.getAllActiveNetworkInfo(this);
		}else{
			networkInfoList=new ArrayList<NetworkInfo>();
		}
		
		Log.d(TAG, "before networkInfoList.size ");
		if(networkInfoList.size()>0){
			for(NetworkInfo info:networkInfoList){
				networkNames.add(info.getTypeName()+"-"+info.getSubtypeName());
			}
			ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, networkNames);
			spnNetworkInfo.setAdapter(adapter);
			spnNetworkInfo.setEnabled(true);
		}else{
			Log.d(TAG, "else ");
//			networkInfoList.clear();
//			spnNetworkInfo.setAdapter(null);
			spnNetworkInfo.setEnabled(false);
			
			if(rdgNetworkInfo.getCheckedRadioButtonId()==R.id.rdbAllNetwork){
				txvNwInfo.setText("No network available");
			}else if(rdgNetworkInfo.getCheckedRadioButtonId()==R.id.rdbActiveNetwork){
				txvNwInfo.setText("No active network available");
			}else{
				setGeneralItems();
			}
		}
		
	}

	private void setGeneralItems() {
		StringBuilder builder=new StringBuilder();
		
		if(ConnectivityUtils.getActiveNetworkInfo(this)!=null){
			builder.append("activeNetworkInfo name="+ConnectivityUtils.getActiveNetworkInfo(this).getTypeName());;
			builder.append("\n");

			builder.append("isConnected="+ConnectivityUtils.isConnected(this));;
			builder.append("\n");

			builder.append("isConnectedFast="+ConnectivityUtils.isConnectedFast(this));;
			builder.append("\n");

			builder.append("isConnectedMobile="+ConnectivityUtils.isConnectedMobile(this));;
			builder.append("\n");

			builder.append("isConnectedWifi="+ConnectivityUtils.isConnectedWifi(this));;
			builder.append("\n");

			builder.append("isWifiConnected="+ConnectivityUtils.isWifiConnected(this));;
			builder.append("\n");

			builder.append("isLowBandwithNetwork="+ConnectivityUtils.isLowBandwidthNetwork(this));;
			builder.append("\n");

			NetworkInfo info=ConnectivityUtils.getActiveNetworkInfo(this);
			builder.append("isConnectionFast="+ConnectivityUtils.isConnectionFast(info.getType(), info.getSubtype()));
			builder.append("\n");

			txvNwInfo.setText(builder);
		}else{
			txvNwInfo.setText("No active network available");
		}
	}
	
}