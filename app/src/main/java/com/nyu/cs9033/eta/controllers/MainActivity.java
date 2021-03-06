package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.service.IBaseGpsListener;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity
{
	private static final String TAG = "MainActivity";
	private Trip recentTrip;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	}

	/**
	 * This method should start the
	 * Activity responsible for creating
	 * a Trip.
	 */
	public void startCreateTripActivity(View view)
	{

		Intent intent = new Intent(MainActivity.this, CreateTripActivity.class);
		startActivityForResult(intent, 1);
	}
	
	/**
	 * This method should start the
	 * Activity responsible for viewing
	 * a Trip.
	 */
	public void startViewTripActivity(View view)
	{
		Intent intent = new Intent(MainActivity.this, ViewTripActivity.class);
		intent.putExtra("recentTrip", recentTrip);
		startActivity(intent);
	}

	public void startTripHistoryActivity(View view)
	{
		Intent intent = new Intent(MainActivity.this, TripHistoryActivity.class);
		intent.putExtra("recentTrip", recentTrip);
		startActivity(intent);
	}
	
	/**
	 * Receive result from CreateTripActivity here.
	 * Can be used to save instance of Trip object
	 * which can be viewed in the ViewTripActivity.
	 * 
	 * Note: This method will be called when a Trip
	 * object is returned to the main activity. 
	 * Remember that the Trip will not be returned as
	 * a Trip object; it will be in the persisted
	 * Parcelable form. The actual Trip object should
	 * be created and saved in a variable for future
	 * use, i.e. to view the trip.
	 * 
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (data != null) {
			Trip result = data.getParcelableExtra("recentTrip");
			if (result != null)
				recentTrip = result;
		}
	}
}
