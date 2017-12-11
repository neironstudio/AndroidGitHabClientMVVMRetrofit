package ru.mydrevolife.materialviewpager;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.mydrevolife.materialviewpager.adapter.CustomAdapter;
import ru.mydrevolife.materialviewpager.db.RepoModel;
import ru.mydrevolife.materialviewpager.viewmodel.ViewModelRepo;


public class RecyclerViewFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;

    ViewModelRepo viewModelRepo;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
private CustomAdapter customAdapter;
    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        viewModelRepo = ViewModelProviders.of(this).get(ViewModelRepo.class);
        customAdapter = new CustomAdapter(new ArrayList<RepoModel>());

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        mRecyclerView.setAdapter(customAdapter);

       viewModelRepo.getRepoList().observe(this, new Observer<List<RepoModel>>() {
           @Override
           public void onChanged(@Nullable List<RepoModel> repoModels) {
             try{
               customAdapter.addItem(repoModels);}
               catch (Exception exc){}
           }
       });

    }
}
