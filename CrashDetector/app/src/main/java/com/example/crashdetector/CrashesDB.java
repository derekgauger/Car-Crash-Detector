package com.example.crashdetector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CrashesDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "crashes_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "my_crashes";
    private static final String ID_COL = "id";
    private static final String TIME_COL = "time";
    private static final String TYPE_COL = "type";
    private static final String SPEED_COL = "speed";
    private static final String ACCELERATION_COL = "acceleration";

    public CrashesDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createCmd = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TIME_COL + " TEXT, "
                + TYPE_COL + " TEXT, "
                + SPEED_COL + " REAL, "
                + ACCELERATION_COL + " REAL)";
        sqLiteDatabase.execSQL(createCmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addNewContact(Crash crash) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues currentEntry = new ContentValues();
        currentEntry.put(TIME_COL, crash.time);
        currentEntry.put(TYPE_COL, crash.type);
        currentEntry.put(SPEED_COL, crash.speed);
        currentEntry.put(ACCELERATION_COL, crash.acceleration);
        db.insert(TABLE_NAME, null, currentEntry);
        db.close();
    }

    public long getDbSize() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();
        System.out.println(count);
        return count;
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("Clearing Database...");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        System.out.println("Cleared Database.");
        db.close();
    }

    public List<Crash> getCrashes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<Crash> crashes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String time = cursor.getString(1);
                String type = cursor.getString(2);
                float speed = cursor.getFloat(3);
                float acceleration = cursor.getFloat(4);
                crashes.add(new Crash(time, type, speed, acceleration));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return crashes;
    }
}
