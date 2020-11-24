package com.example.quanlyuser;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM users")
    public List<User> getAllUser();

    @Insert
    public void addUser(User... users);

    @Update
    public  void updateUser(User user);

    @Delete
    public void deleteUser(User user);
}
