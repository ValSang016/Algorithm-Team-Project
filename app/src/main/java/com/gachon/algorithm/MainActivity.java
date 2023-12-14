package com.gachon.algorithm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_reservation = findViewById(R.id.btn_reservation);
        Button btn_confirm = findViewById(R.id.btn_confirm);
        Button btn_settings = findViewById(R.id.btn_settings);

        // Set click listener for the "Reservation" button
        btn_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to "ReservationPage" on button click
                startActivity(new Intent(MainActivity.this, Activity2.class));
            }
        });

        // Set click listener for the "Confirm reservation" button
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to "ConfirmedReservationPage" on button click
                startActivity(new Intent(MainActivity.this, Activity3.class));
            }
        });

        // Set click listener for the "Manage" button
        btn_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to "ManagementPage" on button click
                startActivity(new Intent(MainActivity.this, Activity4.class));
            }
        });
    }
}
