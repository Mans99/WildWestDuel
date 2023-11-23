package qdesign.project.util;

import android.app.Application;
import android.media.MediaPlayer;
import qdesign.project.R;

public class ThemeMediaPlayer extends Application {
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.saloon_piano);
        mediaPlayer.setLooping(true);
    }

    public void startMusic() {
        mediaPlayer.start();
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }
}

