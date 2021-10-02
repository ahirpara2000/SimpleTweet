package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Tweet {


    public String body;
    public String createdAt;
    public User user;
    public Media media;
    public long id;
    public String retweetCount;
    public String favoritesCount;
    public boolean retweetStatus;
    public RetweetUser retweetUser;

    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = TwitterTimeDifferentitaion(jsonObject.getString("created_at"));
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");
        tweet.retweetCount = String.valueOf(jsonObject.getInt("retweet_count"));
        tweet.favoritesCount = String.valueOf(jsonObject.getInt("favorite_count"));

        try {
            jsonObject.getJSONObject("retweeted_status");
            tweet.retweetStatus = true;
            tweet.retweetUser = RetweetUser.fromJson(jsonObject.getJSONObject("retweeted_status"));
        } catch (JSONException e) {
            tweet.retweetStatus = false;
        }

        try {
            tweet.media = Media.fromJson(jsonObject.getJSONObject("extended_entities"));
        }
        catch (JSONException e) { }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJSON(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public static String TwitterTimeDifferentitaion(String responseTime) {

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        ParsePosition pos = new ParsePosition(0);
        long then = formatter.parse(responseTime, pos).getTime();
        long now = new Date().getTime();

        long seconds = (now - then) / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        String result = null;
        long num = 0;
        if (days > 0) {
            num = days;
            result = days + "d";
        }
        else if (hours > 0) {
            num = hours;
            result = hours + "h";
        }
        else if (minutes > 0) {
            num = minutes;
            result = minutes + "m";
        }
        else {
            num = seconds;
            result = seconds + "s";
        }
        if (num > 1) {
            result += "s";
        }

        Log.d("Tweet", "Result: " + result);
        return (result);

    }
}
