package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewTripActivity extends Activity {

	private static final String TAG = "ViewTripActivity";
	private TextView tripDescriptiontTxt;
	private TextView tripNameTxt;
	private TextView tripDateTxt;
	private TextView title;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_trip);
		findViewById();
		Trip trip = getTrip(getIntent());
		viewTrip(trip);
	}

	public void findViewById() {
		title = (TextView) findViewById(R.id.title);
		tripNameTxt = (TextView) findViewById(R.id.viewName);
		tripDescriptiontTxt = (TextView) findViewById(R.id.viewDescription);
		tripDateTxt = (TextView) findViewById(R.id.viewDate);
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
	public Trip getTrip(Intent intent) {
		Trip trip = (Trip)intent.getParcelableExtra("recentTrip");
		return trip;
	}

	/**
	 * Populate the View using a Trip model.
	 * 
	 * @param trip The Trip model used to
	 * populate the View.
	 */
	public void viewTrip(Trip trip) {
		if (trip !=null) {
			title.setText("Recent Trip");
			tripNameTxt.setText(trip.getName());
			tripDescriptiontTxt.setText(trip.getDescription());
			tripDateTxt.setText(trip.getDate());
			findViewById(R.id.nameRow).setVisibility(View.VISIBLE);
			findViewById(R.id.descRow).setVisibility(View.VISIBLE);
			findViewById(R.id.dateRow).setVisibility(View.VISIBLE);
		} else {
			title.setText("No Recent Trip");
			findViewById(R.id.nameRow).setVisibility(View.INVISIBLE);
			findViewById(R.id.descRow).setVisibility(View.INVISIBLE);
			findViewById(R.id.dateRow).setVisibility(View.INVISIBLE);
		}
	}
}
