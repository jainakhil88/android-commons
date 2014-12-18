package com.akhil.android8;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.aj.android.commons.app.DeviceUtils;

public class DeviceUtilActivity extends Activity {
	
	public static final String TAG=DeviceUtilActivity.class.getName();
	
	TextView txvBluetooth,txvNfc,txvGps,txvCamera,txvFrontCamera;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deviceutil);
		
		init();
		txvCamera.setText(""+DeviceUtils.hasCamera(this));
		txvFrontCamera.setText(""+DeviceUtils.hasFrontCamera(this));
		txvBluetooth.setText(""+DeviceUtils.hasBluetooth(this));
		txvGps.setText(""+DeviceUtils.hasGPS(this));
		txvNfc.setText(""+DeviceUtils.hasNFC(this));
		/*Log.d(TAG, "device hasCamera="+);
		Log.d(TAG, "device hasGps="+DeviceUtils.hasGPS(this));
		Log.d(TAG, "device hasFrontCamera="+DeviceUtils.hasFrontCamera(this));
		Log.d(TAG, "device hasBluetooth="+DeviceUtils.hasBluetooth(this));
		Log.d(TAG, "device hasNFC="+DeviceUtils.hasNFC(this));*/
	}

	private void init() {
		txvBluetooth=(TextView)findViewById(R.id.txvBluetooth);
		txvFrontCamera=(TextView)findViewById(R.id.txvFrontCamera);
		txvCamera=(TextView)findViewById(R.id.txvCamera);
		txvGps=(TextView)findViewById(R.id.txvGps);
		txvNfc=(TextView)findViewById(R.id.txvNfc);
	}

	
}
