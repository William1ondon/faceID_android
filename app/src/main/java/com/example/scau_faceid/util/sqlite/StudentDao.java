package com.example.scau_faceid.util.sqlite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface StudentDao {

    @Insert
    void insertStudents(SQliteStudent... students);

    @Query(("DELETE FROM SQliteStudent"))
    void deleteAllStudents();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateStudents(SQliteStudent ... students);

    @Delete
    void deleteStudents(SQliteStudent... students);

    @Query("SELECT * FROM SQliteStudent")
    List<SQliteStudent> getAllStudents();
}
