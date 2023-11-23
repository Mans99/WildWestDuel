package qdesign.project;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Random;


public class PracticeMatchActivity extends AppCompatActivity implements SensorEventListener {
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
    private Random rand;

    private ImageView gun;

    private float volume;

    private int gameTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_match);
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
        gun = findViewById(R.id.gunImageView);
        rand = new Random();
        volume = 0.7f;
        gameTime = (rand.nextInt(8) + 10) * 1000;
        System.out.println(gameTime);
        theme.setVolume(volume,volume);
        counter = gameTime/1000;
        long[] timings = new long[] { 100,  100,  100 };
        int[] amplitudes = new int[] { 255,  0,  255 };
        int repeatIndex = -1; // Do not repeat.



        counttime.setText("Lower your phones!");
        lower.start();

        theme.start();


        new CountDownTimer(gameTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                    vibrator.vibrate(VibrationEffect.createOneShot(350, VibrationEffect.DEFAULT_AMPLITUDE));

                    if (counter == 8) {
                        counttime.setText("");

                    } else {

                    }

                counter--;
            }

            @Override
            public void onFinish() {
                k = 1;
                time = System.currentTimeMillis();
                counttime.setText("Shoot!");

                theme.stop();
                draw.start();

                vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, repeatIndex));

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
                time = System.currentTimeMillis() - time;
                mp.start();
                k = 0;
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

        gameResultTextView.setText("Great Job!");

        playAgainButton.setOnClickListener(v -> {
            // Do something when the Play Again button is clicked
            Intent i = new Intent(getApplicationContext(), PracticeMatchActivity.class);
            startActivity(i);
        });

        returnHomeButton.setOnClickListener(v -> {
            // Do something when the Return to Home button is clicked
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });

        scoreTextView.setText(score + "ms");
        dialog.show();
    }
}
