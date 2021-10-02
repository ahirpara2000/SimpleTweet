package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.google.android.exoplayer2.SimpleExoPlayer;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);

        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        ImageView ivMedia;
        TextView tvBody;
        TextView tvName;
        TextView tvScreenName;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvName = itemView.findViewById(R.id.tvName);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTime = itemView.findViewById(R.id.tvTime);
        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvName.setText(tweet.user.name);
            tvScreenName.setText("@" + tweet.user.screenName);
            tvTime.setText("· " + tweet.createdAt);

            ivMedia.layout(0, 0, 0, 0);

            Glide.with(context)
                    .load(tweet.user.profileImageUrl.replace("_normal", ""))
                    .centerCrop()
                    .circleCrop()
                    .into(ivProfileImage);

            try {
                if(tweet.media.type.equals("photo")) {
                    ivMedia.setVisibility(View.VISIBLE);
                    Glide.with(context)
                            .load(tweet.media.url)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .override(Target.SIZE_ORIGINAL)
                            .into(ivMedia);
                }
                else {
                    ivMedia.setVisibility(View.GONE);
                }
            }
            catch (NullPointerException e) {
                ivMedia.setVisibility(View.GONE);
            }
        }
    }

}
