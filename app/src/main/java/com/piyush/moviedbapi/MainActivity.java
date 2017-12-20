package com.piyush.moviedbapi;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import ServiceInterface.ApiInterface;
import adapter.HorizontalAdapter;
import adapter.VerticalAdapter;
import helper.CustomClickListener;
import helper.GridSpacingItemDecoration;
import helper.NetworkChecker;
import model.Datum;
import model.JsonData;
import model.Popular;
import retrofit.RetrofitApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewHorizontal;
    RecyclerView recyclerViewVertical;
    HorizontalAdapter horizontalAdapter;
    VerticalAdapter verticalAdapter;
    List<Popular> popularList;
    List<Datum> dataList;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularList = Collections.<Popular>emptyList();
        dataList = Collections.<Datum>emptyList();

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);

        //for spacing after every item
        if (popularList.size() > 0)
            recyclerViewHorizontal.addItemDecoration(new GridSpacingItemDecoration(popularList.size(), spacingInPixels, true, 0));

        initViews();

        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        if (NetworkChecker.isNetworkAvailable(this)) {
            progressBar.setVisibility(View.VISIBLE);
            fetchData();
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void fetchData(){

        Call<JsonData> call = apiInterface.apiCall();

        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {

                JsonData jsonData = response.body();

                dataList = jsonData.getData();
                popularList = jsonData.getPopular();
                progressBar.setVisibility(View.GONE);
                relativeLayout.setBackgroundColor(Color.parseColor("#3481c1"));

                horizontalAdapter = new HorizontalAdapter(MainActivity.this, popularList);
                recyclerViewHorizontal.setAdapter(horizontalAdapter);
                verticalAdapter = new VerticalAdapter(MainActivity.this, dataList, new CustomClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Toast.makeText(MainActivity.this, "Info : "+dataList.get(position).getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                recyclerViewVertical.setAdapter(horizontalAdapter);
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }

    private void openFragment(){


    }

    private void initViews(){
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);

        recyclerViewHorizontal = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        recyclerViewVertical = (RecyclerView) findViewById(R.id.vertical_recycler_view);
        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewVertical.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
}
