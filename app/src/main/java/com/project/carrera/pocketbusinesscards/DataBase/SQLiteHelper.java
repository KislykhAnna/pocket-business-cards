package com.project.carrera.pocketbusinesscards.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carrera on 03.12.2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper
{
    public SQLiteHelper(Context context)
    {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    // Созание таблицы
    public void onCreate(SQLiteDatabase db)
    {
        try
        {
            db.execSQL(Constants.CREATE_TB);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    // Обновление таблицы
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TB_NAME);
        onCreate(db);
    }
}
