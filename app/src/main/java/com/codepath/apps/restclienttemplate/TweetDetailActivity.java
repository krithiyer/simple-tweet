package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity {

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

        //ibDetailRetweets.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
                // creating message
              //  TextView rtBody = (TextView) findViewById(R.id.tvDetailBody);
                //TextView rtUsername = (TextView) findViewById(R.id.tvDetailUsername);
           //     String finalMessage = rtUsername.toString() + ": " + rtBody.toString();
            //    long userID = detailTweet.uid;

              //  TwitterClient client = TwitterApp.getRestClient(getApplicationContext());
               // client.reTweet(userID, finalMessage, new JsonHttpResponseHandler() {
                ///    @Override
                  //  public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //    Context context = getApplicationContext();
                      //  rtTweet = Tweet.fromJSON(response);
                  //  }
 //               });
//
   //         }
  //      });
    }
}
