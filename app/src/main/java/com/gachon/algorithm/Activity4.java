package com.gachon.algorithm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity4 extends AppCompatActivity {

    private Button btn_test1_back;
    private Button btn_test1_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4);

        // Set click listener for the "Setting" button
        btn_test1_set=findViewById(R.id.btn_test1_set);
        btn_test1_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to "RoomSettingPage" on button click
                Intent intent=new Intent(Activity4.this, Activity5.class);
                startActivity(intent);
            }
        });

        // Set click listener for the "Back" button
        btn_test1_back=findViewById(R.id.btn_test1_back);
        btn_test1_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect back to "MainPage" on button click
                Intent intent=new Intent(Activity4.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
