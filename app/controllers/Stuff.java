package controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Criteria;
import models.Team;

/**
 * Created by Win 8 Pro on 25/3/2558.
 */
public class Stuff{
    private Criteria type;
    private List<Map.Entry<Team, Double>> rank;

    public Stuff(List<Map.Entry<Team, Double>> rank, Criteria type) {
        this.rank = rank;
        this.type = type;
    }

    public Criteria getType() {
        return type;
    }
    public List<Map.Entry<Team, Double>> getRank() {
        return rank;
    }

}
