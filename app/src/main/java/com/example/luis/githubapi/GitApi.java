package com.example.luis.githubapi;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


interface GitApi {

    @GET("googlesamples/android-architecture/pulls?state=open")
    Observable<List<PullRequest>> getPullRequest();


}
