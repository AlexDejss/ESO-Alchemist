package com.gmail.aleksmemby.esoalchemist.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.gmail.aleksmemby.esoalchemist.R;
import com.gmail.aleksmemby.esoalchemist.database.Worker;
import com.gmail.aleksmemby.esoalchemist.functions.Filter;
import com.gmail.aleksmemby.esoalchemist.models.Reagent;
import com.gmail.aleksmemby.esoalchemist.service.FileReader;
import com.gmail.aleksmemby.esoalchemist.service.ReagentsAdapter;
import com.gmail.aleksmemby.esoalchemist.utils.Constants;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Constants{

    private FileReader fileReader;
    private ReagentsAdapter adapter;
    private RecyclerView recyclerView;
    private List<Reagent> defaultList;
    private Worker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FileReader(this).readFile();

        recyclerView = findViewById(R.id.reagentsList);
        worker = new Worker(this);

        defaultList = worker.getReagentList();



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ReagentsAdapter(this, (Reagent r) -> filterReagent(r));

        adapter.setCollection(defaultList);

        recyclerView.setAdapter(adapter);

    }



    private List<Reagent> filterReagent(Reagent reagent){
        Log.v("FILTER: ", reagent.toString());
        adapter.setCollection(worker.getReagentList(reagent));
        return null;
    }


    public void resetFilter(View view) {
        adapter.setCollection(defaultList);
    }
}
