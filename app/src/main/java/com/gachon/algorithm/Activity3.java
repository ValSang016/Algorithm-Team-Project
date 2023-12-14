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


        mUserDao = database.userDao(); // Modify to use the database instance
        mManageInfoDao = database.manageInfoDao();

        // Asynchronously fetch data from the database
        new FetchDataAsyncTask(mManageInfoDao.getManageInfo()).execute();

        // Add click event listener for the 'Back' button
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Add click event listener for the 'Reset Data' button
        Button btnReset = findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserDao.deleteAll(); // Delete all User data
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
            // Fetch data from the database in the background
            List<User> users = mUserDao.getUserAll();

            // Apply an algorithm to sort based on end time and select the case with the most reserved time
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
                addTableRow(cancelledTableLayout, 0, user.getGroup_name(), formatTime(user.getStart_hour(), user.getStart_minute()), formatTime(user.getEnd_hour(), user.getEnd_minute()));
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

            // Adding a new column
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

        // Algorithm to sort based on end time and select the case with the most reserved time
        private List<User> sortUsers(List<User> users) {
            List<User> finalReservation = new ArrayList<>(); // Final reservation list
            List<User> currentUsers = new ArrayList<>(users); // List of currently available users

            // Iterate through each room
            for (int i = 0; i < manageInfo.getRoomCount(); i++) {

                // Sort currentUsers based on end time
                Collections.sort(currentUsers, new Comparator<User>() {
                    @Override
                    public int compare(User u1, User u2) {
                        return (u1.getEnd_hour() * 60 + u1.getEnd_minute()) - (u2.getEnd_hour() * 60 + u2.getEnd_minute());
                    }
                });

                List<List<User>> subsets = new ArrayList<>();
                subsets.add(new ArrayList<>());

                // For each currently available user
                for (User user : currentUsers) {
                    List<List<User>> newSubsets = new ArrayList<>();

                    // Activity selection
                    // For each existing subset
                    for (List<User> subset : subsets) {
                        // If the subset is empty or if the end time of the last reservation + cleaning time is before the start time of the user
                        // Create a new subset and add the user
                        if (subset.isEmpty() || (subset.get(subset.size() - 1).getEnd_hour() * 60 + subset.get(subset.size() - 1).getEnd_minute() + manageInfo.getCleaningTime()) <= (user.getStart_hour() * 60 + user.getStart_minute())) {
                            List<User> newSubset = new ArrayList<>(subset);
                            newSubset.add(user);
                            newSubsets.add(newSubset);
                        }
                    }

                    // Add all newly created subsets
                    subsets.addAll(newSubsets);
                }

                List<User> maxSubset = null;
                int maxTime = 0;

                // For each subset
                for (List<User> subset : subsets) {
                    int time = 0;

                    // Calculate time for all reservations in the subset
                    for (User user : subset) {
                        time += (user.getEnd_hour() * 60 + user.getEnd_minute()) - (user.getStart_hour() * 60 + user.getStart_minute());
                    }

                    // If the calculated time is greater than the max time, update the max time and max subset
                    if (time > maxTime) {
                        maxTime = time;
                        maxSubset = subset;
                    }
                }

                // If max subset is not null, set room numbers for each user and add to the final reservation list
                if (maxSubset != null) {
                    for (User user : maxSubset) {
                        user.setRoomNumber(i + 1); // Set room number
                    }
                    finalReservation.addAll(maxSubset);
                    currentUsers.removeAll(maxSubset); // Remove users in the max subset from currentUsers
                }
            }

            cancelledUsers = currentUsers; // Add unprocessed reservations to the cancelled reservations list
            return finalReservation; // Return the final reservation list

        }
    }
}
