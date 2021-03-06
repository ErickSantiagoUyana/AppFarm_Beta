package com.example.kappgranja.ui.management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }


    public void insertData(String idnumber, String name,String age, String state,String health,
                           String sex, String race, byte[] image,String tab){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO "+tab+" VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, idnumber);
        statement.bindString(2, name);
        statement.bindString(3, age);
        statement.bindString(4, state);
        statement.bindString(5, health);
        statement.bindString(6, sex);
        statement.bindString(7, race);
        statement.bindBlob(8, image);

        statement.executeInsert();
    }

    public void updateData(String idnumber, String name, String age, String state,String health,
                           String sex, String race, byte[] image, String id,String tab) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("idnumber",idnumber);
        contentValues.put("name",name);
        contentValues.put("age",age);
        contentValues.put("state",state);
        contentValues.put("health",health);
        contentValues.put("sex",sex);
        contentValues.put("race",race);
        contentValues.put("image",image);
        database.update(tab,contentValues, "id = ?", new String[]{id});

    }

    public  void deleteData(int id,String tab) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM "+tab+" WHERE id = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }


    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }



    }
