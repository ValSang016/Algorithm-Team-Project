package com.gachon.algorithm;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
        // 삽입
    void setInsertUser(User user); // 뭘 삽입할 지 받아오는 것.

    @Update
        // 수정
    void setUpdateUser(User user);

    @Delete
        // 삭제
    void setDeleteUser(User user);

    // 조회 쿼리
    // 관리자 모드 -> 목록보기에서 보여줌.
    @Query("SELECT * FROM User") // 데이터베이스에 명령하는 명령문
    List<User> getUserAll();

    @Query("DELETE FROM User")
    void deleteAll();

    @Insert
    void insert(User user);

    @Query("SELECT * FROM User WHERE group_name = :groupName")
    User findUserByGroupName(String groupName);

}
