package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    private final int RESULT_CODE  = 2;
    Button pressButton;
    ImageButton reply;
    EditText currReply;
    Tweet passTweet;
    Tweet rtTweetUnwrap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        pressButton = (Button) findViewById(R.id.tvTweetButton);
        currReply = (EditText) findViewById(R.id.tvNewTweet);
        reply = (ImageButton) findViewById(R.id.ibReply);
        String replyUserName = getIntent().getStringExtra("tweet");
        if (replyUserName != null) {
            currReply.setText("@" + replyUserName);
        }

        pressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etName = (EditText) findViewById(R.id.tvNewTweet);
                TwitterClient client = TwitterApp.getRestClient(getApplicationContext());
                client.sendTweet(etName.getText().toString(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Context context = getApplicationContext();
                            passTweet = Tweet.fromJSON(response);
                            Intent data = new Intent(context, TimelineActivity.class);
                            data.putExtra("tweet", Parcels.wrap(passTweet));
                            setResult(RESULT_CODE, data);
                            context.startActivity(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        throwable.printStackTrace();
                    }

                });
            }
        });
    }
}

