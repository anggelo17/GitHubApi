package com.example.luis.githubapi.ui;

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

import com.example.luis.githubapi.R;
import com.example.luis.githubapi.api.GitDiff;
import com.example.luis.githubapi.model.PullRequest;
import com.example.luis.githubapi.util.ApiUtils;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LeftRightFragment extends Fragment {

    TextView mTextView;
    GitDiff mGitDiff;

    String LEFT="LEFT";
    String PR="PR";

    private CompositeDisposable disposable=new CompositeDisposable();

    boolean left;
    PullRequest mPullRequest;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle= getArguments();
        if (bundle!=null) {
            left = bundle.getBoolean(LEFT);
            mPullRequest= (PullRequest) bundle.getSerializable(PR);
        }

        Log.d("left","--"+left);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_left_right, container, false);
        mTextView = view.findViewById(R.id.text_diff);

        mGitDiff = ApiUtils.getDiffService();
        loadData();
        return view;
    }

    private void loadData() {



        disposable.add(
        mGitDiff.getDiff(mPullRequest.getNumber())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, Observable<String>>() {
                              @Override
                              public Observable<String> apply(ResponseBody responseBody) throws Exception {

                                  String processString="";

                                  try {
                                      String resp=responseBody.string();
                                      if (!resp.equals("")) {
                                          processString = processString(resp, left);

                                      }


                                  } catch (IOException e) {
                                      e.printStackTrace();
                                  }


                                  Log.d("tag","almost......");
                                  return Observable.just(processString);
                              }
                          }
        )
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String processString) {

                        Log.d("tag","almost......"+processString.length());

                        char[] chars= processString.toCharArray();
                        Log.d("tag","length"+chars.length);

                        if (!processString.equals(""))
                            if (chars.length>=3000)
                                mTextView.setText(Html.fromHtml(processString.substring(0,3000)));
                            else
                                mTextView.setText(Html.fromHtml(processString));

                        else
                            mTextView.setText("Nothing to show.");


                    }

                    @Override
                    public void onError(Throwable e) {
                        mTextView.setText("Sorry, this diff is unavailable. accoding to GitHub Api");
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
           // Log.d("tag", "" + i + ").." + lines[i]);
           // String line = lines[i];
            if (lines[i].contains("@@")) {
                i++;
                n++;
            }

            if (n==2) {
                n=1;
            }

            if (n==1 && left){
                if (containsThis('-',lines[i])){
                    result += "<p style='background-color:#ff6666;'><b>" + lines[i]+ "</b></p>"+"<br>";
                }

                else {
                    if (!containsThis('+',lines[i]))
                        result= result + lines[i]+"<br>";

                }
            }

            if (n==1 && !left){
                if (containsThis('+',lines[i])){
                    result += "<p style='background-color:#7fff7f;'><b>" + lines[i]+ "</b></p>"+"<br>";
                }

                else {
                    if (!containsThis('-',lines[i]))
                        result= result + lines[i]+"<br>";

                }
            }



        }

        //Log.d("left-right",result);

        return result;

    }

    private boolean containsThis(char s, String line) {

       if (line.charAt(0)==s ){
           if (line.length()>1)
               if(line.charAt(1)!=s)
                   return true;

       }

       return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
