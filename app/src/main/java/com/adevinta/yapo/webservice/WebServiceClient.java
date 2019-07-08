package com.adevinta.yapo.webservice;

import com.adevinta.yapo.model.Artista;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebServiceClient {

    @GET("search")
    Call<Artista> getArtista(@Query("term") String artistName);

}
