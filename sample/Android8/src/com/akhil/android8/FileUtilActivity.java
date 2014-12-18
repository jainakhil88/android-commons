package com.akhil.android8;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aj.android.commons.app.ContentProviderUtils;
import com.aj.android.commons.io.FileUtils;
import com.aj.android.commons.view.EditTextUtils;

public class FileUtilActivity extends Activity implements OnClickListener{
	private static final String TAG=FileUtilActivity.class.getName();

	private static final int GET_FILE_REQUEST_CODE=100;
	
	EditText edtFilePath;
	Button btnOpenFile,btnWriteFile,btnDeleteFile;
	TextView txvDetails;
	RadioGroup rdgWriteOptions,rdgDeleteOptions;
	CheckBox chkDeleteShell,chkRecursiveDelete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fileutil);
		init();
	}

	private void init() {
		edtFilePath=(EditText)findViewById(R.id.edtFilePath);
		btnOpenFile=(Button)findViewById(R.id.btnOpenFile);
		btnWriteFile=(Button)findViewById(R.id.btnWriteFile);
		btnDeleteFile=(Button)findViewById(R.id.btnDeleteFile);
		
		chkDeleteShell=(CheckBox)findViewById(R.id.chkDeleteShell);
		chkRecursiveDelete=(CheckBox)findViewById(R.id.chkRecursiveDelete);
		
		rdgWriteOptions=(RadioGroup)findViewById(R.id.rdgWriteFileOptions);
		rdgDeleteOptions=(RadioGroup)findViewById(R.id.rdgDeleteFileOptions);
		
		txvDetails=(TextView)findViewById(R.id.txvDetails);
		btnOpenFile.setOnClickListener(this);
		btnWriteFile.setOnClickListener(this);
		btnDeleteFile.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.btnOpenFile:showFileChooser();break;
		case R.id.btnWriteFile:writeFile();break;
		case R.id.btnDeleteFile:confirmationDialog();break;
		}
	}
	
	private void confirmationDialog() {
		AlertDialog.Builder builder =new AlertDialog.Builder(this);
		builder.setTitle("Caution!");
		builder.setMessage("Are you sure you want to delete, File cannot be recovered");
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				deleteFile();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		
		builder.create().show();
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
			int checkedId=rdgWriteOptions.getCheckedRadioButtonId();
			String originalFilePath=edtFilePath.getText().toString();
			String newFilePath=FileUtils.getFolderName(originalFilePath)+File.separator+"copy"
			+File.separator+FileUtils.getFileName(originalFilePath);
			String detailsFilePath=FileUtils.getFolderName(originalFilePath)+File.separator+FileUtils.getFileNameWithoutExtension(originalFilePath)+"_details.txt";
			try
			{
				if(checkedId==R.id.rdbWriteStreamString){
					FileUtils.writeStream(newFilePath, new FileInputStream(originalFilePath));
				}
				else if(checkedId==R.id.rdbWriteStreamFile){
					FileUtils.writeStream(new File(newFilePath), new FileInputStream(originalFilePath));
				}
				else if(checkedId==R.id.rdbWriteStringToFile){
					FileUtils.writeStringToFile(new File(detailsFilePath),txvDetails.getText().toString(),false);
				}
				else if(checkedId==R.id.rdbCopyFileString){
					String destPath=FileUtils.getFolderName(originalFilePath)+File.separator+"Copy of "+FileUtils.getFileName(originalFilePath);
					FileUtils.copyFile(originalFilePath,destPath);
				}
				else if(checkedId==R.id.rdbCopyFile){
					File destFile=new File(FileUtils.getFolderName(originalFilePath),"Copy of "+FileUtils.getFileName(originalFilePath));
					FileUtils.copyFile(new File(originalFilePath),destFile);
				}
			}catch(Exception e)
			{
				Log.e(TAG, "Exception-", e);
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		else{
			Toast.makeText(this, "Please Browse some file", Toast.LENGTH_LONG).show();
		}
	}
	
	private void deleteFile() {
		if(!EditTextUtils.isEditTextEmpty(edtFilePath)){
			int checkedId=rdgDeleteOptions.getCheckedRadioButtonId();
			String originalFilePath=edtFilePath.getText().toString();
			AlertDialog.Builder builder=new AlertDialog.Builder(this);
			builder.setTitle("Info!");
			boolean recursiveCheck=chkRecursiveDelete.isChecked();
			try
			{
				if(chkDeleteShell.isChecked()){
					if(checkedId==R.id.rdbDeleteFileString){
						FileUtils.deleteViaShell(originalFilePath, recursiveCheck);
					}else if(checkedId==R.id.rdbDeleteFile){
						FileUtils.deleteViaShell(new File(originalFilePath), recursiveCheck);
					}
				}else{
					if(checkedId==R.id.rdbDeleteFileString){
						FileUtils.deleteFile(originalFilePath, recursiveCheck);
					}else if(checkedId==R.id.rdbDeleteFile){
						FileUtils.deleteFile(new File(originalFilePath), recursiveCheck);
					}
				}
				builder.setMessage(FileUtils.getFileName(originalFilePath)+" deleted succesfully");
			}catch(Exception e)
			{
				Log.e(TAG, "Exception-", e);
				builder.setMessage(FileUtils.getFileName(originalFilePath)+" file was not deleted ");
			}
			builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
		else{
			Toast.makeText(this, "Please Browse some file", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==GET_FILE_REQUEST_CODE){
			if(resultCode==RESULT_OK){
				Uri uri = data.getData();
				if(uri!=null){

					String filePath = null;
					/*if(uri.getScheme().contains("content")){
						filePath=ContentProviderUtils.getRealPathFromURI(this,uri);
					}
					else{
						filePath=new File(URI.create(uri.toString())).getAbsolutePath();
					}*/
					filePath=ContentProviderUtils.getFilePathFromUri(this, uri);
					if(filePath!=null){
						edtFilePath.setText(filePath);
						txvDetails.setText(getText());
					}
				}
	              
			}
		}
	}

	private CharSequence getText() {
		StringBuilder builder=new StringBuilder();
		
		String path=edtFilePath.getText().toString();
		
		builder.append("File size :"+FileUtils.formatFileSize(path));
		builder.append("\n");
		
		builder.append("File name :"+FileUtils.getFileName(path));
		builder.append("\n");
		
		builder.append("File extension :"+FileUtils.getFileExtension(path));
		builder.append("\n");

		builder.append("File content type :"+FileUtils.getContentTypeFromFileString(path));
		builder.append("\n");
		
		String parentDirectory=FileUtils.getFolderName(path);
		builder.append("File parent path :"+parentDirectory);
		builder.append("\n");
		
		builder.append("Files in "+parentDirectory+" with directory included :"+FileUtils.getFileCount(parentDirectory,true));
		builder.append("\n");
		
		builder.append("Files in "+parentDirectory+" without directory included (only files) :"+FileUtils.getFileCount(parentDirectory,false));
		builder.append("\n");
		
		builder.append("Files in "+parentDirectory+" getRecursiveFileCount with directory included  :"+FileUtils.getRecursiveFileCount(parentDirectory,true));
		builder.append("\n");
		
		builder.append("Files in "+parentDirectory+" getRecursiveFileCount, without directory included (only files) :"+FileUtils.getRecursiveFileCount(parentDirectory,false));
		builder.append("\n");
		
		builder.append("Size of "+parentDirectory+" getDirectorySize, without sub directory included :"+FileUtils.formatFileSize(FileUtils.getDirectorySize(parentDirectory,false)));
		builder.append("\n");
		
		builder.append("Size of "+parentDirectory+" getDirectorySize, with sub directory included :"+FileUtils.formatFileSize(FileUtils.getDirectorySize(parentDirectory,true)));
		builder.append("\n");
		
		
		
		return builder;
	}
	
	
	
	
}
