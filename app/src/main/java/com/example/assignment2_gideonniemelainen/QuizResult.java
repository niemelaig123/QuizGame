package com.example.assignment2_gideonniemelainen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class QuizResult extends AppCompatActivity {

    int score = 0;

    Button btnPlayAgain;
    Button btnQuit;
    TextView tvMessage;
    TextView tvName;
    TextView tvFinalScore;

    String TAG = "QuizResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnQuit = findViewById(R.id.btnQuit);
        tvMessage = findViewById(R.id.tvMessage);
        tvName = findViewById(R.id.tvName);
        tvFinalScore = findViewById(R.id.tvFinalScore);

    } // end oncreate

    protected void onStart() {
        super.onStart();
        // Collect info from intent
        Bundle extras = getIntent().getExtras();
        try {
            if(extras != null) {
                // Get name from intent
                String s = extras.getString("name");
                score = extras.getInt("score");
                String scoreString = "Score: " + Integer.toString(score);
                // Display player results
                String mess = "";
                if (score > 5) {
                    mess = "Congratulations, you win!";
                }
                if (score == 5) {
                    mess = "Okay, I guess. ¯\\_(ツ)_/¯";
                }
                if (score < 5) {
                    mess = "Whomp Whomp, try again!";
                }
                tvMessage.setText(mess); // I tried to figure out how to use the strings resource to act on this but I couldn't get it
                tvName.setText(s);
                tvFinalScore.setText(scoreString);
            } // end if
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            Log.w(TAG, "There was an error in loading the quiz result.");
        }

        // Setting up click listeners
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(QuizResult.this, Quiz.class);
                    Bundle name = new Bundle();
                    name.putString("name", tvName.getText().toString());
                    i.putExtras(name);
                    startActivityForResult(i, 1);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(QuizResult.this, MainActivity.class);
                    startActivity(i);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }

            }
        });

    }
}