package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {
    Context context;
    private List<Tweet> mTweets;
    // pass in Tweets array in constructor
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }

    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }


    // for each row, inflate layout and pass to ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context =  parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);
        View tweetView = inflator.inflate(R.layout.item_tweet, parent,false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }



    // bind values based on position of element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get data according to position
        final Tweet tweet = mTweets.get(position);

        // populate views based on data
        holder.tvUserName.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvCreatedAt.setText(getRelativeTimeAgo(tweet.createdAt));
        //holder.tvReplies.setText(Integer.toString(tweet.replies));
        holder.tvRetweets.setText(Integer.toString(tweet.retweets));
        holder.tvFavorites.setText(Integer.toString(tweet.favorites));

        Glide.with(context).load(tweet.user.profileImageURL).into(holder.ivProfileImage);

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUserName;
        public TextView tvBody;
        public TextView tvCreatedAt;
        public ImageButton replyTweet;
        public TextView tvFavorites;
        public TextView tvRetweets;
        public ImageButton ibFavorites;


        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvCreatedAt);
            tvFavorites = (TextView) itemView.findViewById(R.id.tvFavorites);
            tvRetweets = (TextView) itemView.findViewById(R.id.tvRetweets);
            replyTweet = (ImageButton) itemView.findViewById(R.id.ibReply);
            ibFavorites = (ImageButton) itemView.findViewById(R.id.ibFavorite);

            tvBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent tweetBody = new Intent(context, TweetDetailActivity.class);
                    Tweet currTweet = mTweets.get(getAdapterPosition());
                    tweetBody.putExtra("tweet", Parcels.wrap(currTweet));
                    context.startActivity(tweetBody);
                }
            });

            ibFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tweet updateFav = mTweets.get(getAdapterPosition());
                    updateFav.favorites = updateFav.getFavorites() + 1;
                    tvFavorites.setText(Integer.toString(updateFav.getFavorites()));

                }
            });

            replyTweet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ComposeActivity.class);
                    Tweet tweet = mTweets.get(getAdapterPosition());
                    i.putExtra("tweet", tweet.user.screenName);
                    context.startActivity(i);
                }
            });

        }
    }
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
