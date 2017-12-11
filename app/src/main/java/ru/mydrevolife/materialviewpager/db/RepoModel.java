package ru.mydrevolife.materialviewpager.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class RepoModel {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int repoid;
    String name;
    String description;
    String autor;
    String avatar;
    int forks;
    int watches;
    public RepoModel(int repoid,String name, String description, String autor, String avatar, int forks, int watches) {
        this.repoid = repoid;
        this.name = name;
        this.description = description;
        this.autor = autor;
        this.avatar = avatar;
        this.forks = forks;
        this.watches = watches;
    }

    public int getId() {
        return id;
    }

    public int getRepoid() {
        return repoid;
    }

    public void setRepoid(int repoid) {
        this.repoid = repoid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getWatches() {
        return watches;
    }

    public void setWatches(int watches) {
        this.watches = watches;
    }

}
