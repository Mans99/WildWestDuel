package qdesign.project.firebase.leaderboard;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

import qdesign.project.firebase.Firebase;
import qdesign.project.firebase.users.FirebaseUsers;

public class FirebaseLeaderboard {

    private CollectionReference leaderboardRef = Firebase.getInstance().getDb().collection("leaderboard");

    public void updateLeaderBoard() {
        leaderboardRef.document(FirebaseUsers.ACTIVE_USER)
                .update("leaderboard", Leaderboard.getLEADERBOARD())
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Updated leaderboard: " + documentReference.toString()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding user", e));
    }

    public Task<DocumentSnapshot> getLeaderboardFromDb() {
        return leaderboardRef.document(FirebaseUsers.ACTIVE_USER)
                .get()
                .addOnSuccessListener(documentReference -> {
                    Leaderboard.setLEADERBOARD((Map<String, ArrayList<Integer>>) documentReference.get("leaderboard"));
                    System.out.println(Leaderboard.LEADERBOARD);
                    Log.d(TAG, "Added leaderboard: " + documentReference.getId());
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding user", e));
    }
}
