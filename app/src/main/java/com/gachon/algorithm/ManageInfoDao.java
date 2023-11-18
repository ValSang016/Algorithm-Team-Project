package com.gachon.algorithm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ManageInfoDao {

    @Insert
    void insert(ManageInfo manageInfo); // 삽입

    @Update
    void updateManageInfo(ManageInfo manageInfo); // 수정

    @Delete
    void deleteManageInfo(ManageInfo manageInfo); // 삭제

    @Query("SELECT * FROM ManageInfo WHERE id = 1")
    ManageInfo getManageInfo();

    @Query("DELETE FROM ManageInfo")
    void deleteAll();

    @Query("SELECT * FROM ManageInfo")
    List<ManageInfo> getAllManageInfo();

}



