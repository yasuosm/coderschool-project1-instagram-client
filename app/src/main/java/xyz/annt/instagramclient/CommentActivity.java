package xyz.annt.instagramclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CommentActivity extends AppCompatActivity {
    private String mediaId;
    private ArrayList<Comment> comments;
    private CommentsAdapter commentsAdapter;
    @Bind(R.id.lvComments) ListView lvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setTitle(R.string.comments);

        ButterKnife.bind(this);

        mediaId = getIntent().getStringExtra("mediaId");

        comments = new ArrayList<>();
        commentsAdapter = new CommentsAdapter(this, comments);
        lvComments.setAdapter(commentsAdapter);

        fetchComments();
    }

    public void fetchComments() {
        String url = "https://api.instagram.com/v1/media/" + mediaId + "/comments?client_id=" +
                getResources().getString(R.string.instagram_client_id);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray commentsJSON = response.optJSONArray("data");
                    if (null != commentsJSON) {
                        for (int i = 0; i < commentsJSON.length(); i++) {
                            JSONObject commentJSON = commentsJSON.optJSONObject(i);
                            Comment comment = getCommentFromJSONObject(commentJSON);
                            comments.add(comment);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                commentsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
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
