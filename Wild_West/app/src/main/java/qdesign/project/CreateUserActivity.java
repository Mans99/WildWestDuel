package qdesign.project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import qdesign.project.firebase.users.FirebaseUsers;

public class CreateUserActivity extends AppCompatActivity {

    EditText inputText;
    Button submitButton;
    FirebaseUsers fbUsers = new FirebaseUsers();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_user);
        inputText = findViewById(R.id.createUserInput);
        submitButton = findViewById(R.id.createUserSubmitButton);
        submitButtonListener(submitButton);
    }

    private void submitButtonListener(Button button){
        button.setOnClickListener(view -> {
            String username = inputText.getText().toString();
            Log.d("CreateUserActivity", "Clicked submit button with username: " + username);
            fbUsers.createUserIfPossible(username);
        });
    }
}