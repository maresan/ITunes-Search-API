package com.adevinta.yapo.presenter;

import android.view.View;

import com.adevinta.yapo.view.MainActivityView;

public interface MainActivityPresenter {
    void onViewAttached(MainActivityView view);
    void onObtenerDatos(String artista);
}
