package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codepath.apps.restclienttemplate.models.PictureSlideActivity;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

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
        TextView tvRetweets;
        TextView tvFavorites;
        TextView rtStatus;
        ImageView ivMedia2;
        LinearLayout linearLayout;
        String profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            ivMedia = itemView.findViewById(R.id.ivMedia);
            ivMedia2 = itemView.findViewById(R.id.ivMedia2);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvName = itemView.findViewById(R.id.tvName);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvRetweets = itemView.findViewById(R.id.tvRetweets);
            tvFavorites = itemView.findViewById(R.id.tvFavorites);
            rtStatus = itemView.findViewById(R.id.rtstatus);
            linearLayout = itemView.findViewById(R.id.linear_image);
        }

        public void bind(Tweet tweet) {
            if(!tweet.retweetStatus) {
                tvName.setText(tweet.user.name);
                tvScreenName.setText("@" + tweet.user.screenName);
                profileImage = tweet.user.profileImageUrl.replace("_normal", "");

                checkVerifiedUser(tweet.user.verified, itemView);
            }
            else {
                tvName.setText(tweet.retweetUser.user.name);
                tvScreenName.setText("@" + tweet.retweetUser.user.screenName);
                profileImage = tweet.retweetUser.user.profileImageUrl.replace("_normal", "");

                checkVerifiedUser(tweet.retweetUser.user.verified, itemView);
            }

            tvBody.setText(tweet.body);
            tvTime.setText("· " + tweet.createdAt);
            tvRetweets.setText(tweet.retweetCount);
            tvFavorites.setText(tweet.favoritesCount);

            ivMedia.layout(0, 0, 0, 0);

            Glide.with(context)
                    .load(profileImage)
                    .centerCrop()
                    .circleCrop()
                    .into(ivProfileImage);

            Log.d("tweetAdapter", "Status: " + tweet.retweetStatus);

            if(tweet.retweetStatus) {
                rtStatus.setText(tweet.user.name + " Retweeted");
                rtStatus.setVisibility(View.VISIBLE);
            }
            else {
                rtStatus.setVisibility(View.GONE);
            }

            try {
                if(tweet.media.type.equals("photo")) {

                    ivMedia.setVisibility(View.VISIBLE);

                    Glide.with(context)
                            .load(tweet.media.url_list[0])
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .override(Target.SIZE_ORIGINAL)
                            .into(ivMedia);

                    if(tweet.media.num_images > 1) {

                        ivMedia.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        linearLayout.setClipToOutline(true);
                        ivMedia2.setVisibility(View.VISIBLE);

                        Glide.with(context)
                                .load(tweet.media.url_list[1])
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                                .override(Target.SIZE_ORIGINAL)
                                .into(ivMedia2);
                    }

                    else {
                        ivMedia.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        ivMedia2.setVisibility(View.GONE);
                    }

                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, PictureSlideActivity.class);
                            intent.putExtra("url_list", tweet.media.url_list);
                            context.startActivity(intent);
                        }
                    });
                }
                else {
                    ivMedia.setVisibility(View.GONE);
                    ivMedia2.setVisibility(View.GONE);
                }
            }
            catch (NullPointerException e) {
                ivMedia.setVisibility(View.GONE);
                ivMedia2.setVisibility(View.GONE);
            }
        }
    }

    private void checkVerifiedUser(Boolean verified, View itemView) {
        TextView tvName = itemView.findViewById(R.id.tvName);
        if(verified) {
            tvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_twitter_verified_badge, 0);
        }
    }

}
