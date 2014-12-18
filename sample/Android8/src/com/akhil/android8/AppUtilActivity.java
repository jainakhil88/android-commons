package com.akhil.android8;

import com.aj.android.commons.app.AppUtils;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class AppUtilActivity extends Activity {

//	private static final String TAG=AppUtilActivity.class.getName();
	TextView txvMessage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apputil);
		initialization();
		
	}

	private void initialization() {
		txvMessage=(TextView)findViewById(R.id.txvMessage);
		StringBuilder builder=new StringBuilder();
		
		try {
			builder.append("App directory=>"+AppUtils.getAppDirectory(this));
		
		
		appendNextLine(builder);
		builder.append("Package Name=>"+AppUtils.getPackageName(this));
		
		appendNextLine(builder);
		builder.append("App Version Code=>"+AppUtils.getVersionCode(this));
		
		appendNextLine(builder);
		builder.append("App Version Name=>"+AppUtils.getVersionName(this));
		
		appendNextLine(builder);
		builder.append("Has Permission("+android.Manifest.permission.ACCESS_CHECKIN_PROPERTIES+")=>"+AppUtils.hasPermission(this, android.Manifest.permission.ACCESS_CHECKIN_PROPERTIES));
		
		appendNextLine(builder);
		builder.append("isGoogleTV=>"+AppUtils.isGoogleTV(this));
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		txvMessage.setText(builder);
	}

	
	void appendNextLine(StringBuilder builder){
		builder.append("\n");
	}
}
