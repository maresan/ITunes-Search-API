package com.adevinta.yapo.presenter;

import com.adevinta.yapo.model.Artista;
import com.adevinta.yapo.model.Cancion;
import com.adevinta.yapo.view.MainActivityView;
import com.adevinta.yapo.webservice.WebServiceClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityImpl implements MainActivityPresenter {
    private MainActivityView mainActivityView;
    private ArrayList<Cancion> canciones = new ArrayList<>();

    @Override
    public void onObtenerDatos(String artista) {
        if (mainActivityView != null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://itunes.apple.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WebServiceClient webServiceClient = retrofit.create(WebServiceClient.class);
            Call<Artista> call = webServiceClient.getArtista(artista);
            call.enqueue(new Callback<Artista>() {
                @Override
                public void onResponse(Call<Artista> call, Response<Artista> response) {
                    canciones.clear();
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        if (response.body().getResults().get(i).getWrapperType().equals("track")) {
                            if (response.body().getResults().get(i).getKind().equals("song")) {
                                Cancion cancion = new Cancion();
                                cancion.setArtworkUrl30(response.body().getResults().get(i).getArtworkUrl60());
                                cancion.setTrackName(response.body().getResults().get(i).getTrackName());
                                cancion.setCollectionName(response.body().getResults().get(i).getCollectionName());
                                cancion.setArtistName(response.body().getResults().get(i).getArtistName());
                                cancion.setPreviewUrl(response.body().getResults().get(i).getPreviewUrl());
                                cancion.setKind(response.body().getResults().get(i).getKind());
                                canciones.add(cancion);
                            }
                        }
                    }
                    mainActivityView.onCanciones(canciones);
                }

                @Override
                public void onFailure(Call<Artista> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onViewAttached(MainActivityView view) {
        mainActivityView = view;
    }
}
