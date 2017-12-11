package ru.mydrevolife.materialviewpager.adapter;


import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;

import ru.mydrevolife.materialviewpager.R;
import ru.mydrevolife.materialviewpager.db.RepoModel;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.RecyclerViewHolder> {
private List<RepoModel> repoModels;

    public CustomAdapter(List<RepoModel> repoModels) {
        this.repoModels = repoModels;
    }

    @Override
    public CustomAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        RepoModel repoModel = repoModels.get(position);
        holder.viewName.setText(repoModel.getName());
        holder.viewDescription.setText( repoModel.getDescription());
        holder.viewAutor.setText(repoModel.getAutor());
        holder.viewAvatar.setText(repoModel.getAvatar());
        holder.viewForks.setText(String.valueOf(repoModel.getForks()));
        holder.viewWatches.setText(String.valueOf(repoModel.getWatches()));

    }

    @Override
    public int getItemCount() {
        return repoModels.size();
    }

    public void addItem(List<RepoModel> repoModels){
        this.repoModels = repoModels;
        notifyDataSetChanged();
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView viewName;
        private TextView viewDescription;
        private TextView viewAutor;
        private TextView viewAvatar;
        private TextView viewForks;
        private TextView viewWatches;




        RecyclerViewHolder(View view) {
            super(view);
            viewName = (TextView) view.findViewById(R.id.viewName);
            viewDescription = (TextView) view.findViewById(R.id.viewDescription);
            viewAutor = (TextView) view.findViewById(R.id.viewAutor);
            viewAvatar = (TextView) view.findViewById(R.id.viewAvatar);
            viewForks = (TextView) view.findViewById(R.id.viewForks);
            viewWatches = (TextView) view.findViewById(R.id.viewWatches);

        }
    }
}
