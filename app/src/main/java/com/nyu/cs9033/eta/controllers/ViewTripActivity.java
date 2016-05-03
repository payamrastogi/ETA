package com.nyu.cs9033.eta.controllers;

import com.nyu.cs9033.eta.database.TripDatabaseHelper;
import com.nyu.cs9033.eta.http.HttpTask;
import com.nyu.cs9033.eta.http.JSONListener;
import com.nyu.cs9033.eta.models.Location;
import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;
import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.service.IBaseGpsListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ViewTripActivity extends Activity implements IBaseGpsListener
{
	private static final String TAG = "ViewTripActivity";
	private TextView txtTripDescription;
	private TextView txtTripName;
	private TextView txtTripDate;
	private TextView txtTitle;
	private TextView txtJoeTime;
	private TextView txtJoeDistance;
	private TextView txtJohnTime;
	private TextView txtJohnDistance;
	private Trip trip;
	private Button buttonStartTrip;
	private Button buttonArrived;
	private List<Person> persons;
	private Location location;
	private Boolean isActive;
	private final static String STATUS_ACTIVE = "ACTIVE";
	private final static String STATUS_COMPLETED = "COMPLETED";
	private static Timer timer;
	private long startTime;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_trip);
		findViewsById();
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 20, this);
		Trip trip = getTrip(getIntent());
		Log.d(TAG, "View TRIP ID:"+trip.getId()+"");
		this.isActive = new Boolean(false);
		//new DBSelectTask().execute(trip.getId());
		TripDatabaseHelper db = new TripDatabaseHelper(ViewTripActivity.this);
		trip = db.getTripById(trip.getId());
		viewTrip(trip);
		if(isActive)
		{
			Log.d(TAG, "Active");
			startTime = System.currentTimeMillis();
			timer = new Timer();
			timer.schedule(new CheckTripStatusTask(trip.getId()), 1000L, 2000L);
		}
		else
		{
			Log.d(TAG, "Not Active");
		}
	}

	public void findViewsById()
	{
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		txtTripName = (TextView) findViewById(R.id.viewName);
		txtTripDescription = (TextView) findViewById(R.id.viewDescription);
		txtTripDate = (TextView) findViewById(R.id.viewDate);
		buttonStartTrip = (Button) findViewById(R.id.buttonStartTrip);
		buttonStartTrip.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				isActive = true;
				Log.d(TAG, "start trip clicked");
				DBUpdateTask dbUpdateTask = new DBUpdateTask(trip.getId());
				dbUpdateTask.execute(STATUS_ACTIVE);
				buttonStartTrip.setEnabled(false);
				timer = new Timer();
				timer.schedule(new CheckTripStatusTask(trip.getId()), 1000L, 2000L);
			}
		});
		buttonArrived = (Button)findViewById(R.id.buttonArrived);
		buttonArrived.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.d(TAG, "arrived clicked");
				DBUpdateTask dbUpdateTask = new DBUpdateTask(trip.getId());
				dbUpdateTask.execute(STATUS_COMPLETED);
				buttonStartTrip.setEnabled(true);
				isActive = false;
			}
		});
		txtJoeDistance = (TextView)findViewById(R.id.viewJoeDistance);
		txtJoeTime = (TextView)findViewById(R.id.viewJoeTime);
		txtJohnDistance = (TextView)findViewById(R.id.viewJohnDistance);
		txtJohnTime = (TextView) findViewById(R.id.viewJohnTime);
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
		trip = intent.getParcelableExtra("trip");
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

	private class DBUpdateTask extends AsyncTask<String, Void, Void>
	{
		long tripId;
		DBUpdateTask(long tripId)
		{
			this.tripId = tripId;
		}

		protected Void doInBackground(String... statuses)
		{
			Log.d(TAG, "DBUpdateTask Background");
			TripDatabaseHelper db = new TripDatabaseHelper(ViewTripActivity.this);
			db.updateTripStatus(tripId, statuses[0]);
			return null;
		}
	}

	private class DBSelectTask extends AsyncTask<Long, Void, Trip>
	{
		protected Trip doInBackground(Long... tripIds)
		{
			Log.d(TAG, "DBSelectTask Background");
			TripDatabaseHelper db = new TripDatabaseHelper(ViewTripActivity.this);
			Trip trip = db.getTripById(tripIds[0]);
			return trip;
		}

		@Override
		protected void onPostExecute(Trip trip)
		{
			if(trip.getStatus().equals("ACTIVE"))
			{
				Log.d(TAG, "DBSelectTask onPostExecute");
				isActive = true;
				buttonStartTrip.setEnabled(false);
			}
		}
	}

	@Override
	public void onLocationChanged(android.location.Location location)
	{
		// TODO Auto-generated method stub
		if (location != null && isActive)
		{
			updateLocation(location);
		}
	}

	@Override
	public void onProviderDisabled(String provider)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onGpsStatusChanged(int event)
	{
		// TODO Auto-generated method stub
	}

	/*
        {"command": "UPDATE_LOCATION", "latitude": 0,
          "longitude": 0, "datetime": 1382591009}
        {"response_code": 0}
     */
	public void updateLocation(android.location.Location location)
	{
		JSONObject objRequest = new JSONObject();
		try
		{
			objRequest.put("command", "UPDATE_LOCATION");
			objRequest.put("latitude", location.getLatitude());
			objRequest.put("longitude",location.getLongitude());
			objRequest.put("datetime", new Date().getTime());

			HttpTask httpTask  = new HttpTask();
			httpTask.setJsonListener(new JSONListener()
			{
				@Override
				public void jsonReceivedSuccessfully(String json)
				{
					try
					{
						JSONObject jsonObject = new JSONObject(json);
						if (jsonObject.getInt("response_code") == 0)
						{
							Log.d(TAG, "details updated");
						}
					} catch (JSONException e) {
						Log.e(TAG, e.getMessage());
					}
				}

				@Override
				public void jsonReceivedFailed()
				{

				}
			});
			httpTask.execute(objRequest);
		}
		catch(JSONException e)
		{
			Log.e(TAG, e.getMessage());
		}
	}

	/*
         {"command": "TRIP_STATUS", "trip_id": 3645686546}
         {"distance_left": [20.399999999999999,
            6.7000000000000002], "time_left": [1920, 900], "people": ["Joe Smith", "John
            Doe"]}
     */
	public void tripStatus(long tripId)
	{
		Log.d(TAG, "tripStatus");
		JSONObject objRequest = new JSONObject();
		try
		{
			objRequest.put("command", "TRIP_STATUS");
			objRequest.put("trip_id", tripId);

			HttpTask httpTask  = new HttpTask();
			httpTask.setJsonListener(new JSONListener() {
				@Override
				public void jsonReceivedSuccessfully(String json) {
					Log.d(TAG, "Received:"+json);
					try {
						JSONObject jsonObject = new JSONObject(json);
						if (jsonObject!=null) {
							Log.d(TAG, "details fetched");
							JSONArray distanceLeft = jsonObject.getJSONArray("distance_left");
							JSONArray timeLeft = jsonObject.getJSONArray("time_left");
							JSONArray people = jsonObject.getJSONArray("people");
							txtJoeDistance.setText(distanceLeft.getDouble(0)+"");
							txtJoeTime.setText(timeLeft.getLong(0)+"");
							txtJohnDistance.setText(distanceLeft.getDouble(1)+"");
							txtJohnDistance.setText(timeLeft.getLong(1)+"");
						}
					} catch (JSONException e) {
						Log.e(TAG, e.getMessage());
					}
				}

				@Override
				public void jsonReceivedFailed() {
					Log.d(TAG, "jsonReceivedFailed");
				}
			});
			httpTask.execute(objRequest);
		}
		catch(JSONException e)
		{
			Log.e(TAG, e.getMessage());
		}
	}

	private class CheckTripStatusTask extends TimerTask
	{
		long tripId;
		CheckTripStatusTask(long tripId)
		{
			this.tripId = tripId;
		}

		public void run()
		{
			Log.d(TAG, "CheckTripStatus background");
			TripDatabaseHelper db = new TripDatabaseHelper(ViewTripActivity.this);
			trip = db.getTripById(trip.getId());
			if(trip.getStatus().equals("ACTIVE"))
			tripStatus(tripId);
			else
				timer.cancel();
		}
	}
}
