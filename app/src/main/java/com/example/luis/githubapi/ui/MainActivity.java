package com.example.luis.githubapi.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.luis.githubapi.R;
import com.example.luis.githubapi.model.PullRequest;

public class MainActivity extends BaseActivity implements FragmentOne.ICallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        showFirstFragment();



    }


    private void showFirstFragment() {

        FragmentManager fragmentManager=  getSupportFragmentManager();
        FragmentOne f1= (FragmentOne) fragmentManager.findFragmentById(R.id.main_frame);

        if (f1==null) {
             f1= new FragmentOne();
             fragmentTransaction(FragmentOne.class.getSimpleName(), f1, false, 1);
        }
    }



    @Override
    public void callBackMainAct(PullRequest pullRequest) {

        Log.d("tag","starrt intent....");

        Intent intent=new Intent(this,DiffActivity.class);
        intent.putExtra("pull_rq",pullRequest);
        startActivity(intent);

    }
}
