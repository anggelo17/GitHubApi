package com.example.luis.githubapi.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luis.githubapi.R;
import com.example.luis.githubapi.api.GitApi;
import com.example.luis.githubapi.model.PullRequest;
import com.example.luis.githubapi.util.ApiUtils;
import com.example.luis.githubapi.util.GitAdapter;
import com.example.luis.githubapi.util.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;



public class FragmentOne extends Fragment implements GitAdapter.PRListener{

    private RecyclerView mRecyclerView;
    private GitApi mgitApi;
    private GitAdapter mgitAdapter;

    private CompositeDisposable disposable=new CompositeDisposable();

    private ICallBack iCallBack;

    private SwipeRefreshLayout mSwipeRefreshLayout;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_one,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        mgitApi = ApiUtils.getSOService();

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mgitAdapter = new GitAdapter(getContext(),new ArrayList<PullRequest>(0),this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mgitAdapter);
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL, 36));


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                     @Override
                                                     public void onRefresh() {
                                                         loadData();
                                                         mSwipeRefreshLayout.setRefreshing(false);
                                                     }
                                                 }
        );

        loadData();

    }

    private void loadData() {


        disposable.add(
                mgitApi.getPullRequest().cache()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<PullRequest>>() {


                    @Override
                    public void onNext(List<PullRequest> pullRequests) {

                        mgitAdapter.updateData(pullRequests);

                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("tag",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        Log.d("tag","rv completed");

                    }
                }));


    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        try {
            iCallBack = (ICallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Listener");
        }
    }

    @Override
    public void onPRSelected(PullRequest pullRequest) {

        iCallBack.callBackMainAct(pullRequest);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    interface ICallBack{
        void callBackMainAct(PullRequest pullRequest);
    }

}
