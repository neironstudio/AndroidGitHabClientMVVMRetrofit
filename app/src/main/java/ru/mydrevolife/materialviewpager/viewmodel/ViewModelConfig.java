package ru.mydrevolife.materialviewpager.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.concurrent.ExecutionException;

import ru.mydrevolife.materialviewpager.db.AppDatabase;
import ru.mydrevolife.materialviewpager.db.ConfigModel;

public class ViewModelConfig extends AndroidViewModel {
   private AppDatabase appDatabase;


    public ViewModelConfig(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

public ConfigModel getConfig(){
        ConfigModel configModel = null;
        try{
            configModel = new ViewModelConfig.getConfAsyncTask(appDatabase).execute().get();

        }catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }
    return configModel;
}


    public class getConfAsyncTask extends AsyncTask<Void,Void,ConfigModel> {
     private final AppDatabase appDatabase;

        public getConfAsyncTask(AppDatabase appDatabase) {this.appDatabase = appDatabase;
        }

        @Override
        protected ConfigModel doInBackground(Void... voids) {
          ConfigModel configModel = appDatabase.getConfigDAO().getConfig();

            return configModel;
        }
    }

public void addConfig(final ConfigModel configModel){
    new ViewModelConfig.addConfigAsyncTask(appDatabase).execute(configModel);
}

static  private class addConfigAsyncTask extends AsyncTask<ConfigModel,Void,Void>{
    public addConfigAsyncTask(AppDatabase db) {
        this.db = db;
    }

    private AppDatabase db;

    @Override
    protected Void doInBackground(ConfigModel... configModels) {
      db.getConfigDAO().addCnfig(configModels[0]);

        return null;
    }
}

}
