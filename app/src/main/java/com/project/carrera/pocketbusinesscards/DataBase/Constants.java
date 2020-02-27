package com.project.carrera.pocketbusinesscards.DataBase;

/**
 * Created by Carrera on 03.12.2017.
 */

public class Constants
{
    // Ячейки
    static final String ID = "id";
    static final String NAME = "name";
    static final String SURNAME = "surname";
    static final String COMPANY = "company";
    static final String POSITION = "position";
    static final String NUMBER = "number";
    static final String EMAIL = "email";
    static final String LOCATION = "location";

    // Свойства
    static final String DB_NAME = "PBC_DB";
    static final String TB_NAME = "PBC_TB";
    static final int DB_VERSION = '1';
    static final String CREATE_TB = "CREATE TABLE PBC_TB(id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL, surname TEXT NOT NULL, company TEXT NOT NULL, position TEXT NOT NULL, number TEXT NOT NULL, email TEXT NOT NULL, location TEXT NOT NULL);";
}
