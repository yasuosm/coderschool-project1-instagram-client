package xyz.annt.instagramclient;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by annt on 3/10/16.
 */
public class MediaItemsAdapter extends ArrayAdapter<MediaItem> {

    // View lookup cache
    static class ViewHolder {
        @Bind(R.id.ivProfilePicture) ImageView ivProfilePicture;
        @Bind(R.id.tvUsername) TextView tvUsername;
        @Bind(R.id.tvFromNow) TextView tvFromNow;
        @Bind(R.id.ivImage) ImageView ivImage;
        @Bind(R.id.ivIconVideo) ImageView ivIconVideo;
        @Bind(R.id.tvLikesCount) TextView tvLikesCount;
        @Bind(R.id.tvCaption) TextView tvCaption;
        @Bind(R.id.tvCommentsCount) TextView tvCommentsCount;
        @Bind(R.id.tvComment0) TextView tvComment0;
        @Bind(R.id.tvComment1) TextView tvComment1;
        @Bind(R.id.ibComment) ImageButton ibComment;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

    public MediaItemsAdapter(Context context, List<MediaItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get item
        final MediaItem item = getItem(position);
        Log.d("VIDEO--", item.getId());

        // View holder
        ViewHolder viewHolder;

        // Inflate layout
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_media, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ibComment.setTag(item.getId());
        viewHolder.tvCommentsCount.setTag(item.getId());
        viewHolder.ivImage.setTag(item.getVideoStandardUrl());

        // Set data to views
        viewHolder.ivProfilePicture.setImageResource(0);
        Picasso.with(getContext()).load(item.getProfilePicture()).into(viewHolder.ivProfilePicture);

        viewHolder.tvUsername.setText(item.getUsername());

        String fromNow = DateUtils.getRelativeTimeSpanString(item.getCreatedTime() * 1000L,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        viewHolder.tvFromNow.setText(fromNow);

        viewHolder.ivImage.setImageResource(0);
        int displayWidth = DeviceDimensionsHelper.getDisplayWidth(getContext());
        int displayHeight = displayWidth * item.getImageStandardHeight() / item.getImageStandardWidth();
        viewHolder.ivImage.getLayoutParams().width = displayWidth;
        viewHolder.ivImage.getLayoutParams().height = displayHeight;
        Picasso.with(getContext()).load(item.getImageStandardUrl())
                .placeholder(R.drawable.img_placeholder).into(viewHolder.ivImage);

        if (item.getType().equals("video")) {
            viewHolder.ivIconVideo.setVisibility(View.VISIBLE);
        }

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String likesCount = formatter.format(item.getLikesCount()) + " " +
                getContext().getResources().getString(R.string.likes);
        viewHolder.tvLikesCount.setText(likesCount);

        String captionHtml = "<b><font color='" + ContextCompat.getColor(getContext(),
                R.color.colorTextPositive) + "'>" + item.getCaptionFrom() + "</font></b> " +
                "<font color='" + ContextCompat.getColor(getContext(), R.color.colorTextDefault) +
                "'>" + item.getCaptionText() + "</font>";
        viewHolder.tvCaption.setText(Html.fromHtml(captionHtml));

        int commentsCount = item.getCommentsCount();
        if (0 < commentsCount) {
            String commentsCountText = getContext().getResources().getString(
                    R.string.view_all_d_comment, item.getCommentsCount());
            viewHolder.tvCommentsCount.setText(commentsCountText);
            viewHolder.tvCommentsCount.setVisibility(View.VISIBLE);
            viewHolder.tvCommentsCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showComments(v, (String) v.getTag());
                }
            });
        }

        ArrayList<Comment> comments = item.getComments();
        if (0 < comments.size()) {
            viewHolder.tvComment0.setText(Html.fromHtml(getCommentHtml(comments.get(0))));
            viewHolder.tvComment0.setVisibility(View.VISIBLE);
        }
        if (1 < comments.size()) {
            viewHolder.tvComment1.setText(Html.fromHtml(getCommentHtml(comments.get(1))));
            viewHolder.tvComment1.setVisibility(View.VISIBLE);
        }

        // Set events
        if (item.getType().equals("video")) {
            viewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showVideo(v, (String) v.getTag());
                }
            });
        }

        viewHolder.ibComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComments(v, (String) v.getTag());
            }
        });

        // return view
        return convertView;
    }

    private String getCommentHtml(Comment comment) {
        String commentHtml = "<b><font color='" + ContextCompat.getColor(getContext(),
                R.color.colorTextPositive) + "'>" + comment.getUsername() + "</font></b> " +
                "<font color='" + ContextCompat.getColor(getContext(), R.color.colorTextDefault) +
                "'>" + comment.getText() + "</font>";

        return commentHtml;
    }

    private void showComments(View v, String mediaId) {
        Intent intent = new Intent(getContext(), CommentActivity.class);
        intent.putExtra("mediaId", mediaId);
        getContext().startActivity(intent);
    }

    private void showVideo(View v, String videoUrl) {
        Intent intent = new Intent(getContext(), VideoPlayerActivity.class);
        intent.putExtra("videoUrl", videoUrl);
        getContext().startActivity(intent);
    }
}
