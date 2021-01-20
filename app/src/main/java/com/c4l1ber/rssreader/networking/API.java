package com.c4l1ber.rssreader.networking;

import com.c4l1ber.rssreader.data.RSSItem;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("/v1/api.json?rss_url=https%3A%2F%2Frss.nytimes.com%2Fservices%2Fxml%2Frss%2Fnyt%2FTechnology.xml")
    Call<RSSItem> getNews();
}
