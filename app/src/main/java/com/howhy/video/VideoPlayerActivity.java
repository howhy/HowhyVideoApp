package com.howhy.video;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoPlayerActivity extends AppCompatActivity {
    private IjkMediaPlayer mPlayer;


    SurfaceView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        videoView=findViewById(R.id.video_view);
        videoView.getHolder().addCallback(callback);

    }
    @Override
    protected void onDestroy() {
        videoView.getHolder().removeCallback(callback);
        super.onDestroy();
    }
    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            createPlayer();
            mPlayer.setDisplay(videoView.getHolder());
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            releasePlayer();
        }
    };

    private void createPlayer() {
        if (mPlayer == null) {
            mPlayer = new IjkMediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mPlayer.setDataSource("https://youku.cdn3-okzy.com/share/d15bd083398976892b54fcdb1d92b0fe#20200731");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.prepareAsync();
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        IjkMediaPlayer.native_profileEnd();
    }

}