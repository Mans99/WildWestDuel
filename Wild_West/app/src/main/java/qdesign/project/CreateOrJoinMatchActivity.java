package qdesign.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import qdesign.project.firebase.match.FirebaseMatch;
import qdesign.project.util.ThemeMediaPlayer;

public class CreateOrJoinMatchActivity extends AppCompatActivity {

    ImageButton createMatchButton;
    ImageButton joinMatchButton;

    ImageButton returnHomeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_or_join_match);
        ThemeMediaPlayer themeMediaPlayer = (ThemeMediaPlayer) getApplicationContext();
        if(!themeMediaPlayer.isPlaying()) {
            themeMediaPlayer.startMusic();
        }

        createMatchButton = findViewById(R.id.createMatchButton);
        joinMatchButton = findViewById(R.id.joinMatchButton);
        returnHomeButton = findViewById(R.id.returnHomeButton);
        createMatchButton.setOnClickListener(v -> {
            String matchString = FirebaseMatch.generateMatchString();
            FirebaseMatch.createMatch(matchString,
                    () -> {
                        Intent i = new Intent(getApplicationContext(), CreateMatchActivity.class);
                        startActivity(i);
                    },
                    () -> {
                        Intent i = new Intent(getApplicationContext(), MatchActivity.class);
                        startActivity(i);
                    },
                    () -> {});
        });
        joinMatchButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), JoinMatchActivity.class);
            startActivity(i);
        });
        returnHomeButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });
    }
}