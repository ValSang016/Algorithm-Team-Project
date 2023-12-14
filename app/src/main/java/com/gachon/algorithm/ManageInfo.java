package com.gachon.algorithm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// This class defines the object unit used to store
// the number of rooms and cleaning time when setting up rooms on the "RoomSettingPage."
@Entity
public class ManageInfo {
    // Getter and setter methods for the entity fields
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

    // Define fields with annotations for Room database
    @PrimaryKey
    private int id;

    private int roomCount;

    private int cleaningTime;

}
