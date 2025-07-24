package com.birthdayreminder;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import java.util.Calendar;

public class AddBirthdayActivity extends AppCompatActivity {
    private EditText editName;
    private EditText editDate;
    private RadioGroup radioGroup;
    private Button btnSave;
    private Button btnSelectDate;
    
    private BirthdayDatabase database;
    private BirthdayDao birthdayDao;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_birthday);

        // 初始化数据库
        database = Room.databaseBuilder(getApplicationContext(),
                BirthdayDatabase.class, "birthday_database")
                .allowMainThreadQueries()
                .build();
        birthdayDao = database.birthdayDao();

        // 初始化视图
        editName = findViewById(R.id.editName);
        editDate = findViewById(R.id.editDate);
        radioGroup = findViewById(R.id.radioGroup);
        btnSave = findViewById(R.id.btnSave);
        btnSelectDate = findViewById(R.id.btnSelectDate);

        selectedDate = Calendar.getInstance();

        // 选择日期按钮
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        // 保存按钮
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBirthday();
            }
        });
    }

    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate.set(year, month, dayOfMonth);
                        String dateStr = String.format("%d年%d月%d日", year, month + 1, dayOfMonth);
                        editDate.setText(dateStr);
                    }
                },
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void saveBirthday() {
        String name = editName.getText().toString().trim();
        String dateStr = editDate.getText().toString().trim();
        
        if (name.isEmpty() || dateStr.isEmpty()) {
            Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        boolean isLunar = radioButton.getText().toString().equals("农历");

        Birthday birthday = new Birthday();
        birthday.name = name;
        birthday.year = selectedDate.get(Calendar.YEAR);
        birthday.month = selectedDate.get(Calendar.MONTH) + 1;
        birthday.day = selectedDate.get(Calendar.DAY_OF_MONTH);
        birthday.isLunar = isLunar;

        birthdayDao.insert(birthday);
        
        // 设置提醒
        NotificationHelper.scheduleNotification(this, birthday);
        
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}