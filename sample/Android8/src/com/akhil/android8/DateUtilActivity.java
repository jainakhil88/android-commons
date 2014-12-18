package com.akhil.android8;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aj.android.commons.java.DateTimeUtils;
import com.aj.android.commons.java.DateTimeUtils.Month;
import com.aj.android.commons.java.MathUtils;
import com.aj.android.commons.java.DateTimeUtils.Weekday;
import com.aj.android.commons.view.EditTextUtils;

public class DateUtilActivity extends Activity implements OnClickListener, OnDateSetListener, OnTimeSetListener {

	private static final String TAG=DateUtilActivity.class.getName();

	Button btnDateSelect,btnTimeSelect,btnDate2Select,btnTime2Select;
	EditText edtDateSelect,edtTimeSelect,edtDate2Select,edtTime2Select;
	EditText selectedEditText;// to be used for temporary purposes when setting values
	TextView txvInformation;
	
	final String DATE_PICKER_FORMAT="dd-MMM-yyyy";
	final String CALENDAR_DEFAULT_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
	final String TIME_PICKER_FORMAT="HH:mm";
	final String DATE_TIME_FORMAT=DATE_PICKER_FORMAT+" "+TIME_PICKER_FORMAT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datetimeutil);
		init();
	}

	
	private void init() {
		edtDateSelect=(EditText)findViewById(R.id.edtDate);
		edtTimeSelect=(EditText)findViewById(R.id.edtTime);
		btnDateSelect=(Button)findViewById(R.id.btnDateSelect);
		btnTimeSelect=(Button)findViewById(R.id.btnTimeSelect);
		
		
		edtDate2Select=(EditText)findViewById(R.id.edtDate2);
		edtTime2Select=(EditText)findViewById(R.id.edtTime2);
		btnDate2Select=(Button)findViewById(R.id.btnDate2Select);
		btnTime2Select=(Button)findViewById(R.id.btnTime2Select);
		
		txvInformation=(TextView)findViewById(R.id.txvInformation);
		
		btnDateSelect.setOnClickListener(this);
		btnTimeSelect.setOnClickListener(this);
		btnDate2Select.setOnClickListener(this);
		btnTime2Select.setOnClickListener(this);
	}


	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.btnDateSelect:showDatePickerDialog(edtDateSelect);break;
		case R.id.btnTimeSelect:showTimePickerDialog(edtTimeSelect);break;
		case R.id.btnDate2Select:showDatePickerDialog(edtDate2Select);break;
		case R.id.btnTime2Select:showTimePickerDialog(edtTimeSelect);break;
		}
	}
	
	
	private void showDatePickerDialog(EditText editText) { 
		int year = 0, month = 0, date = 0;
		if (EditTextUtils.isEditTextEmpty(editText)) { 
			// set current date 
			year = Calendar.getInstance().get(Calendar.YEAR);
			month = Calendar.getInstance().get(Calendar.MONTH);
			date = Calendar.getInstance().get(Calendar.DATE);
		} else { 
			Date selectedDate;
			//set date from edit text
			try {
				selectedDate = DateTimeUtils.parseStringToDate(EditTextUtils.getString(editText), DATE_PICKER_FORMAT, Locale.US);
				Calendar calendar=Calendar.getInstance();
				calendar.setTimeInMillis(selectedDate.getTime());
				year = calendar.get(Calendar.YEAR);
				month = calendar.get(Calendar.MONTH);
				date = calendar.get(Calendar.DATE);
			} catch (ParseException e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		} 
		
		DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, date);
		datePickerDialog.setTitle("Select Date");
		selectedEditText=editText;
		datePickerDialog.show();
	} 
	
	private void showTimePickerDialog(EditText editText) { 
		int hour=0, minute=0;
		if (EditTextUtils.isEditTextEmpty(editText)) { 
			// set current date 
			hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			minute = Calendar.getInstance().get(Calendar.MINUTE);
		} else { 
			Date selectedDate;
			//set date from edit text
			try {
				selectedDate = DateTimeUtils.parseStringToDate(EditTextUtils.getString(editText), DATE_PICKER_FORMAT, Locale.US);
				Calendar calendar=Calendar.getInstance();
				calendar.setTimeInMillis(selectedDate.getTime());
				
				hour = calendar.get(Calendar.HOUR_OF_DAY);
				minute = calendar.get(Calendar.MINUTE);
			} catch (ParseException e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		} 
		
		TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, hour, minute, true);
		timePickerDialog.setTitle("Select Date");
		selectedEditText=editText;
		timePickerDialog.show();
	} 
	
	
	@Override 
	public void onDateSet(DatePicker datePicker, int year, int month,int dayOfMonth) {
		String selectedDate = dayOfMonth + "-" + (month + 1) + "-" + year;
		Log.v(TAG, "selected date="+selectedDate);
//		SimpleDateFormat dateParser = new SimpleDateFormat(DATE_PICKER_FORMAT, Locale.ENGLISH);
		try { 
			
//			Date another
			
			String formatedDate=DateTimeUtils.formatStringDateToString(selectedDate,"dd-MM-yyyy",DATE_PICKER_FORMAT,Locale.US);
			selectedEditText.setText(formatedDate);
			
			
			Date parsedDate=DateTimeUtils.parseStringToDate(selectedDate,"dd-MM-yyyy", Locale.US);
			formatedDate=DateTimeUtils.formatDatetoString(parsedDate,CALENDAR_DEFAULT_DATE_FORMAT,DATE_PICKER_FORMAT,Locale.US);
			System.out.println("second aprsed formatted date is "+formatedDate);			
			if(selectedEditText.getId()==R.id.edtDate){
				//if date for first date selection is set and second date selection is not set
				if(EditTextUtils.isEditTextEmpty(edtDate2Select)){
				
					//adding 1 month to selected date
					Date increasedDate=DateTimeUtils.addMonthsToDate(parsedDate, 1);
					edtDate2Select.setText(DateTimeUtils.parseDateToString(increasedDate, CALENDAR_DEFAULT_DATE_FORMAT, DATE_PICKER_FORMAT, Locale.US));
				}
			}
			
			getInformation();
						
		} catch (ParseException e) { 
			e.printStackTrace();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		} 
	}
	


	@Override
	public void onTimeSet(TimePicker timePicker, int selectedHour,int selectedMinute) {
		String selectedDate = selectedHour + ":" + selectedMinute;
		Log.v(TAG, "selected time="+selectedDate);
		//		SimpleDateFormat dateParser = new SimpleDateFormat(DATE_PICKER_FORMAT, Locale.ENGLISH);
		try { 

			//			Date another

			String formatedDate=DateTimeUtils.formatStringDateToString(selectedDate,TIME_PICKER_FORMAT,TIME_PICKER_FORMAT,Locale.US);
			selectedEditText.setText(formatedDate);


			Date parsedDate=DateTimeUtils.parseStringToDate(selectedDate,TIME_PICKER_FORMAT, Locale.US);
			formatedDate=DateTimeUtils.formatDatetoString(parsedDate,CALENDAR_DEFAULT_DATE_FORMAT,TIME_PICKER_FORMAT,Locale.US);
			System.out.println("second parsed formatted time is "+formatedDate);			
			if(selectedEditText.getId()==R.id.edtTime){
				//if date for first date selection is set and second date selection is not set
				if(EditTextUtils.isEditTextEmpty(edtTime2Select)){

					//adding 10 minutes to selected time
					Date increasedDate=DateTimeUtils.addMinuteToDate(parsedDate, 10);
					edtTime2Select.setText(DateTimeUtils.parseDateToString(increasedDate, CALENDAR_DEFAULT_DATE_FORMAT, TIME_PICKER_FORMAT, Locale.US));
				}
			}
			getInformation();
		} catch (ParseException e) { 
			e.printStackTrace();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		} 

	}


	private void getInformation() {
		
		String time1=null;
		String time2=null;
		if(EditTextUtils.isEditTextEmpty(edtTimeSelect)){
			time1="00:00";		
		}else{
			time1=EditTextUtils.getString(edtTimeSelect);
		}
		
		if(EditTextUtils.isEditTextEmpty(edtTime2Select)){
			time2="00:00";		
		}else{
			time2=EditTextUtils.getString(edtTime2Select);
		}
		
		
		if(EditTextUtils.isEditTextEmpty(edtDateSelect)){
			Toast.makeText(this, "Please select date", Toast.LENGTH_LONG).show();
			edtDateSelect.requestFocus();
			return;
		}
		
		String selectedDate1=EditTextUtils.getString(edtDateSelect)+" "+time1;
		String selectedDate2=EditTextUtils.getString(edtDate2Select)+" "+time2;
		
		Date date1;
		try {
			date1 = DateTimeUtils.parseStringToDate(selectedDate1, DATE_TIME_FORMAT, Locale.US);
			Date date2=DateTimeUtils.parseStringToDate(selectedDate2, DATE_TIME_FORMAT, Locale.US);
			
			int randomAdder=MathUtils.getRandomInteger(60);
			
			StringBuilder builder=new StringBuilder();
			builder.append("add "+randomAdder+" hours to date 1="+formatDate(DateTimeUtils.addHoursToDate(date1, randomAdder)));
			builder.append("\n");

			builder.append("add "+randomAdder+" minute to date 1="+formatDate(DateTimeUtils.addMinuteToDate(date1, randomAdder)));
			builder.append("\n");
			
			builder.append("add "+randomAdder+" days to date 1="+formatDate(DateTimeUtils.addDaysToDate(date1, randomAdder)));
			builder.append("\n");
			
			builder.append("add "+randomAdder+" months to date 1="+formatDate(DateTimeUtils.addMonthsToDate(date1, randomAdder)));
			builder.append("\n");			

			builder.append("add "+randomAdder+" years to date 1="+formatDate(DateTimeUtils.addYearToDate(date1, randomAdder)));
			builder.append("\n\n");	
			
			builder.append("difference in milliseconds="+DateTimeUtils.calculateDiffInMilliSeconds(date1, date2));
			builder.append("\n");

			builder.append("difference in seconds="+DateTimeUtils.calculateDiffInSeconds(date1, date2));
			builder.append("\n");

			builder.append("difference in minutes="+DateTimeUtils.calculateDiffInMinutes(date1, date2));
			builder.append("\n");

			builder.append("difference in hours="+DateTimeUtils.calculateDiffInHours(date1, date2));
			builder.append("\n");

			builder.append("difference in days="+DateTimeUtils.calculateDiffInDays(date1, date2));
			builder.append("\n");

			builder.append("difference in weeks="+DateTimeUtils.calculateDiffInWeeks(date1, date2));
			builder.append("\n");

			builder.append("difference in months="+DateTimeUtils.calculateDiffInMonths(date1, date2));
			builder.append("\n");

			builder.append("difference in years="+DateTimeUtils.calculateDiffInYears(date1, date2));
			builder.append("\n\n");

			builder.append("days in month in date 1="+DateTimeUtils.getDaysinTheMonth(DateTimeUtils.getYearFromDate(date1), DateTimeUtils.getMonthFromDate(date1)));
			builder.append("\n");			 
			
			builder.append("last day in month in date 1="+formatDate(DateTimeUtils.getLastDayOfMonth(date1)));
			builder.append("\n");

			builder.append("previous month in date 1="+formatDate(DateTimeUtils.getPreviousMonth(date1)));
			builder.append("\n");			
			
			builder.append("previous week in date 1="+formatDate(DateTimeUtils.getPreviousWeek(date1)));
			builder.append("\n");	
			
			builder.append("next week in date 1="+formatDate(DateTimeUtils.getNextWeek(date1)));
			builder.append("\n");	
			
			builder.append("getFirstSundayOfMonth in date 1="+formatDate(DateTimeUtils.getFirstSundayOfMonth(date1)));
			builder.append("\n");
			
			builder.append("getLastSundayOfMonth in date 1="+formatDate(DateTimeUtils.getLastSundayOfMonth(date1)));
			builder.append("\n");
			
			builder.append("getFirstMondayOfMonth in date 1="+formatDate(DateTimeUtils.getFirstMondayOfMonth(date1)));
			builder.append("\n");
			
			builder.append("getLastMondayOfMonth in date 1="+formatDate(DateTimeUtils.getLastMondayOfMonth(date1)));
			builder.append("\n");
			
			int randomNth=MathUtils.getRandomInteger(4);
			builder.append("get "+randomNth+" friday in feburary from date 1="+formatDate(DateTimeUtils.getNthOfMonth(randomNth, Weekday.FRIDAY, Month.FEBRUARY, DateTimeUtils.getYearFromDate(date1))));
			builder.append("\n");
			
			builder.append("timeDifference="+DateTimeUtils.timeDifference(date1));
			builder.append("\n");
			
			txvInformation.setText(builder);
		} catch (ParseException e) {
			e.printStackTrace();
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
				
	}
	
	String formatDate(Date date) throws ParseException{
		return DateTimeUtils.parseDateToString(date, CALENDAR_DEFAULT_DATE_FORMAT, DATE_TIME_FORMAT, Locale.US);
	}
	
}
