package com.gmail.aleksmemby.esoalchemist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gmail.aleksmemby.esoalchemist.utils.DatabaseConstants;

/**
 * @author Aleksey Kapura
 *         01.07.2018.
 */

public class Database extends SQLiteOpenHelper implements DatabaseConstants {

    public Database(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + CREATE_EFFECTS_TABLE + "'");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + CREATE_REAGENTS_TABLE + "'");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + CREATE_REAGENT_EFFECTS_TABLE + "'");
        sqLiteDatabase.execSQL(CREATE_EFFECTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_REAGENTS_TABLE);
        sqLiteDatabase.execSQL(CREATE_REAGENT_EFFECTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
