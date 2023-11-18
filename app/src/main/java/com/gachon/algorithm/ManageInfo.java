package com.gachon.algorithm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ManageInfo {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getCleaningTime() {
        return cleaningTime;
    }

    public void setCleaningTime(int cleaningTime) {
        this.cleaningTime = cleaningTime;
    }

    @PrimaryKey
    private int id;

    private int roomCount;

    private int cleaningTime;

}
