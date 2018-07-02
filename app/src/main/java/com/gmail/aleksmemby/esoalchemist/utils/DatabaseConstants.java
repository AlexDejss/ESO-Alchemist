package com.gmail.aleksmemby.esoalchemist.utils;

/**
 * @author Aleksey Kapura
 *         01.07.2018.
 */

public interface DatabaseConstants extends Constants{

    Integer VERSION = 1;

    String REAGENT_ID = "reagent_id";
    String EFFECT_ID = "effect_id";

    String DB_NAME = "eso_alchemist_dejss";

    String EFFECTS_TABLE = "effects";
    String REAGENTS_TABLE = "reagents";
    String REAGENT_EFFECTS_TABLE = "reagent_effects";

    String CREATE_EFFECTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + EFFECTS_TABLE + " (" + ID + " INTEGER PRIMARY KEY, " +
                    NAME + " TEXT, " + TYPE + " TEXT);";

    String CREATE_REAGENTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + REAGENTS_TABLE + " (" + ID + " INTEGER PRIMARY KEY, " +
                    NAME + " TEXT, " + GATHER + " TEXT);";

    String CREATE_REAGENT_EFFECTS_TABLE =
            "CREATE TABLE IF NOT EXISTS " + REAGENT_EFFECTS_TABLE + " (" + ID + " INTEGER PRIMARY KEY, " +
                    EFFECT_ID + " INTEGER, " + REAGENT_ID + " INTEGER);";

    String REAGENT_FILTER =
            "SELECT * FROM " + REAGENTS_TABLE + " WHERE " + ID +
                    " in (select " + REAGENT_ID + " from " + REAGENT_EFFECTS_TABLE + " where " +
                    EFFECT_ID + " = %s OR " +
                    EFFECT_ID + " = %s OR " +
                    EFFECT_ID + " = %s OR " +
                    EFFECT_ID + " = %s)";


}
