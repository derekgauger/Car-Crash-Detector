package com.example.crashdetector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactsDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "contacts_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "my_contacts";
    private static final String ID_COL = "id";
    private static final String FIRST_NAME_COL = "first_name";
    private static final String LAST_NAME_COL = "last_name";
    private static final String PHONE_NUMBER_COL = "phone_number";
    private static final String RELATION_COL = "relation";

    public ContactsDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createCmd = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FIRST_NAME_COL + " TEXT, "
                + LAST_NAME_COL + " TEXT, "
                + PHONE_NUMBER_COL + " TEXT, "
                + RELATION_COL + " TEXT)";
        sqLiteDatabase.execSQL(createCmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addNewContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues currentEntry = new ContentValues();
        currentEntry.put(FIRST_NAME_COL, contact.firstName);
        currentEntry.put(LAST_NAME_COL, contact.lastName);
        currentEntry.put(PHONE_NUMBER_COL, contact.phoneNumber);
        currentEntry.put(RELATION_COL, contact.relation);
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

    public List<Contact> getContacts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<Contact> contacts = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String firstName = cursor.getString(1);
                String lastName = cursor.getString(2);
                String phoneNumber = cursor.getString(3);
                String relation = cursor.getString(4);
                contacts.add(new Contact(firstName, lastName, phoneNumber, relation));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return contacts;
    }
}
