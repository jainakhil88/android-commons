package com.akhil.android8;

import com.aj.android.commons.java.ColorUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ColorUtilActivity extends Activity implements OnClickListener {
	
	private static final String TAG =ColorUtilActivity.class.getName();

	RelativeLayout relBaseColor,relLight10,relLight50,relDark10,relDark50;
	TextView txvRandomColor;
	Button btnSurpriseMe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.colorutil);
		init();
		setColors();
	}


	private void init() {
		
		relBaseColor=(RelativeLayout)findViewById(R.id.relBaseColor);
		relLight10=(RelativeLayout)findViewById(R.id.relLight10);
		relLight50=(RelativeLayout)findViewById(R.id.relLight50);
		relDark10=(RelativeLayout)findViewById(R.id.relDark10);
		relDark50=(RelativeLayout)findViewById(R.id.relDark50);
		
		txvRandomColor=(TextView)findViewById(R.id.txvRandomColor);
		btnSurpriseMe=(Button)findViewById(R.id.btnSurpriseMe);
		btnSurpriseMe.setOnClickListener(this);
	}
	
	
	private void setColors() {
		
		int randomColor=ColorUtils.getRandomColor();
		relBaseColor.setBackgroundColor(randomColor);
		
		txvRandomColor.setText(ColorUtils.getHexDecimalColor(randomColor));
		relLight10.setBackgroundColor(ColorUtils.getDesaturatedColor(randomColor));
		
		int desaturatedColor=ColorUtils.getDesaturatedColor(randomColor);
		for(int i=0;i<4;i++){
			desaturatedColor=ColorUtils.getDesaturatedColor(desaturatedColor);
		}
		relLight50.setBackgroundColor(desaturatedColor);
		
		
		relDark10.setBackgroundColor(ColorUtils.getSaturatedColor(randomColor));
		
		int saturatedColor=ColorUtils.getSaturatedColor(randomColor);
		for(int i=0;i<4;i++){
			saturatedColor=ColorUtils.getSaturatedColor(saturatedColor);
		}
		relDark50.setBackgroundColor(saturatedColor);
		
	}


	@Override
	public void onClick(View arg0) {
		setColors();
	}
	
}
