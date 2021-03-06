package com.example.luis.githubapi.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.luis.githubapi.R;

public class BaseActivity extends AppCompatActivity {

    public String mTagName = "";


    public void fragmentTransaction(String tagName, Fragment fragment, boolean addToBackStack, int style) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            mTagName = tagName;
            switch (style) {
                case 1:
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    transaction.replace(R.id.main_frame, fragment, tagName);
                    break;

                case 2:
                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    transaction.replace(R.id.main_frame, fragment, tagName);
                    break;
                case 3:
                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    transaction.replace(R.id.left_layout, fragment, tagName);
                    break;
                case 4:
                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    transaction.replace(R.id.right_layout, fragment, tagName);
                    break;
                default:
                    transaction.replace(R.id.main_frame, fragment, tagName);
                    break;
            }
            if (addToBackStack) {
                transaction.addToBackStack(tagName);
            }
            transaction.commit();
        }
    }



}