package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class TweetDetailActivity extends AppCompatActivity {

    private final int RESULT_CODE  = 2;
    // declaring views
    public ImageButton ibDetailFavorite;
    public TextView tvDetailFavorites;
    public ImageButton ibDetailRetweets;
    public TextView tvDetailRetweets;
    public ImageView ivDetailProfile;
    public TextView tvDetailUsername;
    public TextView tvDetailBody;
    // tweet to hold unwrapped tweet
    public Tweet detailTweet;
    Tweet rtTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // loading views
        setContentView(R.layout.activity_tweet_detail);
        ibDetailFavorite = findViewById(R.id.ibDetailFavorite);
        tvDetailFavorites = findViewById(R.id.tvDetailFavorites);
        tvDetailRetweets = findViewById(R.id.tvDetailRetweets);
        ibDetailRetweets = findViewById(R.id.ibDetailRetweet);
        ivDetailProfile = findViewById(R.id.ivDetailProfile);
        tvDetailUsername = findViewById(R.id.tvDetailUsername);
        tvDetailBody = findViewById(R.id.tvDetailBody);

        // setting content to views
        detailTweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        tvDetailUsername.setText(detailTweet.user.name);
        tvDetailBody.setText(detailTweet.body);
        tvDetailRetweets.setText(Integer.toString(detailTweet.retweets));
        tvDetailFavorites.setText(Integer.toString(detailTweet.getFavorites()));

        Glide.with(this).load(detailTweet.user.profileImageURL).into(ivDetailProfile);

        ibDetailRetweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creating message
                TextView rtBody = (TextView) findViewById(R.id.tvDetailBody);
                TextView rtUsername = (TextView) findViewById(R.id.tvDetailUsername);
                String finalMessage = rtUsername.toString() + ": " + rtBody.toString();
                long userID = detailTweet.uid;

                TwitterClient client = TwitterApp.getRestClient(getApplicationContext());
                client.reTweet(userID, finalMessage, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Context context = getApplicationContext();
                            rtTweet = Tweet.fromJSON(response);
                            Intent retweet = new Intent(context, TimelineActivity.class);
                            retweet.putExtra("tweet", Parcels.wrap(rtTweet));
                            setResult(RESULT_CODE, retweet);
                            context.startActivity(retweet);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }
}
