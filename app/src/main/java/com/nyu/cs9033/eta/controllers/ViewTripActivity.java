package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewTripActivity extends Activity
{

	private static final String TAG = "ViewTripActivity";
	private TextView txtTripDescription;
	private TextView txtTripName;
	private TextView txtTripDate;
	private TextView txtTitle;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_trip);
		findViewById();
		Trip trip = getTrip(getIntent());
		viewTrip(trip);
	}

	public void findViewById()
	{
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTripName = (TextView) findViewById(R.id.viewName);
		txtTripDescription = (TextView) findViewById(R.id.viewDescription);
		txtTripDate = (TextView) findViewById(R.id.viewDate);
	}
	
	/**
	 * Create a Trip object via the recent trip that
	 * was passed to TripViewer via an Intent.
	 * 
	 * @param intent The Intent that contains
	 * the most recent trip data.
	 * 
	 * @return The Trip that was most recently
	 * passed to TripViewer, or null if there
	 * is none.
	 */
	public Trip getTrip(Intent intent)
	{
		Trip trip = (Trip)intent.getParcelableExtra("recentTrip");
		return trip;
	}

	/**
	 * Populate the View using a Trip model.
	 * 
	 * @param trip The Trip model used to
	 * populate the View.
	 */
	public void viewTrip(Trip trip)
	{
		if (trip !=null)
		{
			txtTitle.setText("Recent Trip");
			txtTripName.setText(trip.getName());
			txtTripDescription.setText(trip.getDescription());
			txtTripDate.setText(trip.getDate());
			findViewById(R.id.rowTripName).setVisibility(View.VISIBLE);
			findViewById(R.id.rowTripDescription).setVisibility(View.VISIBLE);
			findViewById(R.id.rowTripDate).setVisibility(View.VISIBLE);
		}
		else
		{
			txtTitle.setText("No Recent Trip");
			findViewById(R.id.rowTripName).setVisibility(View.INVISIBLE);
			findViewById(R.id.rowTripDescription).setVisibility(View.INVISIBLE);
			findViewById(R.id.rowTripDate).setVisibility(View.INVISIBLE);
		}
	}
}
