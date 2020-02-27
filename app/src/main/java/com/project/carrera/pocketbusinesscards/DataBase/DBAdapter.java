package com.project.carrera.pocketbusinesscards.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Carrera on 03.12.2017.
 */

public class DBAdapter
{
    Context context;
    SQLiteDatabase database;
    SQLiteHelper helper;

    public DBAdapter(Context context)
    {
        this.context = context;
        helper = new SQLiteHelper(context);
    }

    // Открываем базу данных
    public DBAdapter openDB()
    {
        try
        {
            database = helper.getWritableDatabase();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return this;
    }

    // Закрытие базы данных
    public void closeDB()
    {
        try
        {
            helper.close();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    // Ввод данных
    public long add(String name, String surname, String company, String position, String number, String email, String location)
    {
        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.NAME, name);
            contentValues.put(Constants.SURNAME, surname);
            contentValues.put(Constants.COMPANY, company);
            contentValues.put(Constants.POSITION, position);
            contentValues.put(Constants.NUMBER, number);
            contentValues.put(Constants.EMAIL, email);
            contentValues.put(Constants.LOCATION, location);
            return database.insert(Constants.TB_NAME, Constants.ID, contentValues);
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }

    // Перезагрузка
    public Cursor getAllCards()
    {
        String[] columns = {Constants.ID, Constants.NAME, Constants.SURNAME, Constants.COMPANY, Constants.POSITION, Constants.NUMBER, Constants.EMAIL, Constants.LOCATION};

        return database.query(Constants.TB_NAME, columns, null, null, null, null, null);

    }

    // Обновление
    public long Update(int id, String name, String surname, String company, String position, String number, String email, String location)
    {
        try
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.NAME, name);
            contentValues.put(Constants.SURNAME, surname);
            contentValues.put(Constants.COMPANY, company);
            contentValues.put(Constants.POSITION, position);
            contentValues.put(Constants.NUMBER, number);
            contentValues.put(Constants.EMAIL, email);
            contentValues.put(Constants.LOCATION, location);
            return database.update(Constants.TB_NAME, contentValues, Constants.ID + " =?", new String[]{String.valueOf(id)});
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }

    // Удаление
    public long Delete(int id)
    {
        try
        {
            return database.delete(Constants.TB_NAME,Constants.ID + " =?", new String[]{String.valueOf(id)});
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }
}
