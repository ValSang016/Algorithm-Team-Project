package com.gachon.algorithm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
// This class defines the object unit used to store reservation information inputted in "Reservation."
@Entity
public class User {
    @PrimaryKey(autoGenerate =true)
    private int n=0; // 데이터 개수

    private String group_name;
    private int Start_hour;
    private int Start_minute;
    private int End_hour;
    private int End_minute;

    private int roomNumber; // 새로운 필드


    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getStart_hour() {
        return Start_hour;
    }

    public int getStart_minute() {
        return Start_minute;
    }

    public int getEnd_hour() {
        return End_hour;
    }

    public int getEnd_minute() {
        return End_minute;
    }
    public void setStart_hour(int start_hour) {
        Start_hour = start_hour;
    }

    public void setStart_minute(int start_minute) {
        Start_minute = start_minute;
    }

    public void setEnd_hour(int end_hour) {
        End_hour = end_hour;
    }

    public void setEnd_minute(int end_minute) {
        End_minute = end_minute;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
