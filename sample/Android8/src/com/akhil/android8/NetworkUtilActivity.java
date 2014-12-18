package com.akhil.android8;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aj.android.commons.image.BitmapUtils;
import com.aj.android.commons.net.ConnectivityUtils;
import com.aj.android.commons.net.NetworkUtils;
import com.aj.android.commons.view.EditTextUtils;

public class NetworkUtilActivity extends Activity implements OnClickListener {
	
	EditText edtBitmapURL,edtJSONURL;
	Button btnDownloadBitmap,btnGetJson;
	ImageView imgDownloadedBitmap; 
	TextView txvDownloadInfo,txvJsonContent;
	static final String DEFAULT_BITMAP_URL="http://www.google.com/intl/en_ALL/images/logo.gif";
	static final String DEFAULT_JSON_URL="https://api.github.com/users/mralexgray/repos";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.networkutil);
		init();
	}

	private void init() {
		edtBitmapURL=(EditText)findViewById(R.id.edtBitmapURL);
		edtJSONURL=(EditText)findViewById(R.id.edtJSONURL);
		
		btnDownloadBitmap=(Button)findViewById(R.id.btnDownloadBitmap);
		btnGetJson=(Button)findViewById(R.id.btnGetJson);
		imgDownloadedBitmap=(ImageView)findViewById(R.id.imgDownloadedBitmap);
		txvDownloadInfo=(TextView)findViewById(R.id.txvDownloadInfo);
		txvJsonContent=(TextView)findViewById(R.id.txvJsonContent);
		
		edtBitmapURL.setText(DEFAULT_BITMAP_URL);
		edtJSONURL.setText(DEFAULT_JSON_URL);
		btnDownloadBitmap.setOnClickListener(this);
		btnGetJson.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.btnDownloadBitmap:checkUrlBeforeDownload();break;
		case R.id.btnGetJson:checkJsonUrlBeforeDownload();break;
		}
	}

	private void checkJsonUrlBeforeDownload() {
		if(EditTextUtils.isEditTextEmpty(edtJSONURL)){
			Toast.makeText(this, "Please input url", Toast.LENGTH_SHORT).show();
			edtJSONURL.requestFocus();
		}else{
			if(NetworkUtils.isValidURL(EditTextUtils.getString(edtJSONURL))){
				if(ConnectivityUtils.isConnected(this)){
					GetJsonAsyncTask asyncTask=new GetJsonAsyncTask(EditTextUtils.getString(edtJSONURL), this);
					asyncTask.execute();				
				}else{
					Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();	
				}
			}else{
				Toast.makeText(this, "Url is not valid", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void checkUrlBeforeDownload() {
		if(EditTextUtils.isEditTextEmpty(edtBitmapURL)){
			Toast.makeText(this, "Please input url", Toast.LENGTH_SHORT).show();
			edtBitmapURL.requestFocus();
		}else{
			if(NetworkUtils.isValidURL(EditTextUtils.getString(edtBitmapURL))){
				if(ConnectivityUtils.isConnected(this)){
					DownloadBitmapAsyncTask asyncTask=new DownloadBitmapAsyncTask(EditTextUtils.getString(edtBitmapURL), this);
					asyncTask.execute();
				}else{
					Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();	
				}
			}else{
				Toast.makeText(this, "Url is not valid", Toast.LENGTH_SHORT).show();
			}
		}
	}

	class DownloadBitmapAsyncTask extends AsyncTask<Void, Void, Void>{
		
		String TAG=DownloadBitmapAsyncTask.class.getName();
		String url;
		Context context;
		Bitmap downloadedBitmap;
		ProgressDialog pDialog;
		public DownloadBitmapAsyncTask(String url,Context context) {
			this.url=url;
			this.context=context;
		}
		
		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(context);
			pDialog.setTitle("Downloading");
			pDialog.setMessage("Please Wait...");
			pDialog.setIndeterminate(true);
			pDialog.show();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			try {
				downloadedBitmap=NetworkUtils.downloadBitmap(url);
			} catch (Exception e) {
				e.printStackTrace();
				downloadedBitmap=null;
			}
			return null;
		}
		
		private void hideProgress() {
			if(pDialog!=null){
				if(pDialog.isShowing()){
					pDialog.cancel();
					pDialog.hide();
				}
				pDialog=null;
			}
		}
		
		@Override
		protected void onPostExecute(Void result) {
			hideProgress();
			
			if(downloadedBitmap==null){
				Toast.makeText(context, "Failed to download image", Toast.LENGTH_SHORT).show();				
			}
			else{
				txvDownloadInfo.setText(BitmapUtils.getBitmapDetails(downloadedBitmap).toString());
				imgDownloadedBitmap.setImageBitmap(downloadedBitmap);
			}
		}
	}
	
	
	class GetJsonAsyncTask extends AsyncTask<Void, Void, String>{
		String TAG=GetJsonAsyncTask.class.getName();
		String url;
		Context context;
		ProgressDialog pDialog;
		
		GetJsonAsyncTask (String url,Context context) {
			this.url=url;
			this.context=context;
		}
		@Override
		protected void onPreExecute() {
			pDialog=new ProgressDialog(context);
			pDialog.setTitle("Getting data");
			pDialog.setMessage("Please Wait...");
			pDialog.setIndeterminate(true);
			pDialog.show();
		}
		
		@Override
		protected String doInBackground(Void... arg0) {
			String json=null;
			try{
				JSONObject object=NetworkUtils.getJSONFromUrlViaGet(url);
				json=object.toString();
			}catch(Exception e){
				json=null;
				e.printStackTrace();
			}
			return json;
		}
		
		private void hideProgress() {
			if(pDialog!=null){
				if(pDialog.isShowing()){
					pDialog.cancel();
					pDialog.hide();
				}
				pDialog=null;
			}
		}
		
		@Override
		protected void onPostExecute(String result) {
			hideProgress();
			
			if(result!=null){
				txvJsonContent.setText(result);
			}else{
				txvJsonContent.setText("Error while downloading data");
			}
		}
	}
}
