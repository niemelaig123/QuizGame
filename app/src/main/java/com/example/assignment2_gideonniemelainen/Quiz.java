// By Gideon Niemelainen W0197064
// This is the Quiz Game screen itself. Will be responsible for the views and setting text and the like, logic elsewhere


package com.example.assignment2_gideonniemelainen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Quiz extends AppCompatActivity {
    // Game needs two array lists to work with
    ArrayList<String> terms = new ArrayList<>();
    ArrayList<String> definitions = new ArrayList<>();
    Map<String, String> quizMap = new HashMap<String, String>();

    int playerScore = 0;
    int roundCount = 0;
    String cor = "";
    int chances = 0; // You get two attempts to pick the correct term

    // Initialize buttons and views
    TextView tvPlayerName;
    TextView tvPlayerScore;
    TextView tvQTerm;
    TextView tvRoundCount;
    Button btnA1Ans;
    Button btnA2Ans;
    Button btnA3Ans;
    Button btnA4Ans;

    String TAG = "Quiz";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizscreen);

        // Hook up buttons and views
        tvPlayerName = findViewById(R.id.tvPlayerName);
        tvPlayerScore = findViewById(R.id.tvPlayerScore);
        tvQTerm = findViewById(R.id.tvQTerm);
        tvRoundCount = findViewById(R.id.tvRoundCount);
        btnA1Ans = findViewById(R.id.btnA1Ans);
        btnA2Ans = findViewById(R.id.btnA2Ans);;
        btnA3Ans = findViewById(R.id.btnA3Ans);;
        btnA4Ans = findViewById(R.id.btnA4Ans);;
        try {
            // Collect info from intent
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                // Get name from intent
                String s = extras.getString("name");
                // Display player name
                tvPlayerName.setText(s);
                tvPlayerScore.setText("Score: 0");
                String roundString = "Round: " + Integer.toString(roundCount+1);
                tvRoundCount.setText(roundString);
            } // end if

            // Gettin that info
            openFile();
            makeMap();
            // shuffle terms
            Collections.shuffle(terms);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


        // Commencing the game

        // showMap(); This is just a diagnostic function

    } // End oncreate

    protected void onStart() {

        super.onStart();
        try {
            cor = terms.get(roundCount);
            makeRound(roundCount);
            // button click listeners to drive the game
            btnA1Ans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
          /*      System.out.println("The correct Definition is " + quizMap.get(cor)); Helpful diagnostic print
                System.out.println("The text in this button is " + btnA1Ans.getText().toString());*/
                    if (btnA1Ans.getText().toString().equals(quizMap.get(cor))) {
                        Toast.makeText(Quiz.this, "Hurray! Look how smart you are!", Toast.LENGTH_SHORT).show();
                        nextRound();
                    } else {
                        btnA1Ans.setAlpha(.5f);
                        btnA1Ans.setClickable(false);
                        chances += 1;
                        if (chances == 2) {
                            skipRound();
                        }
                    }
                }
            });
            btnA2Ans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnA2Ans.getText().toString().equals(quizMap.get(cor))) {
                        Toast.makeText(Quiz.this, "Hurray! Look how smart you are!", Toast.LENGTH_SHORT).show();
                        nextRound();
                    } else {
                        btnA2Ans.setAlpha(.5f);
                        btnA2Ans.setClickable(false);
                        chances += 1;
                        if (chances == 2) {
                            skipRound();
                        }
                    }
                }
            });
            btnA3Ans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnA3Ans.getText().toString().equals(quizMap.get(cor))) {
                        Toast.makeText(Quiz.this, "Hurray! Look how smart you are!", Toast.LENGTH_SHORT).show();
                        nextRound();
                    } else {
                        btnA3Ans.setAlpha(.5f);
                        btnA3Ans.setClickable(false);
                        chances += 1;
                        if (chances == 2) {
                            skipRound();
                        }
                    }
                }
            });
            btnA4Ans.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btnA4Ans.getText().toString().equals(quizMap.get(cor))) {
                        Toast.makeText(Quiz.this, "Hurray! Look how smart you are!", Toast.LENGTH_SHORT).show();
                        nextRound();
                    } else {
                        btnA4Ans.setAlpha(.5f);
                        btnA4Ans.setClickable(false);
                        chances += 1;
                        if (chances == 2) {
                            skipRound();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    } // end onStart

    // For moving to next round when successful
    private void nextRound() {
        playerScore += 1;
        String newScore = Integer.toString(playerScore);
        tvPlayerScore.setText("Score: " + newScore);
        roundCount += 1;
        if (roundCount == 10) { // at this point we have gone through the entire list, time to end
            endGame();
        } else {
            makeRound(roundCount);
            cor = terms.get(roundCount);
        }

    } // end nextRound

    // To send player to result screen
    private void endGame() {
        try {
            Intent i = new Intent(Quiz.this, QuizResult.class);
            Bundle extras = new Bundle();
            extras.putString("name", tvPlayerName.getText().toString());
            extras.putInt("score", playerScore);
            i.putExtras(extras);
            roundCount = 0;
            playerScore = 0;
            cor = "";
            startActivityForResult(i, 1);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }

    } // end endGame

    // For moving to next round if answer is not correct
    private void skipRound() {
        roundCount += 1;
        if (roundCount == 10) { // at this point we have gone through the entire list, time to end
            endGame();
        } else {
            makeRound(roundCount);
            cor = terms.get(roundCount);
        }
        chances = 0; // Resetting chance counter for new round
        Toast.makeText(Quiz.this, "Oof, better luck next time", Toast.LENGTH_SHORT).show(); // Just to taunt you a bit
    } // end skipRound

    // Populating text view with term and buttons with answers
    private void makeRound(int i) {
        chances = 0;
        System.out.println("The current round is " + roundCount);
        // set textview to show term
        String term = terms.get(i).toString();
        // Update round counter
        String roundString = "Round: " + Integer.toString(roundCount+1);
        tvRoundCount.setText(roundString);
        //System.out.println("The selected term is " + term);
        tvQTerm.setText(term);

       // Re-enable any disabled buttons
        btnA1Ans.setClickable(true);
        btnA1Ans.setAlpha(1);
        btnA2Ans.setClickable(true);
        btnA2Ans.setAlpha(1);
        btnA3Ans.setClickable(true);
        btnA3Ans.setAlpha(1);
        btnA4Ans.setClickable(true);
        btnA4Ans.setAlpha(1);

        // Display 4 potential clickable answers
        Collections.shuffle(definitions);
        btnA1Ans.setText(definitions.get(0));
        btnA2Ans.setText(definitions.get(1));
        btnA3Ans.setText(definitions.get(2));
        btnA4Ans.setText(definitions.get(3));

        // if not already in sequence, randomly insert correct answer verifying using hashmap
        String cor = quizMap.get(term);
        if (!btnA1Ans.getText().toString().equals(cor) &&
            !btnA2Ans.getText().toString().equals(cor) &&
            !btnA3Ans.getText().toString().equals(cor) &&
            !btnA4Ans.getText().toString().equals(cor)) {
                int ran = (int) (Math.floor((Math.random() * 3) + 1));
                switch(ran){
                    case 1:
                        btnA1Ans.setText(cor);
                        break;
                    case 2:
                        btnA2Ans.setText(cor);
                        break;
                    case 3:
                        btnA3Ans.setText(cor);
                        break;
                    case 4:
                        btnA4Ans.setText(cor);
                        break;
                }
        }
    } // end MakeRound

    // Handles the file IO, and building out arraylists as needed
    private void openFile() {
        // Reads from file into lists
        String str;
        BufferedReader br;
        try {
            InputStream is = getResources().openRawResource(R.raw.defs);
            br = new BufferedReader(new InputStreamReader(is));
            System.out.println("File in RAW is open");
            // Read simple text file using console
            while ((str = br.readLine()) != null) {

                // splitting input from file
                String[] split = str.split(":");

                // Splitting terms and definitions into proper lists
                terms.add(split[0]);
                //System.out.println(split[0]); Just some helpful diagnostic tools
                definitions.add(split[1].trim());
               // System.out.println(split[1].trim());
            }
            System.out.println("File in RAW is closed.");
            is.close();
        } catch (IOException e) {
            String msg = e.getMessage();
            Log.w(TAG, msg);
        } catch (Exception e) {
            String msg = e.getMessage();
            Log.w(TAG, msg);
            e.printStackTrace();
        }
    } // End openFile

    private void makeMap() {
        // Populating hashmap member with appropriate variables
        for (int i = 0; i < 10; i++) {
            quizMap.put(terms.get(i), definitions.get(i));
        }
    } // end makeMap

    private void showMap() {
        // This is primarily just a diagnostic helper function for showing values
        Iterator it = quizMap.values().iterator();
        Iterator termsIt = quizMap.keySet().iterator();
        while (it.hasNext()) {
            String text = it.next().toString();
            System.out.println(text);
        }
        while (termsIt.hasNext()) {
            String text = termsIt.next().toString();
            System.out.println(text);
        }
    } // end showMap
}; // End Main


