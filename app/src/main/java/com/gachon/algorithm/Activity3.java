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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Activity3 extends AppCompatActivity {
    private UserDao mUserDao;
    private ManageInfoDao mManageInfoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);

        UserDataBase database = Room.databaseBuilder(getApplicationContext(), UserDataBase.class, "team_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();


        mUserDao = database.userDao(); // database 인스턴스를 사용하도록 수정
        mManageInfoDao = database.manageInfoDao();

        // 데이터베이스에서 데이터 비동기적으로 가져오기
        new FetchDataAsyncTask(mManageInfoDao.getManageInfo()).execute();

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
                new FetchDataAsyncTask(mManageInfoDao.getManageInfo()).execute();
            }
        });

    }

    private class FetchDataAsyncTask extends AsyncTask<Void, Void, List<User>> {
        private List<User> cancelledUsers;
        private ManageInfo manageInfo;

        public FetchDataAsyncTask(ManageInfo manageInfo) {
            this.manageInfo = manageInfo;
        }
        @Override
        protected List<User> doInBackground(Void... voids) {
            // 백그라운드에서 데이터베이스에서 데이터 가져오기
            List<User> users = mUserDao.getUserAll();

            // 종료 시간을 기준으로 정렬하고, 가장 많은 예약 시간을 갖는 경우를 선택하는 알고리즘을 적용
            List<User> sortedUsers = sortUsers(users);

            return sortedUsers;
        }

        @Override
        protected void onPostExecute(List<User> userList) {
            super.onPostExecute(userList);

            TableLayout tableLayout = findViewById(R.id.table_layout);
            tableLayout.removeAllViews();

            addTableHeader(tableLayout, "Room Number", "Group Name", "Start Time", "End Time");

            for (User user : userList) {
                addTableRow(tableLayout, user.getRoomNumber(), user.getGroup_name(), formatTime(user.getStart_hour(), user.getStart_minute()), formatTime(user.getEnd_hour(), user.getEnd_minute()));
            }

            TableLayout cancelledTableLayout = findViewById(R.id.cancelled_table_layout);
            cancelledTableLayout.removeAllViews();

            addTableHeader(cancelledTableLayout, "Room Number", "Cancelled Group Name", "Start Time", "End Time");

            for (User user : cancelledUsers) {
                addTableRow(cancelledTableLayout,0, user.getGroup_name(), formatTime(user.getStart_hour(), user.getStart_minute()), formatTime(user.getEnd_hour(), user.getEnd_minute()));
            }
        }


        private void addTableHeader(TableLayout tableLayout, String... headers) {
            TableRow headerRow = new TableRow(Activity3.this);
            headerRow.setBackgroundColor(Color.GRAY);


            for (String header : headers) {
                TextView headerTextView = new TextView(Activity3.this);
                headerTextView.setText(header);
                headerTextView.setGravity(Gravity.CENTER);
                headerTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                headerTextView.setTextColor(Color.WHITE);

                headerRow.addView(headerTextView);
            }

            tableLayout.addView(headerRow);
        }

        private void addTableRow(TableLayout tableLayout, int index, String... values) {
            TableRow row = new TableRow(Activity3.this);

            // 새로운 column 추가
            TextView indexTextView = new TextView(Activity3.this);
            indexTextView.setText(String.valueOf(index));
            indexTextView.setGravity(Gravity.CENTER);
            indexTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            indexTextView.setTextColor(Color.BLACK);

            row.addView(indexTextView);

            for (String value : values) {
                TextView valueTextView = new TextView(Activity3.this);
                valueTextView.setText(value);
                valueTextView.setGravity(Gravity.CENTER);
                valueTextView.setLayoutParams(new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                valueTextView.setTextColor(Color.BLACK);

                row.addView(valueTextView);
            }

            tableLayout.addView(row);
        }


        private String formatTime(int hours, int minutes) {
            return String.format("%02d:%02d", hours, minutes);
        }

        // 종료 시간을 기준으로 정렬하고, 가장 많은 예약 시간을 갖는 경우를 선택하는 알고리즘
        private List<User> sortUsers(List<User> users) {
            List<User> finalReservation = new ArrayList<>();
            List<User> currentUsers = new ArrayList<>(users);

            for (int i = 0; i < manageInfo.getRoomCount(); i++) {
                Collections.sort(currentUsers, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return (u1.getEnd_hour() * 60 + u1.getEnd_minute()) - (u2.getEnd_hour() * 60 + u2.getEnd_minute());
                    }
                });

                List<List<User>> subsets = new ArrayList<>();
                subsets.add(new ArrayList<>());

                for (User user : currentUsers) {
                    List<List<User>> newSubsets = new ArrayList<>();
                    for (List<User> subset : subsets) {
                        if (subset.isEmpty() || (subset.get(subset.size() - 1).getEnd_hour() * 60 + subset.get(subset.size() - 1).getEnd_minute() + manageInfo.getCleaningTime()) <= (user.getStart_hour() * 60 + user.getStart_minute())) {
                            List<User> newSubset = new ArrayList<>(subset);
                            newSubset.add(user);
                            newSubsets.add(newSubset);
                        }
                    }
                    subsets.addAll(newSubsets);
                }

                List<User> maxSubset = null;
                int maxTime = 0;
                for (List<User> subset : subsets) {
                    int time = 0;
                    for (User user : subset) {
                        time += (user.getEnd_hour() * 60 + user.getEnd_minute()) - (user.getStart_hour() * 60 + user.getStart_minute());
                    }
                    if (time > maxTime) {
                        maxTime = time;
                        maxSubset = subset;
                    }
                }

                if (maxSubset != null) {
                    for (User user : maxSubset) {
                        user.setRoomNumber(i + 1); // 방 번호 설정
                    }
                    finalReservation.addAll(maxSubset);
                    currentUsers.removeAll(maxSubset);
                }
            }
            cancelledUsers = currentUsers;
            return finalReservation;
        }

    }
}