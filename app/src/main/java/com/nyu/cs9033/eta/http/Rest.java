package com.nyu.cs9033.eta.http;

import android.util.Log;

import com.nyu.cs9033.eta.models.Location;
import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;
import java.util.List;

/**
 * Created by payamrastogi on 4/8/16.
 */
public class Rest
{
    public static final String TAG = "Rest";

    public void createTrip(final Location location, final List<Person> persons, final Trip trip)
    {
        /*
            {"command": "CREATE_TRIP", "location": [“location name”,
             ”address”, ”latitude”, ”longitude”], "datetime": 1382584629, "people": ["John
              Doe", "Joe Smith"]}
        */
        JSONObject objRequest = new JSONObject();
        int tripId=-1;
        try
        {
            objRequest.put("command", "CREATE_TRIP");

            JSONArray objLocation = new JSONArray();
            objLocation.put(location.getName());
            objLocation.put(location.getAddress());
            objLocation.put(location.getLatitude());
            objLocation.put(location.getLongitude());
            objRequest.put("location", objLocation);
            objRequest.put("datetime", new Date().getTime());
            JSONArray objPerson = new JSONArray();
            for(Person person: persons)
                objPerson.put(person);

            objRequest.put("people", objPerson);

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
                            Log.d(TAG, jsonObject.getInt("trip_id") + "98797977997");
                            trip.setId(jsonObject.getInt("trip_id"));
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



}
