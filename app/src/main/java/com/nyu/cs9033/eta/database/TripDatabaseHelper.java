package com.nyu.cs9033.eta.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import 	android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by payamrastogi on 3/21/16.
 */
public class TripDatabaseHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "trips";

    private static final String TABLE_TRIP = "trip";
    private static final String COLUMN_TRIP_ID = "_id"; // convention
    private static final String COLUMN_TRIP_DATE = "date";
    private static final String COLUMN_TRIP_DESTINATION = "destination";

    private static final String TABLE_LOCATION = "location";
    private static final String COLUMN_LOC_TRIPID = "trip_id";
    private static final String COLUMN_LOC_TIMESTAMP = "timestamp";
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
            + COLUMN_TRIP_ID + " integer primary key autoincrement, "
            + COLUMN_TRIP_DATE + " integer, "
            + COLUMN_TRIP_DESTINATION + " text)");
        // create location table
        db.execSQL("create table " + TABLE_LOCATION + "("
            + COLUMN_LOC_TRIPID + " integer references trip(_id), "
            + COLUMN_LOC_TIMESTAMP + " integer, "
            + COLUMN_LOC_LAT + " real, "
            + COLUMN_LOC_LONG + " real, "
            + COLUMN_LOC_ALT + " real, "
            + COLUMN_LOC_PROVIDER + " varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);

        // create tables again
        onCreate(db);
    }
}
