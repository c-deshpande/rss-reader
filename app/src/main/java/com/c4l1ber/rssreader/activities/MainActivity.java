package com.c4l1ber.rssreader.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.Toast;

import com.c4l1ber.rssreader.adapters.RSSAdapter;
import com.c4l1ber.rssreader.data.RSSItem;
import com.c4l1ber.rssreader.networking.API;
import com.c4l1ber.rssreader.networking.RetrofitInstance;
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

                if (menuItem.getItemId() == R.id.btn_refresh) {

                    getFeedData();
                    return true;
                } else {

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

                if (response.isSuccessful()) {

                    Toast.makeText(MainActivity.this, "Successful response", Toast.LENGTH_SHORT).show();
                    feedInit(response.body().getItems());
                } else {

                    Toast.makeText(MainActivity.this, "Response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RSSItem> call, Throwable t) {

                Toast.makeText(MainActivity.this, "No response, check your internet connection", Toast.LENGTH_SHORT).show();
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
