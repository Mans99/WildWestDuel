package qdesign.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import qdesign.project.firebase.match.FirebaseMatch;
import qdesign.project.util.ThemeMediaPlayer;

public class CreateMatchActivity extends AppCompatActivity {

    TextView matchCodeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_match);
        matchCodeTv = findViewById(R.id.matchCodeTextView);
        matchCodeTv.setText(FirebaseMatch.ACTIVE_MATCH_CODE);

        ImageButton returnCreateJoinButton = findViewById(R.id.returnHomeButton);

        returnCreateJoinButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), CreateOrJoinMatchActivity.class);
            startActivity(i);
        });

    }
}