package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTripActivity extends Activity implements View.OnClickListener
{
	
	private static final String TAG = "CreateTripActivity";
	private EditText txtTripDescription;
	private EditText txtTripName;
	private EditText txtTripDate;
	private Button btnSave;
	private Button btnCancel;
	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;
	private Trip recentTrip;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_trip);
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		findViewsById();
	}

	public void findViewsById()
	{
		txtTripName = (EditText) findViewById(R.id.txtTripName);
		txtTripDescription = (EditText) findViewById(R.id.txtTripDescription);
		txtTripDate = (EditText) findViewById(R.id.txtTripDate);
		txtTripDate.setInputType(InputType.TYPE_NULL);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		setDateTimeField();
	}
	/**
	 * This method should be used to
	 * instantiate a Trip model object.
	 * 
	 * @return The Trip as represented
	 * by the View.
	 */
	public Trip createTrip()
	{
		String tripName = txtTripName.getText().toString();
		String tripDescription = txtTripDescription.getText().toString();
		String tripDate = txtTripDate.getText().toString();

		return new Trip(tripName, tripDescription, tripDate);
	}

	/**
	 * For HW2 you should treat this method as a 
	 * way of sending the Trip data back to the
	 * main Activity.
	 * 
	 * Note: If you call finish() here the Activity 
	 * will end and pass an Intent back to the
	 * previous Activity using setResult().
	 * 
	 * @return whether the Trip was successfully 
	 * saved.
	 */
	public boolean saveTrip(Trip trip)
	{

		Intent result = new Intent();
		result.putExtra("recentTrip",recentTrip);
		setResult(RESULT_OK, result);
		finish();
		return false;
	}

	/**
	 * This method should be used when a
	 * user wants to cancel the creation of
	 * a Trip.
	 * 
	 * Note: You most likely want to call this
	 * if your activity dies during the process
	 * of a trip creation or if a cancel/back
	 * button event occurs. Should return to
	 * the previous activity without a result
	 * using finish() and setResult().
	 */
	public void cancelTrip()
	{
		Intent i = new Intent(getBaseContext(), MainActivity.class);
		startActivity(i);
	}

	private void setDateTimeField()
	{
		txtTripDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				datePickerDialog.show();
			}
		});

		txtTripDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View view, boolean b) {
				if (b) {
					datePickerDialog.show();
				}
			}
		});
		txtTripDate.setKeyListener(null);
		Calendar newCalendar = Calendar.getInstance();
		datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				txtTripDate.setText(dateFormatter.format(newDate.getTime()));
			}
		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public void onClick(View v)
	{

		switch (v.getId())
		{

			case R.id.btnCancel:cancelTrip();
								break;

			case R.id.btnSave: 	String tripName = txtTripName.getText().toString();
								String tripDescription = txtTripDescription.getText().toString();
								String tripDate = txtTripDate.getText().toString();
								boolean invalid = false;
								if (tripName.equals(""))
								{
									invalid = true;
									Toast.makeText(getApplicationContext(), "Enter Trip Name",
														Toast.LENGTH_SHORT).show();
								}
								else if (tripDescription.equals(""))
								{
									invalid = true;
									Toast.makeText(getApplicationContext(),
											"Enter Trip Description", Toast.LENGTH_SHORT).show();
								}
								else if (tripDate.equals(""))
								{
									invalid = true;
									Toast.makeText(getApplicationContext(),
											"Enter your Trip Date", Toast.LENGTH_SHORT).show();
								}
								if (invalid == false)
								{
									recentTrip = createTrip();
									saveTrip(recentTrip);
								}
								break;
		}
	}
}
