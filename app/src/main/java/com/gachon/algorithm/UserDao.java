package com.gachon.algorithm;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// This interface defines queries that handle the data stored in UserInfo
@Dao
public interface UserDao {
    @Insert
    void setInsertUser(User user);

    @Update
    void setUpdateUser(User user);

    @Delete
    void setDeleteUser(User user);

    @Query("SELECT * FROM User")
    List<User> getUserAll();

    @Query("DELETE FROM User")
    void deleteAll();

    @Insert
    void insert(User user);

    @Query("SELECT * FROM User WHERE group_name = :groupName")
    User findUserByGroupName(String groupName);
}
