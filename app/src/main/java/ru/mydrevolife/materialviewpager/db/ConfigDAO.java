package ru.mydrevolife.materialviewpager.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Den on 11.12.2017.
 */
@Dao
public interface ConfigDAO {
    @Query("select * from ConfigModel")
    ConfigModel getConfig();
    @Insert(onConflict = REPLACE)
    void addCnfig(ConfigModel configModel);
}
