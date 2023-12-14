package com.gachon.algorithm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class Activity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity5);

        EditText et_roomCount = findViewById(R.id.et_roomCount);
        EditText et_cleaningTime = findViewById(R.id.et_cleaningTime);
        Button btn_save = findViewById(R.id.btn_save);
        Button btn_back = findViewById(R.id.btn_Back);
        Button btn_reset = findViewById(R.id.btn_Reset);

        UserDataBase database = Room.databaseBuilder(getApplicationContext(), UserDataBase.class, "team_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        ManageInfoDao mManageInfoDao = database.manageInfoDao();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int roomCount = Integer.parseInt(et_roomCount.getText().toString());
                int cleaningTime = Integer.parseInt(et_cleaningTime.getText().toString());

                // Create and set values for ManageInfo object
                ManageInfo manageInfo = new ManageInfo();
                manageInfo.setId(1);
                manageInfo.setRoomCount(roomCount);
                manageInfo.setCleaningTime(cleaningTime);

                // Save to the Room database
                mManageInfoDao.insert(manageInfo);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the page
                finish();
            }
        });

        // Click event for the reset button
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset the ManageInfo table in the Room database
                mManageInfoDao.deleteAll();
            }
        });
    }
}
