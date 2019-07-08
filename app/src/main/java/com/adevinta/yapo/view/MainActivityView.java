package com.adevinta.yapo.view;

import com.adevinta.yapo.model.Cancion;

import java.util.ArrayList;

public interface MainActivityView {
    void onCanciones(ArrayList<Cancion> canciones);
}
