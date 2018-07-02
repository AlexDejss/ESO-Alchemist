package com.gmail.aleksmemby.esoalchemist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gmail.aleksmemby.esoalchemist.models.Effect;
import com.gmail.aleksmemby.esoalchemist.models.Reagent;
import com.gmail.aleksmemby.esoalchemist.utils.DatabaseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksey Kapura
 *         01.07.2018.
 */

public class Worker implements DatabaseConstants{

    private Database database;

    public Worker(Context context) {
        this.database = new Database(context);
    }

    public void saveContent(List<Reagent> reagents, List<Effect> effects){
        final SQLiteDatabase sql = database.getWritableDatabase();

        Log.v("START EFFECTS_TABLE", DatabaseUtils.queryNumEntries(sql, EFFECTS_TABLE) + "");
        Log.v("START REAGENTS_TABLE", DatabaseUtils.queryNumEntries(sql, REAGENTS_TABLE) + "");
        Log.v("START REAGENT_EFFECTS_TABLE", DatabaseUtils.queryNumEntries(sql, REAGENT_EFFECTS_TABLE) + "");

        if(DatabaseUtils.queryNumEntries(sql, EFFECTS_TABLE) == 0)
            effects.forEach(effect -> {
                ContentValues values = new ContentValues();
                values.put(ID, effect.getId());
                values.put(NAME, effect.getName());
                values.put(TYPE, effect.getType());
                sql.insert(EFFECTS_TABLE, null, values);
            });

        Boolean isReagentsEmpty = DatabaseUtils.queryNumEntries(sql, REAGENTS_TABLE) == 0;
        Boolean isReagentEffectsEmpty = DatabaseUtils.queryNumEntries(sql, REAGENT_EFFECTS_TABLE) == 0;


            reagents.forEach(reagent -> {
                if(isReagentsEmpty){
                    ContentValues values = new ContentValues();
                    values.put(ID, reagent.getId());
                    values.put(NAME, reagent.getName());
                    values.put(GATHER, reagent.getGather());
                    sql.insert(REAGENTS_TABLE, null, values);
                }


                if(isReagentEffectsEmpty)
                    reagent.getEffects().forEach(effect -> {
                        ContentValues valuesSecond = new ContentValues();
                        valuesSecond.put(EFFECT_ID, effect.getId());
                        valuesSecond.put(REAGENT_ID, reagent.getId());
                        sql.insert(REAGENT_EFFECTS_TABLE, null, valuesSecond);
                    });
            });

        Log.v("END EFFECTS_TABLE", DatabaseUtils.queryNumEntries(sql, EFFECTS_TABLE) + "");
        Log.v("END REAGENTS_TABLE", DatabaseUtils.queryNumEntries(sql, REAGENTS_TABLE) + "");
        Log.v("END REAGENT_EFFECTS_TABLE", DatabaseUtils.queryNumEntries(sql, REAGENT_EFFECTS_TABLE) + "");

        sql.close();
        database.close();

    }

    public List<Reagent> getReagentList(){

        List<Reagent> reagents = new ArrayList<>();

        SQLiteDatabase sql = database.getReadableDatabase();
        Log.v("EFFECTS_TABLE", DatabaseUtils.queryNumEntries(sql, EFFECTS_TABLE) + "");
        Log.v("REAGENTS_TABLE", DatabaseUtils.queryNumEntries(sql, REAGENTS_TABLE) + "");
        Log.v("REAGENT_EFFECTS_TABLE", DatabaseUtils.queryNumEntries(sql, REAGENT_EFFECTS_TABLE) + "");

        Cursor cursor = sql.rawQuery("select * from " + REAGENTS_TABLE, null);

        Log.v("Reagents founded", cursor.getCount() + " items");

        if(cursor.moveToFirst()){
            do {
                Integer id = cursor.getInt(cursor.getColumnIndex(ID));
                reagents.add(
                        Reagent.builder()
                                .id(id)
                                .name(cursor.getString(cursor.getColumnIndex(NAME)))
                                .gather(cursor.getString(cursor.getColumnIndex(GATHER)))
                                .effects(getEffectsOfReagent(id))
                                .build()
                );
                Log.v("Effect name", cursor.getString(cursor.getColumnIndex(NAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        sql.close();
        database.close();

        return reagents;
    }

    public List<Reagent> getReagentList(Reagent reagent){

        List<Reagent> reagents = new ArrayList<>();

        SQLiteDatabase sql = database.getReadableDatabase();

        List<Effect> effects = reagent.getEffects();

        String query = String.format(REAGENT_FILTER,
                effects.get(0).getId().toString(),
                effects.get(1).getId().toString(),
                effects.get(2).getId().toString(),
                effects.get(3).getId().toString()
        );


        Cursor cursor = sql.rawQuery(query, null);

        Log.v("Reagents founded", cursor.getCount() + " items");

        if(cursor.moveToFirst()){
            do {
                reagents.add(
                        Reagent.builder()
                                .id(cursor.getInt(cursor.getColumnIndex(ID)))
                                .name(cursor.getString(cursor.getColumnIndex(NAME)))
                                .gather(cursor.getString(cursor.getColumnIndex(GATHER)))
                                .effects(getEffectsOfReagent(cursor.getInt(cursor.getColumnIndex(ID))))
                                .build()
                );
                Log.v("Effect name", cursor.getString(cursor.getColumnIndex(NAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        sql.close();
        database.close();

        return reagents;
    }


    private List<Effect> getEffectsOfReagent(Integer reagentId){

        SQLiteDatabase sql = database.getReadableDatabase();

        List<Effect> effects = new ArrayList<>(4);

        String query =
                "select * from effects where id in (select effect_id from reagent_effects where reagent_id = " + reagentId + ")";


        Cursor cursor = sql.rawQuery(query, null);
        Log.v("Founded: ", cursor.getCount() + "");

        if(cursor.moveToFirst()){
            do {
                effects.add(Effect.builder()
                        .id(cursor.getInt(cursor.getColumnIndex(ID)))
                        .name(cursor.getString(cursor.getColumnIndex(NAME)))
                        .type(cursor.getString(cursor.getColumnIndex(TYPE)))
                        .build()
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        sql.close();
        Log.v("getEffectsOfReagent: ", effects.toString());
        return effects;

    }

}
