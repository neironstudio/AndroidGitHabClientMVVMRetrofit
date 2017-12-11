package ru.mydrevolife.materialviewpager.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Den on 10.12.2017.
 */
@Dao
public interface RepoDAO {
    @Query("select * from RepoModel")
    LiveData<List<RepoModel>> getAllRepo();

    @Query("select * from RepoModel where id = :id")
    RepoModel getItembyId(int id);

    @Insert(onConflict = REPLACE)
    void addRepo(RepoModel repoModel);

    @Delete
    void delRepo(RepoModel repoModel);

}
