package com.nyu.cs9033.eta.controllers;

import android.app.Activity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.database.TripDatabaseHelper;
import com.nyu.cs9033.eta.models.Trip;

import java.util.List;

/**
 * Created by payamrastogi on 3/30/16.
 */
public class TripHistoryActivity extends Activity implements View.OnClickListener
{
    private static final String TAG = "TripHistoryActivity";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        //findViewsById();
        //addTest();
        populate();
    }

    public void addTest()
    {
        for(int i=0;i<10;i++)
        {
            TableLayout tableLayout = (TableLayout) findViewById(R.id.tlTripHistory);
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            TextView txtView = new TextView(this);
            txtView.setText(i+"");
            txtView.setTag(i+"");
            //txtView.setOnTouchListener(this);
            txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "textview clicked"+view.getTag());

                }
            });
            txtView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.addView(txtView);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    public void populate()
    {
        TripDatabaseHelper tripDatabaseHelper = new TripDatabaseHelper(this);
        List<Trip> trips = tripDatabaseHelper.getTrips();
        for(Trip trip: trips)
        {
            addTrips(trip);
        }
    }

    public void addTrips(Trip trip)
    {
        /* Find Tablelayout defined in main.xml */
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tlTripHistory);
        TableRow tableRow = new TableRow(this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        TextView txtView = new TextView(this);
        txtView.setText(trip.getName() + " " + trip.getDate());
        txtView.setTag(trip.getId());
        //txtView.setOnTouchListener(this);
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "textview clicked");
            }
        });
        txtView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableRow.addView(txtView);
        tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public void findViewsById()
    {

    }

    @Override
    public void onClick(View v)
    {
        Toast.makeText(getApplicationContext(), "dfdsfdfee", Toast.LENGTH_SHORT).show();

        switch (v.getId())
        {


        }
    }

    //@Override
    //public boolean onTouch(View v, MotionEvent event)
    //{
        // check which textview it is and do what you need to do

        // return true if you don't want it handled by any other touch/click events after this
    //    return true;
    //}
}
