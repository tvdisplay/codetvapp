package com.app.beyondlotto.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.app.beyondlotto.Model.AppRepository;
import com.app.beyondlotto.R;

public class Screen5Activity extends AppCompatActivity {
    ImageView imageView;
    ProgressBar pbar;
    VideoView videoView;
    private static final String TAG = "Screen5Activity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);
        init();
        AppRepository.getUser(this, getApplicationContext(), "screen5", (status, user) -> {
            if (status) {
                AppRepository.getGamesofUser(this, user, getApplicationContext(), "screen5", (type, img) -> {
                    if (type != null && img != null) {
                        setMediaData(Screen5Activity.this, type, img, "");
                    } else {
                        Toast.makeText(this, "No Data found!", Toast.LENGTH_SHORT).show();
                        pbar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }

    private void init() {
        imageView = findViewById(R.id.imagev);
        pbar = findViewById(R.id.pbar);
        pbar.setVisibility(View.VISIBLE);
        videoView = (VideoView) findViewById(R.id.myvideoview);
    }

    private void setMediaData(Context context, String type, String img, String orientation) {

        pbar.setVisibility(View.GONE);
        if (type != null) {
            if (type.equals("image")) {
                imageView.setVisibility(View.VISIBLE);
                Glide.with(context).load(img).into(imageView);
            } else if (type.equals("video")) {
                videoView.setVisibility(View.VISIBLE);
                String LINK = img;
                MediaController mc = new MediaController(context);
                mc.setAnchorView(videoView);
                mc.setMediaPlayer(videoView);
                Uri video = Uri.parse(LINK);
                videoView.setMediaController(mc);
                videoView.setVideoURI(video);
                videoView.start();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                    }
                });
            }
        }
    }
}