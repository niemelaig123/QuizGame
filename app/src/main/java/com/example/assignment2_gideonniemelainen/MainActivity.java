// By Gideon Niemelainen W0197064
// This is the intro screen. Will collect username from user and then move to quiz game screen


package com.example.assignment2_gideonniemelainen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Declaring widgets
    Button btnConfirm;
    TextView tvTitle;
    TextView tvPlayerPrompt;
    EditText etPlayerName;
    TextView tvFooter;

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect widgets on load
        btnConfirm = findViewById(R.id.btnConfirm);
        tvTitle = findViewById(R.id.tvTitle);
        tvPlayerPrompt = findViewById(R.id.tvPlayerPrompt);
        etPlayerName = findViewById(R.id.etPlayerName);
        tvFooter = findViewById(R.id.tvFooter);

        // Event listener for confirm button click
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Take name input from this screen and insert it into view in next screen - intent
                try {
                    Intent i = new Intent("Quiz");
                    Bundle name = new Bundle();
                    if (etPlayerName.getText().toString().equals("")) {
                        Toast.makeText(MainActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                    } else if (!etPlayerName.getText().toString().matches("[a-zA-Z]+")) {
                        Toast.makeText(MainActivity.this, "Please Enter Only Letters, We're not Elon Musk fans here", Toast.LENGTH_SHORT).show();
                    } else {
                        name.putString("name", etPlayerName.getText().toString());
                        i.putExtras(name);
                        startActivityForResult(i, 1);
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                    e.printStackTrace();
                }

            }
        }); // end event listener


    }; // end oncreate

} // End main