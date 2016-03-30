package com.nyu.cs9033.eta.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.models.Trip;

/**
 * Created by payamrastogi on 3/30/16.
 */
public class TripHistoryActivity extends Activity implements View.OnClickListener
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);
        findViewsById();
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
}
