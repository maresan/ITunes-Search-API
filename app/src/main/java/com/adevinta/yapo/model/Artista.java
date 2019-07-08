package com.adevinta.yapo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Artista implements Serializable {
    private Integer resultCount;
    private ArrayList<Cancion> results;

    public Artista() {
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public ArrayList<Cancion> getResults() {
        return results;
    }

    public void setResults(ArrayList<Cancion> results) {
        this.results = results;
    }
}
