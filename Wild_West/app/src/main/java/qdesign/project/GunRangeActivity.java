package qdesign.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GunRangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gun_range);
        Intent i = new Intent(getApplicationContext(), PracticeMatchActivity.class);
        startActivity(i);
    }
    // Ändra i PracticeMatchActivity när vilka sensorer som ska användas är bestämt
}