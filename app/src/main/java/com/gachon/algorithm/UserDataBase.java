package com.gachon.algorithm;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gachon.algorithm.User;
import com.gachon.algorithm.UserDao;

@Database(entities = {User.class, ManageInfo.class}, version = 6)
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao(); // Access the User data
    public abstract ManageInfoDao manageInfoDao(); // Access the ManageInfo data
}
