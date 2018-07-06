package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {

    // list out attributes
    public String body;
    public long uid;
    public User user;
    public String createdAt;
   // public int replies;
    public int retweets;
    public int favorites;

    public Tweet(){}

    public User getUser() {
        return user;
    }

    public int getFavorites() {
        return favorites;
    }

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        // extract value from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.retweets = jsonObject.getInt("retweet_count");
        if (jsonObject.optJSONObject("retweeted_status") == null) {
            tweet.favorites = jsonObject.getInt("favorite_count");
        } else {
            JSONObject retweetFavCount = jsonObject.getJSONObject("retweeted_status");
            tweet.favorites = retweetFavCount.getInt("favorite_count");
        }
        //tweet.replies = jsonObject.getInt("reply_count");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        return tweet;
    }



}
