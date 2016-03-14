package xyz.annt.instagramclient;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ArrayList<MediaItem> items;
    private MediaItemsAdapter itemsAdapter;
    @Bind(R.id.srlContainer) SwipeRefreshLayout srlContainer;
    @Bind(R.id.lvItems) ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.instagram);

        ButterKnife.bind(this);

        items = new ArrayList<>();
        itemsAdapter = new MediaItemsAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

        srlContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        srlContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPopularMediaItems();
            }
        });
        srlContainer.post(new Runnable() {
            @Override
            public void run() {
                srlContainer.setRefreshing(true);
                fetchPopularMediaItems();
            }
        });
    }

    public void fetchPopularMediaItems() {
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + getResources().getString(R.string.instagram_client_id);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    items.clear();
                    JSONArray itemsJSON = response.optJSONArray("data");
                    if (null != itemsJSON) {
                        for (int i = 0; i < itemsJSON.length(); i++) {
                            JSONObject itemJSON = itemsJSON.optJSONObject(i);
                            MediaItem item = getMediaItemFromJSONObject(itemJSON);
                            items.add(item);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // notify adapter
                itemsAdapter.notifyDataSetChanged();
                // stop refresh
                srlContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                // stop refresh
                srlContainer.setRefreshing(false);
            }
        });
    }

    private MediaItem getMediaItemFromJSONObject(JSONObject object) throws JSONException {
        MediaItem item = new MediaItem();

        item.setId(object.optString("id"));
        item.setType(object.optString("type"));
        JSONObject captionJSON = object.optJSONObject("caption");
        if (null != captionJSON) {
            item.setCaptionText(captionJSON.optString("text"));
            JSONObject from = captionJSON.optJSONObject("from");
            if (null != from) {
                item.setCaptionFrom(from.optString("username"));
            }
        }
        JSONObject likesJSON = object.optJSONObject("likes");
        if (null != likesJSON) {
            item.setLikesCount(likesJSON.optInt("count"));
        }
        JSONObject userJSON = object.optJSONObject("user");
        if (null != userJSON) {
            item.setUsername(userJSON.optString("username"));
            item.setProfilePicture(userJSON.optString("profile_picture"));
        }
        item.setCreatedTime(object.optInt("created_time"));
        JSONObject imagesJSON = object.optJSONObject("images");
        if (null != imagesJSON) {
            JSONObject imageStandardJSON = imagesJSON.optJSONObject("standard_resolution");
            if (null != imageStandardJSON) {
                item.setImageStandardUrl(imageStandardJSON.optString("url"));
                item.setImageStandardWidth(imageStandardJSON.optInt("width"));
                item.setImageStandardHeight(imageStandardJSON.optInt("height"));
            }
        }
        JSONObject videosJSON = object.optJSONObject("videos");
        if (null != videosJSON) {
            JSONObject videoStandardJSON = videosJSON.optJSONObject("standard_resolution");
            if (null != videoStandardJSON) {
                item.setVideoStandardUrl(videoStandardJSON.optString("url"));
            }
        }
        JSONObject commentsJSON = object.optJSONObject("comments");
        if (null != commentsJSON) {
            item.setCommentsCount(commentsJSON.optInt("count"));

            JSONArray commentsDataJSON = commentsJSON.optJSONArray("data");
            if (null != commentsDataJSON) {
                ArrayList<Comment> comments = new ArrayList<>();
                for (int i = 0; i < commentsDataJSON.length(); i++) {
                    JSONObject commentJSON = commentsDataJSON.optJSONObject(i);
                    Comment comment = getCommentFromJSONObject(commentJSON);
                    comments.add(comment);
                }
                item.setComments(comments);
            }
        }

        return item;
    }

    private Comment getCommentFromJSONObject(JSONObject object) throws JSONException {
        Comment comment = new Comment();

        comment.setCreatedTime(object.optInt("created_time"));
        comment.setText(object.optString("text"));
        JSONObject fromJSON = object.optJSONObject("from");
        if (null != fromJSON) {
            comment.setUsername(fromJSON.optString("username"));
            comment.setProfilePicture(fromJSON.optString("profile_picture"));
        }

        return comment;
    }
}
