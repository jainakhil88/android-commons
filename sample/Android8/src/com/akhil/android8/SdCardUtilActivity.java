package com.akhil.android8;

import com.aj.android.commons.io.FileUtils;
import com.aj.android.commons.io.SDCardUtils;
import com.aj.android.commons.java.MathUtils;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SdCardUtilActivity extends Activity {

	TextView txvSdCardDetails;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sdcardutil);
		init();
	}
	
	
	private void init() {
		txvSdCardDetails=(TextView)findViewById(R.id.txvSdCardDetails);
		
		
		txvSdCardDetails.setText(getText());
	}


	private CharSequence getText() {
		StringBuilder builder=new StringBuilder();
		
		builder.append("hasSdCard="+SDCardUtils.hasSdCard());
		builder.append("\n");
		
//		builder.append("getFreeSpace="+FileUtils.formatFileSize(SDCardUtils.getFreeSpace()));
//		builder.append("\n");
		
		builder.append("isSDCardWritable="+SDCardUtils.isSDCardWritable());
		builder.append("\n");
		
//		long randomSize=MathUtils.getRandomLong(5600000);
//		builder.append("hasFreeSpace for "+FileUtils.formatFileSize(randomSize)+"="+SDCardUtils.hasFreeSpace(randomSize));
//		builder.append("\n");
		
		return builder;
	}

	
}
