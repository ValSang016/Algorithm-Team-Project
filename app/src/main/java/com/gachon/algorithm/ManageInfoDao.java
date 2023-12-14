package com.gachon.algorithm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// This interface defines queries that handle the data stored in ManageInfo.
@Dao
public interface ManageInfoDao {

    @Insert
    void insert(ManageInfo manageInfo); // Insertion operation

    @Update
    void updateManageInfo(ManageInfo manageInfo); // Update operation

    @Delete
    void deleteManageInfo(ManageInfo manageInfo); // Deletion operation

    @Query("SELECT * FROM ManageInfo WHERE id = 1") // Retrieve specific ManageInfo entity
    ManageInfo getManageInfo();

    @Query("DELETE FROM ManageInfo")
    void deleteAll(); // Delete all ManageInfo entities

    @Query("SELECT * FROM ManageInfo")
    List<ManageInfo> getAllManageInfo(); // Retrieve all ManageInfo entities

}



