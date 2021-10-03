package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Media {

    public String type;
    public String[] url_list;

    public static Media fromJson(JSONObject entities) throws JSONException {
        Media media = new Media();

        media.type = entities.getJSONArray("media").getJSONObject(0).getString("type");

        media.url_list = new String[entities.getJSONArray("media").length()];

        for(int i = 0; i < entities.getJSONArray("media").length(); i++) {
            media.url_list[i] = entities.getJSONArray("media").getJSONObject(i).getString("media_url_https");
        }

        return media;
    }

}
