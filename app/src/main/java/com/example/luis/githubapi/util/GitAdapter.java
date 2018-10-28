package com.example.luis.githubapi.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.luis.githubapi.R;
import com.example.luis.githubapi.model.PullRequest;

import java.util.List;

public class GitAdapter extends RecyclerView.Adapter<GitAdapter.MyViewHolder> {

    private PRListener listener;
    private Context context;
    private List<PullRequest> PRListFiltered;


    public GitAdapter(Context context, List<PullRequest> PRList, PRListener listener) {
        this.context = context;
        this.listener = listener;
        this.PRListFiltered = PRList;

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

         PullRequest d= PRListFiltered.get(position);
        holder.title.setText(d.getTitle());
        String info= "#" + d.getNumber()+" opened by "+ d.getUser().getLogin();
        holder.number.setText(info);

    }

    @Override
    public int getItemCount() {
        return PRListFiltered.size();
    }

    public void updateData(List<PullRequest> pullRequests) {

        PRListFiltered= pullRequests;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView number;

        public MyViewHolder(View itemView){
            super(itemView);
            title =  itemView.findViewById(R.id.titlePR);
            number = itemView.findViewById(R.id.number);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onPRSelected(PRListFiltered.get(getAdapterPosition() ));

                }
            });
        }

    }


    public interface PRListener{

        void onPRSelected(PullRequest pullRequest);

    }







}
