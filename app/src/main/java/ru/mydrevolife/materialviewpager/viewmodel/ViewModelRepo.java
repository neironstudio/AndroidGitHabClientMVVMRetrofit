package ru.mydrevolife.materialviewpager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

import ru.mydrevolife.materialviewpager.db.AppDatabase;
import ru.mydrevolife.materialviewpager.db.RepoModel;



public class ViewModelRepo extends AndroidViewModel{
    private AppDatabase appDatabase;
    private final LiveData<List<RepoModel>> repoList;

    public ViewModelRepo(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
        repoList = appDatabase.getRepoDAO().getAllRepo();
    }

public void addLocalDbRepo(final RepoModel repoModel){new addasyncTask(appDatabase).execute(repoModel);}


    private  class addasyncTask extends AsyncTask<RepoModel,Void,Void> {
      private  AppDatabase db;
        addasyncTask(AppDatabase appDatabase) {db = appDatabase; }

        @Override
        protected Void doInBackground(RepoModel... repoModels) {
           db.getRepoDAO().addRepo(repoModels[0]);
            return null;
        }
    }

public LiveData<List<RepoModel>> getRepoList(){
    return  repoList;
}


}
