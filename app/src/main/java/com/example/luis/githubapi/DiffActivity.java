package com.example.luis.githubapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class DiffActivity extends BaseActivity {

    PullRequest mPullRequest;
    String LEFT="LEFT";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.split_activity);

         mPullRequest=(PullRequest)getIntent().getSerializableExtra("pull_rq");

        FragmentManager fragmentManager=  getSupportFragmentManager();
        LeftFragment f1= (LeftFragment) fragmentManager.findFragmentById(R.id.left_layout);
        LeftFragment f2= (LeftFragment) fragmentManager.findFragmentById(R.id.right_layout);



        if(f1==null) {
            f1 = new LeftFragment();
            Bundle b=new Bundle();
            b.putBoolean(LEFT,true);
            f1.setArguments(b);
            Log.d("left","--"+true);
            fragmentTransaction("LeftFragment", f1, false, 3);
        }
        if(f2==null) {
            f2 = new LeftFragment();
            Bundle b=new Bundle();
            b.putBoolean(LEFT,false);
            f2.setArguments(b);
            Log.d("left","--"+false);
            fragmentTransaction("RightFragment", f2, false, 4);
        }


    }
}
