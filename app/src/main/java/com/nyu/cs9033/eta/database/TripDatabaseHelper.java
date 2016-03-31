package com.nyu.cs9033.eta.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import 	android.database.sqlite.SQLiteOpenHelper;

import com.nyu.cs9033.eta.models.Location;
import com.nyu.cs9033.eta.models.Person;
import com.nyu.cs9033.eta.models.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by payamrastogi on 3/21/16.
 */
public class TripDatabaseHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "data.db";

    private static final String TABLE_TRIP = "trip";
    private static final String COLUMN_TRIP_ID = "_id"; // convention
    private static final String COLUMN_TRIP_NAME = "name";
    private static final String COLUMN_TRIP_DATE = "date";
    private static final String COLUMN_TRIP_DESCRIPTION = "description";

    private static final String TABLE_GROUP = "persons";
    private static final String COLUMN_GRP_TRIP_ID = "trip_id";
    private static final String COLUMN_GRP_PERSON_NAME= "person_name";

    private static final String TABLE_LOCATION = "location";
    private static final String COLUMN_LOC_TRIP_ID = "trip_id";
    private static final String COLUMN_LOC_NAME = "name";
    private static final String COLUMN_LOC_ADDRESS = "address";
    private static final String COLUMN_LOC_LAT = "latitude";
    private static final String COLUMN_LOC_LONG = "longitude";
    private static final String COLUMN_LOC_ALT = "altitude";
    private static final String COLUMN_LOC_PROVIDER = "provider";

    public TripDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // create trip table
        db.execSQL("create table " + TABLE_TRIP + "("
            + COLUMN_TRIP_ID + " integer primary key autoincrement,"
            + COLUMN_TRIP_NAME + " text, "
            + COLUMN_TRIP_DATE + " timestamp, "
            + COLUMN_TRIP_DESCRIPTION + " text)");
        // create location table
        db.execSQL("create table " + TABLE_LOCATION + "("
                + COLUMN_LOC_TRIP_ID + " integer references trip(_id), "
                + COLUMN_LOC_NAME + " text, "
                + COLUMN_LOC_ADDRESS + "text, "
                + COLUMN_LOC_LAT + " text, "
                + COLUMN_LOC_LONG + " text, "
                + COLUMN_LOC_ALT + " real, "
                + COLUMN_LOC_PROVIDER + " varchar(100))");
        // create group table
        db.execSQL("create table " + TABLE_GROUP + "("
                + COLUMN_GRP_TRIP_ID + " integer references trip(_id), "
                + COLUMN_GRP_PERSON_NAME + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
        // create tables again
        onCreate(db);
    }


    public boolean insertTrip(Trip trip, Location location, List<Person> personList)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TRIP_NAME, trip.getName());
        contentValues.put(COLUMN_TRIP_DATE, trip.getDate());
        contentValues.put(COLUMN_TRIP_DESCRIPTION, trip.getDescription());
        long id = db.insert(TABLE_TRIP, null, contentValues);

        contentValues = new ContentValues();
        contentValues.put(COLUMN_LOC_TRIP_ID, id);
        contentValues.put(COLUMN_LOC_NAME, location.getName());
        //contentValues.put(COLUMN_LOC_ADDRESS, location.getAddress());
        contentValues.put(COLUMN_LOC_LAT, location.getLatitude());
        contentValues.put(COLUMN_LOC_LONG, location.getLongitude());
        db.insert(TABLE_LOCATION, null, contentValues);

        for(Person person: personList)
        {
            contentValues = new ContentValues();
            contentValues.put(COLUMN_GRP_TRIP_ID, id);
            contentValues.put(COLUMN_GRP_PERSON_NAME, person.getName());
            db.insert(TABLE_GROUP, null, contentValues);
        }
        return true;
    }

    public List<Trip> getTrips()
    {
        final String TABLE_NAME = "trip";

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String[] data      = null;
        List<Trip> trips = new ArrayList<Trip>();
        if (cursor.moveToFirst())
        {
            do
            {
                Trip trip = new Trip();
                trip.setId(cursor.getLong(0));
                trip.setName(cursor.getString(1));
                trip.setDate(cursor.getString(2));
                trips.add(trip);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return trips;
    }
}
