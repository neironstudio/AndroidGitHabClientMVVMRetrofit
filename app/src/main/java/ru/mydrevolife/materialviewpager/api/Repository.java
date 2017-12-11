package ru.mydrevolife.materialviewpager.api;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Repository  {




    @SerializedName("id")

    public
    int id;
    @SerializedName("name")

    public
    String name;
    @SerializedName("full_name")
    public
    String description;

     @SerializedName("forks_count")
    public
   int forks;
    @SerializedName("watchers_count")
    public
    int watches;

 @SerializedName("owner")

 public
 Owner owner;
 public class Owner{
     @SerializedName("login")
     public
     String autor;
     @SerializedName("id")
     int login_id;
     @SerializedName("avatar_url")
     public
     String avatar;

 }


}
