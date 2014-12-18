package com.akhil.android8;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.aj.android.commons.app.AppUtils;
import com.aj.android.commons.app.DisplayUtils;

public class DisplayUtilActivity extends Activity {

	public static final String TAG=DisplayUtilActivity.class.getName();
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
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		appendNextLine(builder);
		builder.append("getScreenRawSize=>"+DisplayUtils.getScreenRawSize(this));
		
		appendNextLine(builder);
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		builder.append("getScreenRawSize=>"+DisplayUtils.getScreenRawSize(display));
		
		appendNextLine(builder);
		builder.append("getActionBarHeightInDp=>"+DisplayUtils.getActionBarHeightInDp(this));
		
		appendNextLine(builder);
		builder.append("getActionBarHeight=>"+DisplayUtils.getActionBarHeight(this));
		
		appendNextLine(builder);
		builder.append("getStatusBarHeight=>"+DisplayUtils.getStatusBarHeight(this));
		
		appendNextLine(builder);
		builder.append("getSystemBarHeight=>"+DisplayUtils.getSystemBarHeight(this));
		
		appendNextLine(builder);
		builder.append("getStatusBarHeightInDp=>"+DisplayUtils.getStatusBarHeightInDp(this));
		
		appendNextLine(builder);
		builder.append("getSystemBarHeightInDp=>"+DisplayUtils.getSystemBarHeightInDp(this));
		
		appendNextLine(builder);
		builder.append("getResourceValue=>"+DisplayUtils.getResourceValue(this,R.layout.deviceutil));
		
		appendNextLine(builder);
		builder.append("dpToPx(getActionBarHeight)=>"+DisplayUtils.dpToPx(this,DisplayUtils.getActionBarHeightInDp(this)));
		
		appendNextLine(builder);
		builder.append("dpToPx(getStatusBarHeight) =>"+DisplayUtils.dpToPx(this,DisplayUtils.getStatusBarHeightInDp(this)));
		
		appendNextLine(builder);
		builder.append("pxToDp(450)=>"+DisplayUtils.pxToDp(this, 450));
		txvMessage.setText(builder);
	}

	
	void appendNextLine(StringBuilder builder){
		builder.append("\n");
	}
}
