package com.gmail.aleksmemby.esoalchemist.service;

import android.content.Context;
import android.util.Log;

import com.gmail.aleksmemby.esoalchemist.database.Worker;
import com.gmail.aleksmemby.esoalchemist.models.Effect;
import com.gmail.aleksmemby.esoalchemist.models.Reagent;
import com.gmail.aleksmemby.esoalchemist.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Aleksey Kapura
 *         01.07.2018.
 */

public class FileReader implements Constants{

    private Context context;

    public FileReader(Context context) {
        this.context = context;
    }

    public void readFile(){

        String json = null;

        try {
            InputStream is = context.getAssets().open(CONTENT);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            JSONObject file = new JSONObject(new String(buffer, UTF_8));

            new Worker(context).saveContent(readReagents(file), readEffects(file));

        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            Log.e("Error", ex.toString());
        }
    }

    private List<Effect> readEffects(JSONObject file) throws JSONException {

        JSONArray effects = file.getJSONArray(EFFECTS);

        List<Effect> effectList = new ArrayList<>();
        for (int i = 0; i < effects.length(); i++) {
            effectList.add(Effect.builder()
                    .id(effects.getJSONObject(i).getInt(ID))
                    .name(effects.getJSONObject(i).getString(NAME))
                    .type(effects.getJSONObject(i).getString(TYPE))
                    .build()
            );
            Log.i("Effect: ", effectList.get(i).toString());
        }

        return effectList;
    }

    private List<Reagent> readReagents(JSONObject file) throws JSONException {

        JSONArray reagents = file.getJSONArray(REAGENTS);

        List<Reagent> reagentReads = new ArrayList<>(reagents.length());
        for (int i = 0; i < reagents.length(); i++) {

            JSONArray effectsId = reagents.getJSONObject(i).getJSONArray(EFFECTS);

            reagentReads.add(Reagent.builder()
                    .id(reagents.getJSONObject(i).getInt(ID))
                    .name(reagents.getJSONObject(i).getString(NAME))
                    .gather(reagents.getJSONObject(i).getString(GATHER))
                    .effects(
                            Stream.of(
                                    effectsId.getJSONObject(EFF_1).getInt(ID),
                                    effectsId.getJSONObject(EFF_2).getInt(ID),
                                    effectsId.getJSONObject(EFF_3).getInt(ID),
                                    effectsId.getJSONObject(EFF_4).getInt(ID)
                            )
                                    .map(id->new Effect(id, null,null))
                                    .collect(Collectors.toList())
                    )
                    .build()
            );
            Log.i("Reagent: ", reagentReads.get(i).toString());
        }

        return reagentReads;
    }





}
