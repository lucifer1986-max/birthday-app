package com.birthdayreminder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface BirthdayDao {
    @Query("SELECT * FROM birthdays ORDER BY month, day")
    List<Birthday> getAllBirthdays();
    
    @Query("SELECT * FROM birthdays WHERE id = :id")
    Birthday getBirthdayById(int id);
    
    @Query("SELECT * FROM birthdays WHERE month = :month AND day = :day")
    List<Birthday> getBirthdaysByDate(int month, int day);
    
    @Insert
    void insert(Birthday birthday);
    
    @Update
    void update(Birthday birthday);
    
    @Delete
    void delete(Birthday birthday);
    
    @Query("DELETE FROM birthdays WHERE id = :id")
    void deleteById(int id);
}