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
        Button btn_reset = findViewById(R.id.btn_Reset); // 초기화 버튼 추가

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

                // ManageInfo 객체 생성 및 값 설정
                ManageInfo manageInfo = new ManageInfo();
                manageInfo.setId(1);
                manageInfo.setRoomCount(roomCount);
                manageInfo.setCleaningTime(cleaningTime);

                // Room 데이터베이스에 저장
                mManageInfoDao.insert(manageInfo);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 초기화 버튼 클릭 이벤트
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Room 데이터베이스의 ManageInfo 테이블 초기화
                mManageInfoDao.deleteAll();
            }
        });
    }
}
