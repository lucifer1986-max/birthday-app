package com.birthdayreminder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BirthdayAdapter adapter;
    private BirthdayDatabase database;
    private BirthdayDao birthdayDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化数据库
        database = Room.databaseBuilder(getApplicationContext(),
                BirthdayDatabase.class, "birthday_database")
                .allowMainThreadQueries()
                .build();
        birthdayDao = database.birthdayDao();

        // 初始化视图
        recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fab = findViewById(R.id.fab);

        // 设置RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BirthdayAdapter(this);
        recyclerView.setAdapter(adapter);

        // 添加按钮点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddBirthdayActivity.class);
                startActivity(intent);
            }
        });

        // 加载数据
        loadBirthdays();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBirthdays();
    }

    private void loadBirthdays() {
        List<Birthday> birthdays = birthdayDao.getAllBirthdays();
        adapter.setBirthdays(birthdays);
    }
}