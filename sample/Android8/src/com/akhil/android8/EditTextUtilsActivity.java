package com.akhil.android8;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.aj.android.commons.java.StringUtils;
import com.aj.android.commons.view.EditTextUtils;

public class EditTextUtilsActivity extends Activity implements TextWatcher, OnItemSelectedListener, OnClickListener {

	EditText edtInput,edtMin,edtMax;
	Spinner spnEditTextOptions;
	TextView txvInfo;
	Button btnInputClear;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editextutil);
		init();
	}

	private void init() {
		edtInput=(EditText)findViewById(R.id.edtInput);
		edtMin=(EditText)findViewById(R.id.edtMin);
		edtMax=(EditText)findViewById(R.id.edtMax);
		spnEditTextOptions=(Spinner)findViewById(R.id.spnEditTextOptions);
		btnInputClear=(Button)findViewById(R.id.btnInputClear);
		
		txvInfo=(TextView)findViewById(R.id.txvInfo);
		btnInputClear.setOnClickListener(this);
		spnEditTextOptions.setOnItemSelectedListener(this);
		edtInput.addTextChangedListener(this);
	}

	@Override
	public void afterTextChanged(Editable arg0) {
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		setInfo();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		setInfo();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	void setInfo(){
		StringBuilder builder=new StringBuilder();
		if(!EditTextUtils.isEditTextEmpty(edtInput)){

			builder.append("getString="+EditTextUtils.getString(edtInput));
			builder.append("\n");
			
			builder.append("isEmailValid="+EditTextUtils.isEmailValid(edtInput));
			builder.append("\n");
			getMinMax(builder);
			getStringUtils(builder);
		}else{

			builder.append("Please input text");
			builder.append("\n");
		}
		
		txvInfo.setText(builder);
	}

	@Override
	public void onClick(View view) {
		EditTextUtils.clearEditText(edtInput);
		EditTextUtils.clearEditText(edtMin);
		EditTextUtils.clearEditText(edtMax);
	}
	
	void getMinMax(StringBuilder builder){
		if(spnEditTextOptions.getSelectedItemPosition()==0){
			long min,max;
			if(!EditTextUtils.isEditTextEmpty(edtMin)){
				min=EditTextUtils.getLong(edtMin)==null?0:EditTextUtils.getLong(edtMin);
			}else{
				min=0;
			}
			if(!EditTextUtils.isEditTextEmpty(edtMax)){
				max=EditTextUtils.getLong(edtMax)==null?0:EditTextUtils.getLong(edtMax);
			}else{
				max=0;
			}
			System.out.println("min="+min+ "max="+max);
			builder.append("EditTextUtils.isWithinRange("+min+","+max+")="+EditTextUtils.isWithinRange(edtInput, min, max));
			builder.append("\n");
		}
		else{
			double min,max;
			if(!EditTextUtils.isEditTextEmpty(edtMin)){
				min=EditTextUtils.getDouble(edtMin)==null?0:EditTextUtils.getDouble(edtMin);
			}else{
				min=0;
			}
			if(!EditTextUtils.isEditTextEmpty(edtMax)){
				max=EditTextUtils.getDouble(edtMax)==null?0:EditTextUtils.getDouble(edtMax);
			}else{
				max=0;
			}
			
			builder.append("EditTextUtils.isWithinRange("+min+","+max+")="+EditTextUtils.isWithinRange(edtInput, min, max));
			builder.append("\n");
		}
	}
	
	void getStringUtils(StringBuilder builder){
		String word =EditTextUtils.getString(edtInput);
		builder.append("<----------------------------------->");
		builder.append("\n");
		if(!StringUtils.isEmpty(word)){
			
			builder.append("asLowerCaseFirstChar-"+StringUtils.asLowerCaseFirstChar(word));
			builder.append("\n");
			
			builder.append("asUpperCaseFirstChar-"+StringUtils.asUpperCaseFirstChar(word));
			builder.append("\n");
			
			builder.append("toCamelCase-"+StringUtils.toCamelCase(Locale.US, word));
			builder.append("\n");
			
			builder.append("isURLValid-"+StringUtils.isURLValid(word));
			builder.append("\n");
			
			builder.append("isEmailValid-"+StringUtils.isEmailValid(word));
			builder.append("\n");
			
			StringUtils.trimWhiteSpace(word);
			builder.append("trimWhiteSpace-").append(word);
			builder.append("\n");
		}
		builder.append("isBlank-"+StringUtils.isBlank(word));
		builder.append("\n");
		
		builder.append("isEmpty-"+StringUtils.isEmpty(word));
		builder.append("\n");
	}
}
