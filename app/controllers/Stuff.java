package controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Criteria;
import models.Team;

/**
 * Created by Win 8 Pro on 25/3/2558.
 */
public class Stuff<T,K,V>{
    private T type;
    private List<Map.Entry<K, V>> rank;

    public Stuff(List<Map.Entry<K, V>> rank, T type) {
        this.rank = rank;
        this.type = type;
    }

    public T getType() {
        return type;
    }
    public List<Map.Entry<K, V>> getRank() {
        return rank;
    }

}
