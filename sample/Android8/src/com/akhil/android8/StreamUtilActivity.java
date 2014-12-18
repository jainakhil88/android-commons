package com.akhil.android8;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aj.android.commons.app.ContentProviderUtils;
import com.aj.android.commons.io.FileUtils;
import com.aj.android.commons.io.StreamUtils;
import com.aj.android.commons.view.EditTextUtils;
import com.aj.android.commons.view.ViewUtils;

public class StreamUtilActivity extends Activity implements OnClickListener {
	
	private static final String TAG=StreamUtilActivity.class.getName();
	
	private static final int GET_FILE_REQUEST_CODE=100;
	
	EditText edtFilePath;
	Button btnOpenFile,btnWriteFile;
	RadioGroup rdgStreamOptions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.streamutil);
		init();
	}
	
	
	private void init() {
		edtFilePath=(EditText)findViewById(R.id.edtFilePath);
		btnOpenFile=(Button)findViewById(R.id.btnOpenFile);
		btnWriteFile=(Button)findViewById(R.id.btnWriteFile);
		rdgStreamOptions=(RadioGroup)findViewById(R.id.rdgStreamOptions);
		
		btnOpenFile.setOnClickListener(this);
		btnWriteFile.setOnClickListener(this);
	}


	@Override
	public void onClick(View view) {
		switch(view.getId()){
			case R.id.btnOpenFile:showFileChooser();break;
			case R.id.btnWriteFile:writeFile();break;
		}
	}


	private void showFileChooser() {
		Intent chooseFile;
		Intent intent;
		chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
		chooseFile.setType("file/*");
		intent = Intent.createChooser(chooseFile, "Choose a file");
		startActivityForResult(intent, GET_FILE_REQUEST_CODE);		
	}
	
	private void writeFile() {
		if(!EditTextUtils.isEditTextEmpty(edtFilePath)){
			int checkedId=rdgStreamOptions.getCheckedRadioButtonId();
			String originalFilePath=edtFilePath.getText().toString();
			try
			{
				if(checkedId==R.id.rdbWriteBytesToFile){
					StreamUtils.writeBytesToFile(getNewFilePathName(), StreamUtils.getBytesFromFile(originalFilePath));
				}
				else if(checkedId==R.id.rdbCopyInStream){
					StreamUtils.copy(new FileInputStream(originalFilePath), new FileOutputStream(getNewFilePathName()));
				}
				else if(checkedId==R.id.rdbCopyLarge){
					StreamUtils.copyLarge(new FileInputStream(originalFilePath), new FileOutputStream(getNewFilePathName()));
				}
				else if(checkedId==R.id.rdbCopyLargeCustom){
					StreamUtils.copyLarge(new FileInputStream(originalFilePath), new FileOutputStream(getNewFilePathName()),new byte[10*1024]);
				}
				else if(checkedId==R.id.rdbCopyFile){
				}
			}catch(Exception e)
			{
				Log.e(TAG, "Exception-", e);
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
	}

	private String getNewFilePathName() {
		String originalFilePath=edtFilePath.getText().toString();
		String sourceDirectory=FileUtils.getFolderName(originalFilePath)+File.separator;
		String fileName=FileUtils.getFileName(originalFilePath);
		String options=ViewUtils.getCheckedRadioButtonText(rdgStreamOptions);
		
		String outputFile=sourceDirectory+"copy using "+options+" of "+fileName;
		Log.d(TAG, "file path=>"+outputFile);
		return outputFile;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==GET_FILE_REQUEST_CODE){
			if(resultCode==RESULT_OK){
				  Uri uri = data.getData();
				  if(uri!=null){

						String filePath = null;
/*						if(uri.getScheme().contains("content")){
							filePath=ContentProviderUtils.getRealPathFromURI(this,uri);
						}
						else{
							filePath=new File(URI.create(uri.toString())).getAbsolutePath();
						}*/
						filePath=ContentProviderUtils.getFilePathFromUri(this, uri);
						if(filePath!=null){
							edtFilePath.setText(filePath);
						}
					}
			}
		}
	}
	
}
