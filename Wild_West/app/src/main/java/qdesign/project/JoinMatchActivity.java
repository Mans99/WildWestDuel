package qdesign.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import qdesign.project.firebase.match.FirebaseMatch;
import qdesign.project.util.ThemeMediaPlayer;

public class JoinMatchActivity extends AppCompatActivity {

    ImageButton joinMatchButton;
    EditText matchCodeInput;
    TextView errorTextView;

    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_join_match);
        ImageButton returnCreateJoinButton = findViewById(R.id.returnHomeButton);
        joinMatchButton = findViewById(R.id.join_match_button);
        matchCodeInput = findViewById(R.id.match_code_input);
        errorTextView = findViewById(R.id.errorTextView);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        matchCodeInput.setOnKeyListener((v, keyCode, event) -> {
            errorTextView.setText("");
            return false;
        });
        joinMatchButton.setOnClickListener(view -> {
            String matchCode = matchCodeInput.getText().toString();
            if (matchCode.length() == 4){
                final String matchCodeUpper = matchCode.toUpperCase();
                FirebaseMatch
                        .joinMatch(matchCode,
                                () -> {
                                FirebaseMatch.setActiveMatchCode(matchCodeUpper);
                                Intent i = new Intent(getApplicationContext(), MatchActivity.class);
                                startActivity(i);
                            },
                                () -> {
                                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                                    errorTextView.setText("Incorrect Match Code!");
                                });
            } else {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                errorTextView.setText("Code Needs 4 Characters!");
            }
        });
        returnCreateJoinButton.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), CreateOrJoinMatchActivity.class);
            startActivity(i);
        });
    }
}