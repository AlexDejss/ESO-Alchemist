package com.gmail.aleksmemby.esoalchemist.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.aleksmemby.esoalchemist.R;
import com.gmail.aleksmemby.esoalchemist.functions.Filter;
import com.gmail.aleksmemby.esoalchemist.models.Reagent;

import java.util.ArrayList;
import java.util.List;

public class ReagentsAdapter extends RecyclerView.Adapter<ReagentsAdapter.ViewHolder> {

    private List<Reagent> reagentList = new ArrayList<>();
    private Context context;
    private Filter reagentFilter;

    public ReagentsAdapter(Context context, Filter reagentFilter) {
        this.context = context;
        this.reagentFilter = reagentFilter;
    }

    public void setCollection(List<Reagent> reagentList){
        this.reagentList = reagentList;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, effect_1, effect_2, effect_3, effect_4;
        private LinearLayout reagentField;
        private Reagent reagent;

        ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.reagentView);
            name.setOnClickListener(view1 -> reagentFilter.filterByReaget(reagent));
            effect_1 = view.findViewById(R.id.effect_1);
            effect_2 = view.findViewById(R.id.effect_2);
            effect_3 = view.findViewById(R.id.effect_3);
            effect_4 = view.findViewById(R.id.effect_4);
            reagentField = view.findViewById(R.id.reagentField);

        }

        void UpdateUI(Reagent reagent){
            this.reagent = reagent;

            name.setText(reagent.getName());
            effect_1.setText(reagent.getEffects().get(0).getName());
            effect_1.setTextColor(context.getResources().getColor(reagent.getEffects().get(0).getType().equals("buff") ? R.color.buff : R.color.debuff, null));
            effect_2.setText(reagent.getEffects().get(1).getName());
            effect_2.setTextColor(context.getResources().getColor(reagent.getEffects().get(1).getType().equals("buff") ? R.color.buff : R.color.debuff, null));
            effect_3.setText(reagent.getEffects().get(2).getName());
            effect_3.setTextColor(context.getResources().getColor(reagent.getEffects().get(2).getType().equals("buff") ? R.color.buff : R.color.debuff, null));
            effect_4.setText(reagent.getEffects().get(3).getName());
            effect_4.setTextColor(context.getResources().getColor(reagent.getEffects().get(3).getType().equals("buff") ? R.color.buff : R.color.debuff, null));

        }
    }

    @Override
    public ReagentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reagent_view, parent,false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.UpdateUI(reagentList.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return reagentList.size();
    }
}