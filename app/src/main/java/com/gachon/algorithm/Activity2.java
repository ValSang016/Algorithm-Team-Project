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

        // Finding the EditText views and Button
        final EditText et_groupName = findViewById(R.id.et_groupName);
        final EditText et_startTime = findViewById(R.id.et_startTime);
        final EditText et_endTime = findViewById(R.id.et_endTime);
        Button btn_save = findViewById(R.id.btn_save);

        // Building the Room database
        UserDataBase database = Room.databaseBuilder(getApplicationContext(), UserDataBase.class, "team_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        mUserDao = database.userDao();

        // Setting a click listener for the save button
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting input values
                String groupName = et_groupName.getText().toString();
                if (groupName.isEmpty()) {
                    // Checking if group name is empty
                    Toast.makeText(getApplicationContext(), "Please enter a group name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Checking for duplicate group names
                User existingUser = mUserDao.findUserByGroupName(groupName);
                if (existingUser != null) {
                    Toast.makeText(getApplicationContext(), "Group name already exists.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Parsing start and end time from input strings
                    int[] startTime = parseTime(et_startTime.getText().toString());
                    int[] endTime = parseTime(et_endTime.getText().toString());

                    // Validating business hours
                    if (startTime[0] < 6 || endTime[0] > 23) {
                        Toast.makeText(getApplicationContext(), "Business hours are from 06:00 to 23:00.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Calculating time in minutes
                    int startTimeInMinutes = startTime[0] * 60 + startTime[1];
                    int endTimeInMinutes = endTime[0] * 60 + endTime[1];

                    // Checking time constraints
                    if (startTimeInMinutes >= endTimeInMinutes) {
                        Toast.makeText(getApplicationContext(), "Start time must be before end time.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (endTimeInMinutes - startTimeInMinutes < 60) {
                        Toast.makeText(getApplicationContext(), "Minimum booking time is 1 hour.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (endTimeInMinutes - startTimeInMinutes > 240) {
                        Toast.makeText(getApplicationContext(), "Maximum rental time is 4 hours.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Creating a new User object and inserting it into the database
                    User user = new User();
                    user.setGroup_name(groupName);
                    user.setStart_hour(startTime[0]);
                    user.setStart_minute(startTime[1]);
                    user.setEnd_hour(endTime[0]);
                    user.setEnd_minute(endTime[1]);

                    mUserDao.insert(user);
                    Toast.makeText(getApplicationContext(), "Data saved successfully.", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    Toast.makeText(getApplicationContext(), "Please enter time in 'hour:minute' format.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Setting a click listener for the back button
        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Method to parse a string in "hour:minute" format into hours and minutes
    private int[] parseTime(String timeStr) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        String[] parts = timeStr.split(":");
        int[] time = new int[2];
        time[0] = Integer.parseInt(parts[0]); // Hour
        time[1] = Integer.parseInt(parts[1]); // Minute
        return time;
    }
}
