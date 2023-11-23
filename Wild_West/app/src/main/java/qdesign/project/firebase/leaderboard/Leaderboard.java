package qdesign.project.firebase.leaderboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Leaderboard {
    public static Map<String, ArrayList<Integer>> LEADERBOARD = new HashMap<>();

    public Map<String, ArrayList<Integer>> getLeaderBoard(){
        return LEADERBOARD;
    }

    public static void setLEADERBOARD(Map<String, ArrayList<Integer>> LEADERBOARD) {
        Leaderboard.LEADERBOARD = LEADERBOARD;
    }

    public static Map<String, ArrayList<Integer>> getLEADERBOARD() {
        return LEADERBOARD;
    }

    public static void updateLeaderboard(String username, Boolean win){
        ArrayList<Integer> score = LEADERBOARD.get(username);
        if (win){
            score.set(0, score.get(0) + 1);
        } {
            score.set(1, score.get(1) + 1);
        }
        LEADERBOARD.put(username, score);
    }
}
