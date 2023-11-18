package com.gachon.algorithm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class Activity2 extends AppCompatActivity {
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        final EditText et_groupName = findViewById(R.id.et_groupName);
        final EditText et_startTime = findViewById(R.id.et_startTime);
        final EditText et_endTime = findViewById(R.id.et_endTime);
        Button btn_save = findViewById(R.id.btn_save);

        UserDataBase database = Room.databaseBuilder(getApplicationContext(), UserDataBase.class, "team_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        mUserDao = database.userDao();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String groupName = et_groupName.getText().toString();
                if (groupName.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "그룹 이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 중복 그룹 이름 체크
                User existingUser = mUserDao.findUserByGroupName(groupName);
                if (existingUser != null) {
                    Toast.makeText(getApplicationContext(), "이미 존재하는 그룹 이름입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int[] startTime = parseTime(et_startTime.getText().toString());
                    int[] endTime = parseTime(et_endTime.getText().toString());

                    if (startTime[0] < 6 || endTime[0] > 23) {
                        Toast.makeText(getApplicationContext(), "영업시간은 06시부터 23시까지입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int startTimeInMinutes = startTime[0] * 60 + startTime[1];
                    int endTimeInMinutes = endTime[0] * 60 + endTime[1];

                    if (startTimeInMinutes >= endTimeInMinutes) {
                        Toast.makeText(getApplicationContext(), "시작 시간은 종료 시간보다 이전이어야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (endTimeInMinutes - startTimeInMinutes < 60) {
                        Toast.makeText(getApplicationContext(), "최소 신청 시간은 1시간입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (endTimeInMinutes - startTimeInMinutes > 240) {
                        Toast.makeText(getApplicationContext(), "최대 대여시간은 4시간입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    User user = new User();
                    user.setGroup_name(groupName);
                    user.setStart_hour(startTime[0]);
                    user.setStart_minute(startTime[1]);
                    user.setEnd_hour(endTime[0]);
                    user.setEnd_minute(endTime[1]);

                    mUserDao.insert(user);
                    Toast.makeText(getApplicationContext(), "데이터가 성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    Toast.makeText(getApplicationContext(), "시간을 '시:분' 형식으로 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // "시:분" 형식의 문자열을 시와 분으로 분리하는 메소드
    private int[] parseTime(String timeStr) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        String[] parts = timeStr.split(":");
        int[] time = new int[2];
        time[0] = Integer.parseInt(parts[0]); // 시
        time[1] = Integer.parseInt(parts[1]); // 분
        return time;
    }

}
