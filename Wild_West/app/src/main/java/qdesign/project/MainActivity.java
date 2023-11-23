package qdesign.project;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import qdesign.project.databinding.ActivityMainBinding;
import qdesign.project.firebase.users.FirebaseUsers;
import qdesign.project.util.ThemeMediaPlayer;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton playButton = findViewById(R.id.playNowButton);
        ImageButton GunRangeButton = findViewById(R.id.shootingRangeButton);
        ImageButton tutorialButton = findViewById(R.id.tutorialButton);

        ThemeMediaPlayer themeMediaPlayer = (ThemeMediaPlayer) getApplicationContext();
        if(!themeMediaPlayer.isPlaying()) {
            themeMediaPlayer.startMusic();
        }
        playButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreateOrJoinMatchActivity.class);
            startActivity(intent);
        });

      GunRangeButton.setOnClickListener(view -> {
          Intent intent = new Intent(MainActivity.this, PracticeMatchActivity.class);
          themeMediaPlayer.pauseMusic();
          startActivity(intent);
      });

        tutorialButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            startActivity(intent);
        });
    }

}


/**
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}*/