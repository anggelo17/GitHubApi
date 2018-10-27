package com.example.luis.githubapi;

public class ApiUtils {

    private static final String BASE_URL = "https://api.github.com/repos/";
    private static final String DIFF_URL = "https://patch-diff.githubusercontent.com/raw/";

    public static GitApi getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(GitApi.class);
    }

    public static GitDiff getDiffService(){
        return RetrofitClient.getClient2(DIFF_URL).create(GitDiff.class);
    }
}