package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class RetweetUser {

    public User user;

    public RetweetUser() { }

    public static RetweetUser fromJson(JSONObject jsonObject) throws JSONException {
        RetweetUser retweetUser = new RetweetUser();
        retweetUser.user = User.fromJson(jsonObject.getJSONObject("user"));
        return retweetUser;
    }

}
