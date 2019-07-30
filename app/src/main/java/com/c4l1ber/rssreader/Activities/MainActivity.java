package com.c4l1ber.rssreader.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.c4l1ber.rssreader.Adapter.RSSAdapter;
import com.c4l1ber.rssreader.DataModel.RSSItem;
import com.c4l1ber.rssreader.Networking.API;
import com.c4l1ber.rssreader.Networking.RetrofitInstance;
import com.c4l1ber.rssreader.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.feedToolbar);
        toolbar.inflateMenu(R.menu.menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                if(menuItem.getItemId() == R.id.btn_refresh) {

                    getFeedData();
                    return true;
                }
                else {

                    return false;
                }
            }
        });

        getFeedData();
    }

    private void getFeedData() {

        API api = RetrofitInstance.getRetrofitInstance().create(API.class);
        Call<RSSItem> call = api.getNews();
        call.enqueue(new Callback<RSSItem>() {
            @Override
            public void onResponse(Call<RSSItem> call, Response<RSSItem> response) {

                if(response.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "Successful response", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "rssItemList size: " + response.body().getItems());
                    feedInit(response.body().getItems());
                }
                else {

                    Toast.makeText(MainActivity.this, "Response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RSSItem> call, Throwable t) {


            }
        });
    }

    private void feedInit(List<RSSItem.Item> rssItemList) {

        RecyclerView mRecyclerview = findViewById(R.id.recyclerView);
        RSSAdapter mAdapter = new RSSAdapter(this, rssItemList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(mAdapter);
    }
}
