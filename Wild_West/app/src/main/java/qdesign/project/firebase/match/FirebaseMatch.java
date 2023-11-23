package qdesign.project.firebase.match;

import static android.content.ContentValues.TAG;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

import qdesign.project.firebase.Firebase;
import qdesign.project.firebase.users.FirebaseUsers;
import qdesign.project.util.Timestamps;

public class FirebaseMatch {

    private static final CollectionReference matchRef = Firebase.getInstance().getDb().collection("match");
    public static String ACTIVE_MATCH_CODE = "TEST";
    public static Long ACTIVE_MATCH_TIME = null;

    public static int PLAYER = 1;
    public static Long PLAYER_TIME = 0L;
    public static Long OTHER_PLAYER_TIME = 0L;

    public static Long RANDOM_TIME = null;
    public static void setActiveMatchCode(String matchCode){
        ACTIVE_MATCH_CODE = matchCode;
    }

    public static String getOtherPlayerTime(){
        return "player_" + (PLAYER % 2 + 1) + "_time";
    }

    public static String generateMatchString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 4;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString()
                .toUpperCase();
    }


    public static void createMatch(String matchCode, Runnable onCreateSuccess, Runnable onJoinSuccess, Runnable onFailure) {
        Map<String, Object> match = new HashMap<>();
        match.put("match_code", matchCode);
        match.put("player_one", FirebaseUsers.DEFAULT_MATCH_CREATOR);
        match.put("player_two_joined", false);
        match.put("match_complete", false);
        PLAYER = 1;
        matchRef
                .add(match)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "Match added with match code: " + matchCode);
                    setActiveMatchCode(matchCode);
                    awaitPlayer(onJoinSuccess);
                    onCreateSuccess.run();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding match", e);
                    onFailure.run();
                });
    }

    public static void setPlayerTime(Long playerTime) {
       matchRef
               .whereEqualTo("match_code", ACTIVE_MATCH_CODE)
               .get()
               .addOnCompleteListener(res -> {
                   QuerySnapshot querySnapshot = res.getResult();
                   DocumentSnapshot matchSnapshot = querySnapshot.getDocuments().get(0);
                   matchSnapshot
                           .getReference()
                           .update("player_" + PLAYER + "_time", playerTime,"match_complete", true);
               });
    }

    public static void joinMatch(String matchCode, Runnable onSuccess, Runnable onFailure){
            matchRef
                    .whereEqualTo("match_code", matchCode)
                    .get()
                    .addOnCompleteListener(res1 -> {
                        try{
                            QuerySnapshot querySnapshot = res1.getResult();
                            DocumentSnapshot matchSnapshot = querySnapshot.getDocuments().get(0);
                            Long timestamp = System.currentTimeMillis();
                            ACTIVE_MATCH_TIME = timestamp + 5000;
                            Random rand = new Random();
                            int randomTime = rand.nextInt(6) + 15;
                            RANDOM_TIME = (long) randomTime;
                            PLAYER = 2;
                            matchSnapshot
                                    .getReference()
                                    .update("player_two", FirebaseUsers.DEFAULT_MATCH_JOINER, "player_two_joined", true, "game_time", ACTIVE_MATCH_TIME, "random_time", randomTime);
                            onSuccess.run();
                        } catch(Exception e){
                            onFailure.run();
                        }
                    });
    }

    public static void awaitPlayer(Runnable onSuccess){
        matchRef.whereEqualTo("match_code", ACTIVE_MATCH_CODE).limit(1).addSnapshotListener(
                (snapshot, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if (snapshot != null) {
                        QueryDocumentSnapshot qds = snapshot.getDocumentChanges().get(0).getDocument();
                        Boolean playerTwoJoined = (Boolean) qds.get("player_two_joined");
                        Boolean matchComplete = (Boolean) qds.get("match_complete");
                        ACTIVE_MATCH_TIME = (Long) qds.get("game_time");
                        RANDOM_TIME = (Long) qds.get("random_time");
                        /*if (ACTIVE_MATCH_TIME != null){
                            ACTIVE_MATCH_TIME -= 400L;
                        }*/
                        if (Boolean.TRUE.equals(playerTwoJoined) && Boolean.FALSE.equals(matchComplete)){
                            onSuccess.run();
                        }
                    } else {
                        Log.d(TAG, "Current data: null");
                    }
                }
        );
    }

    public static void seeMatchResult(Runnable onSuccess){
        matchRef.whereEqualTo("match_code", ACTIVE_MATCH_CODE).limit(1).addSnapshotListener(
                (snapshot, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    if (snapshot != null) {
                        QueryDocumentSnapshot qds = snapshot.getDocumentChanges().get(0).getDocument();
                        System.out.println(getOtherPlayerTime());
                        OTHER_PLAYER_TIME = (Long) qds.get(getOtherPlayerTime());;
                        if (OTHER_PLAYER_TIME != null){
                            System.out.println("OTHER PLAYER TIME: " + OTHER_PLAYER_TIME);
                            System.out.println("MY TIME: " + PLAYER_TIME);
                            onSuccess.run();
                        }
                    } else {
                        Log.d(TAG, "Current data: null");
                    }
                }
        );
    }
}
