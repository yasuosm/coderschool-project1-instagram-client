package xyz.annt.instagramclient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
    private ProgressDialog pdVideo;
    private VideoView vvVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        vvVideo = (VideoView) findViewById(R.id.vvVideo);
        pdVideo = new ProgressDialog(VideoPlayerActivity.this);
        pdVideo.setMessage(getResources().getString(R.string.buffering));
        pdVideo.setIndeterminate(false);
        pdVideo.setCancelable(false);
        pdVideo.show();

        try {
            MediaController mediacontroller = new MediaController(VideoPlayerActivity.this);
            mediacontroller.setAnchorView(vvVideo);
            String videoUrl = getIntent().getStringExtra("videoUrl");
            Log.d("VIDEO", videoUrl);
            Uri videoUri = Uri.parse(videoUrl);
            vvVideo.setMediaController(mediacontroller);
            vvVideo.setVideoURI(videoUri);
        } catch (Exception e) {
            e.printStackTrace();
        }

        vvVideo.requestFocus();
        vvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pdVideo.dismiss();
                vvVideo.start();
            }
        });
    }
}
