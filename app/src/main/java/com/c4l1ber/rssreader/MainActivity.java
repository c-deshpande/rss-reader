package com.c4l1ber.rssreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.c4l1ber.rssreader.Adapter.RSSAdapter;
import com.c4l1ber.rssreader.DataModel.RSSItem;
import com.c4l1ber.rssreader.Networking.API;
import com.c4l1ber.rssreader.Networking.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    List<RSSItem.Item> rssItemList;
    RecyclerView mRecyclerview;
    RSSAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadItems();

        mRecyclerview = findViewById(R.id.recyclerView);
    }

    private void loadItems() {

        API api = RetrofitInstance.getRetrofitInstance().create(API.class);
        Call<RSSItem> call = api.getNews();
        call.enqueue(new Callback<RSSItem>() {
            @Override
            public void onResponse(Call<RSSItem> call, Response<RSSItem> response) {

                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Response size: "+response.body().getItems().size());
                //response.body().getItems();
            }

            @Override
            public void onFailure(Call<RSSItem> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
