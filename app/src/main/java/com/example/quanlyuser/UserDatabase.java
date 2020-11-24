package com.example.quanlyuser;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},version = 1,exportSchema = false)
public  abstract class UserDatabase extends RoomDatabase {
    private final static String dataName="user_db";
    private static UserDatabase instance;

    public synchronized static UserDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,dataName).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return instance;
    }
    public  abstract UserDAO userDAO();
}
