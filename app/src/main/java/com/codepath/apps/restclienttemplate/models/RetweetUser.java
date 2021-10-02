package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

public class RetweetUser {

    public User user;

    public static RetweetUser fromJson(JSONObject jsonObject) throws JSONException {
        RetweetUser retweetUser = new RetweetUser();
        retweetUser.user = User.fromJson(jsonObject.getJSONObject("user"));
        return retweetUser;
    }

}
