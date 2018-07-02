package com.gmail.aleksmemby.esoalchemist.functions;

import com.gmail.aleksmemby.esoalchemist.models.Reagent;

import java.util.List;

public interface Filter{
    List<Reagent> filterByReaget(Reagent reagent);
}