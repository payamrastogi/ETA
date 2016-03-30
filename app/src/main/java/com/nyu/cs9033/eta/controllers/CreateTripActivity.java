package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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
	private Button btnPickContact;
	private Button btnSave;
	private Button btnCancel;
	private DatePickerDialog datePickerDialog;
	private SimpleDateFormat dateFormatter;
	private Trip recentTrip;
	private TextView resultText;


	private static final int RESULT_PICK_CONTACT = 85500;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//Toast.makeText(getApplicationContext(), "msg msg", Toast.LENGTH_SHORT).show();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_trip);
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		findViewsById();
	}

	public void findViewsById()
	{
		//Toast.makeText(getApplicationContext(), "msg msg 3", Toast.LENGTH_SHORT).show();
		txtTripName = (EditText) findViewById(R.id.txtTripName);
		txtTripDescription = (EditText) findViewById(R.id.txtTripDescription);
		txtTripDate = (EditText) findViewById(R.id.txtTripDate);
		txtTripDate.setInputType(InputType.TYPE_NULL);
		btnPickContact = (Button) findViewById(R.id.btnPickContact);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(this);
		btnPickContact.setOnClickListener(this);
		resultText = (TextView)findViewById(R.id.searchViewResult);
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
		result.putExtra("recentTrip", recentTrip);
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
		Toast.makeText(getApplicationContext(), "dfdsfdfee", Toast.LENGTH_SHORT).show();

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
			case R.id.btnPickContact:pickContact(v);
										break;
		}
	}

	public void pickContact(View v)
	{
		Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
		startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check whether the result is ok
		if (resultCode == RESULT_OK) {
			// Check for the request code, we might be usign multiple startActivityForReslut
			switch (requestCode) {
				case RESULT_PICK_CONTACT:
					contactPicked(data);
					break;
			}
		} else {
			Log.e("MainActivity", "Failed to pick contact");
		}
	}

	private void contactPicked(Intent data) {
		Cursor cursor = null;
		try {
			//String phoneNo = null ;
			String name = null;
			// getData() method will have the Content Uri of the selected contact
			Uri uri = data.getData();
			//Query the content uri
			cursor = getContentResolver().query(uri, null, null, null, null);
			cursor.moveToFirst();
			// column index of the phone number
			int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
			// column index of the contact name
			int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
			//phoneNo = cursor.getString(phoneIndex);
			name = cursor.getString(nameIndex);
			// Set the value to the textviews
			//resultText.setText(name);
			//TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
			//TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
			//EditText myEditText = new EditText(getApplicationContext());
			//myEditText.setLayoutParams(tableLayoutParams);
			//tableLayout.addView(myEditText);
			/* Find Tablelayout defined in main.xml */
			TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
			/* Create a new row to be added. */
			TableRow tableRow = new TableRow(this);
			tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
			/* Create a edit text to be the row-content. */
			EditText myEditText = new EditText(this);
			myEditText.setText(name);
			myEditText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
			/* Add Button to row. */
			tableRow.addView(myEditText);
			/* Add row to TableLayout. */
			//tableRow.setBackgroundResource(R.drawable.sf_gradient_03);
			tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
			resultText.setText(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
