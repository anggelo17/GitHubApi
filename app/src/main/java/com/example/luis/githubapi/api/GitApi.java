package com.example.luis.githubapi.api;

import com.example.luis.githubapi.model.PullRequest;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface GitApi {

    @GET("googlesamples/android-architecture/pulls?state=open")
    Observable<List<PullRequest>> getPullRequest();


}
