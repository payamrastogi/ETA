package com.nyu.cs9033.eta.http;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by payamrastogi on 4/9/16.
 */
public class HttpTask extends AsyncTask<JSONObject, Void, String>
{
    public static final String url = "http://cs9033-homework.appspot.com/";
    public static final String TAG = "HttpTask";

    JSONListener jsonListener;

    public void setJsonListener(JSONListener jsonListener)
    {
        this.jsonListener = jsonListener;
    }

    protected String doInBackground(JSONObject... object)
    {
        Log.d(TAG, "80909090");
        return makeRequest(object[0]);
    }

    public String makeRequest(JSONObject requestObject)
    {
        String json = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            httpPost.addHeader("Content-Type", "application/json; charset=utf-8");    // addHeader()
            httpPost.setEntity(new StringEntity(requestObject.toString(), "utf-8"));  // request data
            Log.d(TAG, "789087878");
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            Log.d(TAG, "4343434333");
            json = EntityUtils.toString(entity);

        }
        catch(UnsupportedEncodingException e )
        {
            Log.e(TAG, e.getMessage());
        }
        catch(ClientProtocolException e)
        {
            Log.e(TAG, e.getMessage());
        }
        catch(IOException e)
        {
            Log.e(TAG, e.getMessage());
        }
        return json;
    }

    @Override
    protected void onPostExecute(String json)
    {
        if(json != null)
            jsonListener.jsonReceivedSuccessfully(json);
        else
            jsonListener.jsonReceivedFailed();
    }
}
