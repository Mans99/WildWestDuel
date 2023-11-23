package qdesign.project.firebase.users;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qdesign.project.firebase.Firebase;

public class FirebaseUsers {
    private CollectionReference usersRef = Firebase.getInstance().getDb().collection("users");
    private List<String> userList = new ArrayList<>();
    public static String ACTIVE_USER = "maks";
    public static String DEFAULT_MATCH_CREATOR = "alice";
    public static String DEFAULT_MATCH_JOINER = "bob";

    public void createUserIfPossible(String username) {
        usersRef
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int count = task.getResult().size();
                        if (count > 0) {
                            Log.d(TAG, "Username already exists");
                            setActiveUser(username);
                        } else {
                            Log.d(TAG, "Username is available");
                            addUser(username);
                        }
                    } else {
                        Log.d(TAG, "Error checking username", task.getException());
                    }
                });
    }


    private void addUser(String username) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        usersRef
                .add(user)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "User added with ID: " + documentReference.getId());
                    setActiveUser(username);
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding user", e);
                    }
                });
    }

    public void setActiveUser(String username){
        ACTIVE_USER = username;
    }
}
