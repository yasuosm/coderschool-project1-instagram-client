package xyz.annt.instagramclient;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by annt on 3/14/16.
 */
public class CommentsAdapter extends ArrayAdapter<Comment> {

    static class ViewHolder {
        @Bind(R.id.ivProfilePicture) ImageView ivProfilePicture;
        @Bind(R.id.tvComment) TextView tvComment;
        @Bind(R.id.tvFromNow) TextView tvFromNow;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

    public CommentsAdapter(Context context, List<Comment> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get item
        Comment comment = getItem(position);

        // View holder
        ViewHolder viewHolder;

        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set data
        viewHolder.ivProfilePicture.setImageResource(0);
        Picasso.with(getContext()).load(comment.getProfilePicture()).into(viewHolder.ivProfilePicture);

        String commentHtml = "<b><font color='" + ContextCompat.getColor(getContext(),
                R.color.colorTextPositive) + "'>" + comment.getUsername() + "</font></b> " +
                "<font color='" + ContextCompat.getColor(getContext(), R.color.colorTextDefault) +
                "'>" + comment.getText() + "</font>";
        viewHolder.tvComment.setText(Html.fromHtml(commentHtml));

        String fromNow = DateUtils.getRelativeTimeSpanString(comment.getCreatedTime() * 1000L,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        viewHolder.tvFromNow.setText(fromNow);

        return convertView;
    }
}
