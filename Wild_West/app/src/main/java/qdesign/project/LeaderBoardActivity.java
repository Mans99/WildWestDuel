package qdesign.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import qdesign.project.firebase.leaderboard.FirebaseLeaderboard;
import qdesign.project.firebase.leaderboard.Leaderboard;

public class LeaderBoardActivity extends AppCompatActivity {

    FirebaseLeaderboard fbLeaderBoard = new FirebaseLeaderboard();
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_leader_board);
        tableLayout = findViewById(R.id.leaderBoardTable);
        fbLeaderBoard.getLeaderboardFromDb().addOnCompleteListener(task -> {
            Map<String, ArrayList<Integer>> lb = Leaderboard.LEADERBOARD;
            for (Map.Entry<String, ArrayList<Integer>> entry: lb.entrySet()){
                String name = entry.getKey();
                System.out.println("Username: ");
                System.out.print(name);
                TableRow tbRow = new TableRow(getApplicationContext());
                TextView txt1=new TextView(this);
                txt1.setText(name + ": " + entry.getValue().toString());
                tbRow.addView(txt1);
                tableLayout.addView(tbRow);
            }
        });
    }
}