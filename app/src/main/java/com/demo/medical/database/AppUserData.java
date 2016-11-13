package com.demo.medical.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.demo.medical.model.AppUser;

import java.util.ArrayList;

/**
 * Created by Moosa moosa.bh@gmail.com on 11/12/2016 12 November.
 * Everything is possible in programming.
 */

public class AppUserData extends SQLiteOpenHelper {
    /*
            {
                "_id":"5826469fc5e53f3b975e09bd",
                -//"uid":"101",
                "name":"Sushan Kumar",
                "email":"shahid@gmail.com",
                -//"password":"12345",
                -//"is_admin":false,
                -//"__v":0,
                "patients":[],
                "specialization":[]
            }
          */
    private String TABLE_USER_DATA = "user_data";
    private String ID = "COL_ID";
    private String COL_NAME = "COL_NAME";
    private String COL_USER_ID = "COL_USER_ID";
    private String COL_EMAIL = "COL_EMAIL";

    private String COL_P_ID = "COL_P_ID";
    private String COL_P_PID = "COL_P_PID";
    private String PATIENT_TABLE = "COL_PATIENT_TABLE";

    private String COL_S_ID = "COL_S_ID";
    private String COL_S_SPECS = "COL_S_SPECS";
    private String SPECS_TABLE = "COL_SPECS_TABLE";


    public AppUserData(Context context) {
        super(context, "user.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql =
                "create table " + TABLE_USER_DATA + " ("
                        + ID + " int primary key,"
                        + COL_USER_ID + " text ,"
                        + COL_NAME + " text ,"
                        + COL_EMAIL + " text "
                        + ")";
        String sql2 =
                "create table " + SPECS_TABLE + " ("
                        + COL_S_ID + " int primary key,"
                        + COL_S_SPECS + " text "
                        + ")";
        String sql3 =
                "create table " + PATIENT_TABLE + " ("
                        + COL_P_ID + " int primary key,"
                        + COL_P_PID + " text "
                        + ")";


        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void saveUserData(AppUser log) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_ID, log.getUserID());
        cv.put(COL_EMAIL, log.getEmail());
        cv.put(COL_NAME, log.getUserName());
        db.insert(TABLE_USER_DATA, null, cv);

        for (String val : log.getPatientIds()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_P_PID, val);
            db.insert(PATIENT_TABLE, null, contentValues);
        }
        for (String val : log.getSpecialization()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_S_SPECS, val);
            db.insert(SPECS_TABLE, null, contentValues);
        }
        db.close();
    }

    public AppUser getUserData() {
        AppUser appUser = new AppUser();
        ArrayList<String> patients = new ArrayList<>();
        ArrayList<String> specs = new ArrayList<>();
        SQLiteDatabase read = this.getReadableDatabase();
        Cursor cursor = read.rawQuery("SELECT * FROM " + TABLE_USER_DATA, null);
        while (cursor.moveToNext()) {
            appUser.setUserID(cursor.getString(1));
            appUser.setUserName(cursor.getString(2));
            appUser.setEmail(cursor.getString(3));
        }
        Cursor cursor2 = read.rawQuery("SELECT * FROM " + PATIENT_TABLE, null);
        while (cursor2.moveToNext()) {
            patients.add(cursor2.getString(1));
        }

        Cursor cursor3 = read.rawQuery("SELECT * FROM " + SPECS_TABLE, null);
        while (cursor3.moveToNext()) {
            specs.add(cursor3.getString(1));
        }
        appUser.setSpecialization(specs);
        appUser.setPatientIds(patients);
        read.close();
        return appUser;
    }

}
