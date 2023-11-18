package com.gachon.algorithm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Activity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity5);

        EditText et_roomCount = findViewById(R.id.et_roomCount);
        EditText et_cleaningTime = findViewById(R.id.et_cleaningTime);
        Button btn_roomCount = findViewById(R.id.btn_roomCount);
        Button btn_cleaningTime = findViewById(R.id.btn_cleaningTime);
        Button btn_back = findViewById(R.id.btn_Back);


        btn_cleaningTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cleaningTime = Integer.parseInt(et_cleaningTime.getText().toString());

                // Room 데이터베이스에 저장하는 코드
                // saveToDatabase(roomCount, cleaningTime);
            }
        });

        btn_roomCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int roomCount = Integer.parseInt(et_roomCount.getText().toString());

                // Room 데이터베이스에 저장하는 코드
                // saveToDatabase(roomCount, cleaningTime);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
