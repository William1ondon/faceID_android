package com.example.scau_faceid.util.sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {SQliteStudent.class},version = 1,exportSchema = false)
public abstract class SQliteStudentDatabase extends RoomDatabase {
    //单例模式
    private static SQliteStudentDatabase INSTANCE;
    static synchronized SQliteStudentDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE= Room.databaseBuilder(context,SQliteStudentDatabase.class,"SQliteStudentDatabase").build();
        }
        return INSTANCE;
    }

    public abstract StudentDao getStudentDao();
}
