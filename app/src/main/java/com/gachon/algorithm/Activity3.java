package com.gachon.algorithm;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.gachon.algorithm.User;
import com.gachon.algorithm.UserDao;
import com.gachon.algorithm.UserDataBase;

import java.util.List;

public class Activity3 extends AppCompatActivity {
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);

        UserDataBase database = Room.databaseBuilder(getApplicationContext(), UserDataBase.class, "team_db")
                .fallbackToDestructiveMigration() // 스키마 버전 변경 가능
                .allowMainThreadQueries()   // main thread 에서 db에 IO 가능하게.
                .build();

        mUserDao = database.userDao(); // database 인스턴스를 사용하도록 수정

        // 데이터베이스에서 데이터 비동기적으로 가져오기
        new FetchDataAsyncTask().execute();

        // '뒤로가기' 버튼 클릭 이벤트 리스너 추가
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // '데이터 초기화' 버튼 클릭 이벤트 리스너 추가
        Button btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDao.deleteAll(); // 모든 User 데이터 삭제
                new FetchDataAsyncTask().execute(); // 테이블 업데이트
            }
        });

    }

    private class FetchDataAsyncTask extends AsyncTask<Void, Void, List<User>> {

        @Override
        protected List<User> doInBackground(Void... voids) {
            // 백그라운드에서 데이터베이스에서 데이터 가져오기
            List<User> users = mUserDao.getUserAll();

            // activity-selection 알고리즘을 이용하여 데이터 정렬
            // sortUsers(users);

            return users;
        }

        @Override
        protected void onPostExecute(List<User> userList) {
            super.onPostExecute(userList);

            // TableLayout 가져오기
            TableLayout tableLayout = findViewById(R.id.table_layout);
            tableLayout.removeAllViews(); // 테이블 초기화

            // 데이터를 이용하여 동적으로 테이블 생성
            for (User user : userList) {
                TableRow row = new TableRow(Activity3.this);

                // 사용자 정보를 표시할 TextView들 생성
                TextView userGroupNameTextView = new TextView(Activity3.this);
                userGroupNameTextView.setText(user.getGroup_name());
                userGroupNameTextView.setGravity(Gravity.CENTER);
                userGroupNameTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                userGroupNameTextView.setTextColor(Color.WHITE);

                TextView userStartTimeTextView = new TextView(Activity3.this);
                userStartTimeTextView.setText(formatTime(user.getStart_hour(), user.getStart_minute())); // 수정된 부분
                userStartTimeTextView.setGravity(Gravity.CENTER);
                userStartTimeTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                userStartTimeTextView.setTextColor(Color.WHITE);

                TextView userEndTimeTextView = new TextView(Activity3.this);
                userEndTimeTextView.setText(formatTime(user.getEnd_hour(), user.getEnd_minute())); // 수정된 부분
                userEndTimeTextView.setGravity(Gravity.CENTER);
                userEndTimeTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                userEndTimeTextView.setTextColor(Color.WHITE);

                // 각 행에 TextView 추가
                row.addView(userGroupNameTextView);
                row.addView(userStartTimeTextView);
                row.addView(userEndTimeTextView);

                // 테이블에 행 추가
                tableLayout.addView(row);
            }
        }

        private String formatTime(int hours, int minutes) {
            return String.format("%02d:%02d", hours, minutes);
        }
    }
}
