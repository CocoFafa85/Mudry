package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Entites.Garagiste;

public class DataBaseManagerSQLite extends SQLiteOpenHelper {
    private static final String TAG="SQLite";
    private static final String TABLE="garagiste";
    private static final String COLUMN_SPE_Id="Id_PERSONNEL";
    private static final String GARAGISTE_TABLE_CREATE = "CREATE TABLE garagiste(Id_PERSONNEL INTEGER PRIMARY KEY)";
    private static final String DB_NAME = "garagiste.sqlite";
    private static final int DB_VERSION =1;
    private SQLiteDatabase db;

    public DataBaseManagerSQLite(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db=this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG,"DataBaseManagerSQLite.onCreate");
        db.execSQL(GARAGISTE_TABLE_CREATE);
    }

    public void inserer (Integer Id_PERSONNEL){
        ContentValues cv = new ContentValues();
        cv.put("Id_PERSONNEL", Id_PERSONNEL);
        db.insert(TABLE, null,cv);
    }

    public Garagiste getGaragiste(){
        Log.i(TAG,"MyDatabaseHelper.getAllContacts..." );
        List<Garagiste> contactList = new ArrayList<Garagiste>();

        String selectQuery = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Garagiste garagiste = new Garagiste(Integer.parseInt(cursor.getString(0)));
                return garagiste;
            }while (cursor.moveToNext());
        }
        return new Garagiste();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
