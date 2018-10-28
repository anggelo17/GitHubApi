package com.example.luis.githubapi.api;

import io.reactivex.Observable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitDiff {

    @GET("googlesamples/android-architecture/pull/{id}.diff")
    Observable<ResponseBody> getDiff(@Path("id") String id);
}

//https://patch-diff.githubusercontent.com/raw/googlesamples/android-architecture/pull/605.diff
