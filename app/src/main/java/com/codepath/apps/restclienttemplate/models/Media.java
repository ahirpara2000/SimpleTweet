package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Media {

    public String url;
    public String type;

    public static Media fromJson(JSONObject entities) throws JSONException {
        Media media = new Media();

        media.url = entities.getJSONArray("media").getJSONObject(0).getString("media_url_https");
        media.type = entities.getJSONArray("media").getJSONObject(0).getString("type");

        return media;
    }
}
