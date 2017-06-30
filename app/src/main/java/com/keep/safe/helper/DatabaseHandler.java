package com.keep.safe.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.keep.safe.model.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garima on 4/13/2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "masterPassword";

    // Contacts table name
    private static final String DATA_TABLE = "details";

    // Contacts Table Columns names
    private static final String ID = "id";
    private static final String PASSWORD = "password";
    private static final String EMAILID = "emailid";
    private static final String WEBSITE = "website";
    private static final String TAG = "tag";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_SAVE_TABLE = "CREATE TABLE " + DATA_TABLE + "("
                + ID + " INTEGER PRIMARY KEY, "
                + TAG + " TEXT, "
                + WEBSITE + " TEXT, "
                + EMAILID + " TEXT, "
                + PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_SAVE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE);
        // Create tables again
        onCreate(db);

    }


    public void insertDetails(Data d) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TAG, d.getTag());
        values.put(WEBSITE, d.getWebsite());
        values.put(EMAILID, d.getEmailid());
        values.put(PASSWORD, d.getPassword());

        db.insert(DATA_TABLE, null, values);
        db.close();
    }

    public List<Data> getAllDetails() {
        List<Data> detail = new ArrayList<Data>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DATA_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Data data = new Data();
                data.setId(cursor.getString(cursor.getColumnIndex(ID)));
                data.setTag(cursor.getString(cursor.getColumnIndex(TAG)));
                data.setWebsite(cursor.getString(cursor.getColumnIndex(WEBSITE)));
                data.setEmailid(cursor.getString(cursor.getColumnIndex(EMAILID)));
                data.setPassword(cursor.getString(cursor.getColumnIndex(PASSWORD)));
                // Adding contact to custom_list_row
                detail.add(data);
            } while (cursor.moveToNext());
        }

        // return contact custom_list_row
        return detail;
    }


    public boolean updateDetail(Data d) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TAG, d.getTag());
        contentValues.put(WEBSITE, d.getWebsite());
        contentValues.put(EMAILID, d.getEmailid());
        contentValues.put(PASSWORD, d.getPassword());

        db.update(DATA_TABLE, contentValues, "id = ? ", new String[]{d.getId()});
        return true;

    }

    public Integer deleteContact(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DATA_TABLE, "id = ? ", new String[]{id});

    }


}
