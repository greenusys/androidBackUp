package com.example.rx_java_kaif.service;

import android.database.Observable;

import com.example.rx_java_kaif.model.Gist;

import retrofit2.http.GET;

/**
 * @author Praveen2Gemini on 24/06/17.
 */

public interface GistService {

    String BASE_URL = "https://api.github.com/";

    @GET("gists/59488f02db24ebd83450289e0b0f9ff7")
    Observable<Gist> fetchGistInformation();
}
