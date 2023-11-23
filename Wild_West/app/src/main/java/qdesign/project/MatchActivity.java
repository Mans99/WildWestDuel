package qdesign.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.media.MediaPlayer;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import qdesign.project.firebase.match.FirebaseMatch;
import qdesign.project.util.ThemeMediaPlayer;


public class MatchActivity extends AppCompatActivity implements SensorEventListener {
    private int counter;
    private long time;
    private int k = 0;
    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView counttime;
    private Vibrator vibrator;



    private MediaPlayer mp;
    private MediaPlayer beep;
    private MediaPlayer theme;
    private MediaPlayer lower;
    private MediaPlayer draw;
    private MediaPlayer heart;
    private MediaPlayer whistle;
    private MediaPlayer lose;
    private MediaPlayer cheer;

    private ImageView gun;

private float volume;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_match);
        ThemeMediaPlayer themeMediaPlayer = (ThemeMediaPlayer) getApplicationContext();
        themeMediaPlayer.pauseMusic();
            counttime = findViewById(R.id.counterView);

            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.gun);
        beep = MediaPlayer.create(this, R.raw.beep);
        theme = MediaPlayer.create(this, R.raw.goodbadugly);
        lower = MediaPlayer.create(this, R.raw.lower_gun);
        whistle = MediaPlayer.create(this, R.raw.vissla);
        draw = MediaPlayer.create(this, R.raw.draw);
        heart = MediaPlayer.create(this, R.raw.heartbeat);
        lose = MediaPlayer.create(this, R.raw.lose);
        cheer = MediaPlayer.create(this, R.raw.cheer);
        gun = findViewById(R.id.gunImageView);

        volume = 0.7f;
        theme.setVolume(volume,volume);

        counttime.setText("Lower your phones!");
        lower.start();

        long timeToStart = FirebaseMatch.ACTIVE_MATCH_TIME - System.currentTimeMillis();

        System.out.println(timeToStart);

        new CountDownTimer((int) timeToStart, 1) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                theme.start();
                startMatch();
            }
        }.start();

        }

        private void startMatch() {
            Integer randomTime = (FirebaseMatch.RANDOM_TIME.intValue() * 1000);
            counter = randomTime/1000;
            new CountDownTimer(randomTime, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    vibrator.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.DEFAULT_AMPLITUDE));

                    if (counter == 8) {
                        counttime.setText("");
                        counter--;
                    } else {
                        counter--;
                    }
                }

                @Override
                public void onFinish() {
                    k = 1;
                    System.out.println("Finished match: " + System.currentTimeMillis() + 500);
                    time = System.currentTimeMillis();
                    counttime.setText("Shoot!");
                    theme.stop();
                    draw.start();
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }.start();

        }

        protected void onResume() {
            super.onResume();
            sensorManager.registerListener(this , sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        protected void onPause() {
            super.onPause();
            sensorManager.unregisterListener(this);
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            if (event.values[0] <= 0){
                gun.setScaleY(2);
            } else {
                gun.setScaleY(-2);
            }
            if (k == 1) {
                if (event.values[0] > 9.5 || event.values[0] < -9.5) {
                    mp.start();
                    time = System.currentTimeMillis() - time;
                    k = 0;
                    FirebaseMatch.PLAYER_TIME = time;
                    FirebaseMatch.setPlayerTime(time);
                    showDialog(String.valueOf(time));
                }
            }
        }

    private void showDialog(String score) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_game_menu);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Find the buttons inside the dialog layout and set their click listeners
        ImageButton playAgainButton = dialog.findViewById(R.id.playAgainButton);
        ImageButton returnHomeButton = dialog.findViewById(R.id.returnHomeButton);

        TextView scoreTextView = dialog.findViewById(R.id.scoreTextView);
        TextView gameResultTextView = dialog.findViewById(R.id.gameResultTextView);

        playAgainButton.setOnClickListener(v -> {
            // Do something when the Play Again button is clicked
            Intent i = new Intent(getApplicationContext(), CreateOrJoinMatchActivity.class);
            startActivity(i);
        });

        returnHomeButton.setOnClickListener(v -> {
            // Do something when the Return to Home button is clicked
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });

        scoreTextView.setText(score + "ms");

        FirebaseMatch.seeMatchResult(() -> {
            if (FirebaseMatch.PLAYER_TIME >= FirebaseMatch.OTHER_PLAYER_TIME){
                gameResultTextView.setText("You lost!");

            } else {
                gameResultTextView.setText("You won!");

            }
        });

        dialog.show();
    }
}
