package qdesign.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tutorial);

       /* TextView textView = findViewById(R.id.bigTextView);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "/Wild_West/app/src/main/res/font/crimson.ttf");
        textView.setTypeface(typeface);*/

        ImageButton returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(v -> {
            // Do something when the Return to Home button is clicked
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        });

    }
}