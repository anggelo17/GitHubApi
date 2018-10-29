package com.example.luis.githubapi.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.luis.githubapi.R;
import com.example.luis.githubapi.model.PullRequest;

public class DiffActivity extends BaseActivity {

    PullRequest mPullRequest;
    private static  final String LEFT="LEFT";
    private static  final String PR="PR";

    TextView mTextViewTitle;
    TextView mTextViewNumberAuthor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.split_activity);

        mTextViewTitle = findViewById(R.id.titleAct);
        mTextViewNumberAuthor = findViewById(R.id.number_author);



         mPullRequest=(PullRequest)getIntent().getSerializableExtra("pull_rq");

         if (mPullRequest!=null){
             mTextViewTitle.setText(mPullRequest.getTitle());
             String info= "#" + mPullRequest.getNumber()+" opened by "+ mPullRequest.getUser().getLogin();
             mTextViewNumberAuthor.setText(info);
         }


        FragmentManager fragmentManager=  getSupportFragmentManager();
        LeftRightFragment f1= (LeftRightFragment) fragmentManager.findFragmentById(R.id.left_layout);
        LeftRightFragment f2= (LeftRightFragment) fragmentManager.findFragmentById(R.id.right_layout);



        if(f1==null) {
            f1 = new LeftRightFragment();
            Bundle b=new Bundle();
            b.putBoolean(LEFT,true);
            b.putSerializable(PR,mPullRequest);
            f1.setArguments(b);
            Log.d("left","--"+true);
            fragmentTransaction("LeftFragment", f1, false, 3);
        }
        if(f2==null) {
            f2 = new LeftRightFragment();
            Bundle b=new Bundle();
            b.putBoolean(LEFT,false);
            b.putSerializable(PR,mPullRequest);
            f2.setArguments(b);
            Log.d("left","--"+false);
            fragmentTransaction("RightFragment", f2, false, 4);
        }


    }
}
