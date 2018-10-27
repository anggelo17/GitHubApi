package com.example.luis.githubapi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LeftFragment extends Fragment {

    TextView mTextView;
    GitDiff mGitDiff;

    String LEFT="LEFT";

    private CompositeDisposable disposable=new CompositeDisposable();

    boolean left;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle= getArguments();
        if (bundle!=null)
            left = bundle.getBoolean(LEFT);

        Log.d("left","--"+left);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;
        if (left) {
            view = inflater.inflate(R.layout.fragment_left, container, false);
            mTextView = view.findViewById(R.id.text_diff);
        }
        else {
            view = inflater.inflate(R.layout.fragment_right, container, false);
            mTextView = view.findViewById(R.id.text_right);
        }


        mGitDiff = ApiUtils.getDiffService();
        loadData();
        return view;
    }

    private void loadData() {


        Log.d("left","--"+left);

        disposable.add(
        mGitDiff.getDiff("535")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String processString = processString(responseBody.string(),left);
                            mTextView.setText(Html.fromHtml(processString));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextView.setText(e.getMessage());
                        Log.e("tag",e.getMessage()+"-----why.....");

                    }

                    @Override
                    public void onComplete() {
                        Log.d("tag","complettted text");

                    }
                }));

    }

    private String processString(String diff,boolean left) {

        String result="";

        int n=0;

        String[] lines = diff.split("\n");
        for (int i=0;i<lines.length;i++) {
            Log.d("tag", "" + i + ").." + lines[i]);
           // String line = lines[i];
            if (lines[i].contains("@@")) {
                i++;
                n++;
            }

            if (n==2) {
                n=1;
            }

            if (n==1 && left){
                if (lines[i].contains("-")){
                    result += "<b>" + lines[i]+ "</b>"+"<br>";
                }
                else if (lines[i].contains("+")){
                    i++;
                }
                else {
                    result= result + lines[i]+"<br>";

                }
            }

            if (n==1 && !left){
                if (lines[i].contains("+")){
                    result += "<b>" + lines[i]+ "</b>"+"<br>";
                }
                else if (lines[i].contains("-")){
                    i++;
                }
                else {
                    result= result + lines[i]+"<br>";

                }
            }



        }

        Log.d("left-right",result);

        return result;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
