package com.birthdayreminder;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Birthday.class}, version = 1, exportSchema = false)
public abstract class BirthdayDatabase extends RoomDatabase {
    public abstract BirthdayDao birthdayDao();
    
    private static volatile BirthdayDatabase INSTANCE;
    
    public static BirthdayDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BirthdayDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BirthdayDatabase.class, "birthday_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}