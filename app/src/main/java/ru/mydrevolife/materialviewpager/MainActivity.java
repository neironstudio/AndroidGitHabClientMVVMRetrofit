package ru.mydrevolife.materialviewpager;



import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mydrevolife.materialviewpager.api.ApiService;

import ru.mydrevolife.materialviewpager.api.Repository;
import ru.mydrevolife.materialviewpager.db.ConfigModel;
import ru.mydrevolife.materialviewpager.db.RepoModel;
import ru.mydrevolife.materialviewpager.viewmodel.ViewModelConfig;
import ru.mydrevolife.materialviewpager.viewmodel.ViewModelRepo;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;
    ViewModelRepo viewModelRepo;
    ViewModelConfig viewModelConfig;
    Retrofit retrofit;
    ApiService apiService;
    String login;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        ButterKnife.bind(this);
        viewModelRepo = ViewModelProviders.of(this).get(ViewModelRepo.class);
        viewModelConfig = ViewModelProviders.of(this).get(ViewModelConfig.class);
        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {

                return RecyclerViewFragment.newInstance();

            }

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return "My repo";

            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://phandroid.s3.amazonaws.com/wp-content/uploads/2014/06/android_google_moutain_google_now_1920x1080_wallpaper_Wallpaper-HD_2560x1600_www.paperhi.com_-640x400.jpg");

                }


                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        if (viewModelConfig.getConfig() == null) {
            alertDialog();
        } else {
            ConfigModel configModel = viewModelConfig.getConfig();
            login = configModel.getLogin();
            pass = configModel.getPass();
        }


        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();

                    alertDialog();


                }
            });
        }
    }

    private void alertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("input login pass");
        builder.setView(getLayoutInflater().inflate(R.layout.set_note_passwd, null));
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setNegativeButton(android.R.string.cancel, null);
        final AlertDialog dialog = builder.create();
        builder.setCancelable(false);
        dialog.show();
        dialog.setCancelable(false);
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        EditText editText1UserId = (EditText) dialog.findViewById(R.id.editText1InputPass);
                        EditText editText2UserId = (EditText) dialog.findViewById(R.id.editText2InputPass);
                        login = editText1UserId.getText().toString();
                        pass = editText2UserId.getText().toString();

                        autorities();
                        getData();
                        dialog.dismiss();


                    }
                });

        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();
                    }
                });


    }


    private void autorities() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient()

                .newBuilder()

                .addInterceptor
                        (new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                                Request originalRequest = chain.request();

                                Request.Builder builder = originalRequest

                                        .newBuilder()
                                        .header("Authorization",

                                                Credentials.basic(login, pass));


                                Request newRequest = builder.build();
                                return chain.proceed(newRequest);
                            }
                        })
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")

                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private void getData() {


        Call<List<Repository>> calllist = apiService.getRepoService();
        calllist.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> calllist, Response<List<Repository>> response) {
                // hidePDialog();
                try {
                    if (response.isSuccess()) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        viewModelConfig.addConfig(new ConfigModel(login, pass));
                        for (Repository item : response.body()) {

                            RepoModel repo = new RepoModel(item.id, item.name.toString(), item.description.toString(), item.owner.autor.toString(), item.owner.avatar.toString(), item.forks, item.watches);
                            viewModelRepo.addLocalDbRepo(repo);

                        }



                    } else {
                        Toast.makeText(getApplicationContext(), "Failed! : " + response.errorBody().string(), Toast.LENGTH_LONG).show();


                    }
                } catch (IOException e) {
                    Log.e("LOG_TAG", "IOException:" + e.getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                //  hidePDialog();
                if (t != null) {
                    Toast.makeText(getApplicationContext(), "Failed! OnFailure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}

