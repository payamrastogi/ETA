package com.nyu.cs9033.eta.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nyu.cs9033.eta.R;
import com.nyu.cs9033.eta.models.Trip;
import java.util.List;

/**
 * Created by payamrastogi on 4/1/16.
 */
public class TripAdapter extends ArrayAdapter<Trip>
{
    // View lookup cache
    private static class ViewHolder
    {
        TextView name;
    }

    public TripAdapter(Context context, List<Trip> trips)
    {
        super(context, 0, trips);
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        Trip trip = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_trip, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textViewName);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(trip.getName());
        viewHolder.name.setTag(trip.getId());
        // Return the completed view to render on screen
        return convertView;
    }
}
