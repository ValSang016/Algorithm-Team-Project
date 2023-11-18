package com.gachon.algorithm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Activity4 extends AppCompatActivity {

    private Button btn_test1_back;
    private Button btn_test1_set;
    private String roomNum;
    private String cleanTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4);

        btn_test1_set=findViewById(R.id.btn_test1_set);
        btn_test1_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity4.this, Activity5.class);
                startActivity(intent);
            }
        });

        btn_test1_back=findViewById(R.id.btn_test1_back);
        btn_test1_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity4.this, MainActivity.class);
                startActivity(intent);//액티비티 이동
            }
        });

        Intent intent=getIntent();
        intent.getStringExtra("roomNum");
        intent.getStringExtra("cleanTime");


    }
}