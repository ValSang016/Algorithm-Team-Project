package com.gachon.algorithm;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.gachon.algorithm.User;
import com.gachon.algorithm.UserDao;

@Database(entities = {User.class, ManageInfo.class}, version = 4) // 스키마 변경을 반영하려면 버전 번호를 증가시킵니다.
public abstract class UserDataBase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ManageInfoDao manageInfoDao(); // 추가된 부분
}
