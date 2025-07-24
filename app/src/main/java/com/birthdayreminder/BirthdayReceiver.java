package com.birthdayreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BirthdayReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // 系统启动后重新设置所有提醒
            rescheduleAllNotifications(context);
        } else {
            // 处理生日提醒
            String birthdayName = intent.getStringExtra("birthday_name");
            int daysBefore = intent.getIntExtra("days_before", 0);
            
            if (birthdayName != null) {
                NotificationHelper.showNotification(context, birthdayName, daysBefore);
            }
        }
    }

    private void rescheduleAllNotifications(Context context) {
        // 重新安排所有生日提醒
        BirthdayDatabase database = BirthdayDatabase.getDatabase(context);
        BirthdayDao birthdayDao = database.birthdayDao();
        
        new Thread(() -> {
            java.util.List<Birthday> birthdays = birthdayDao.getAllBirthdays();
            for (Birthday birthday : birthdays) {
                NotificationHelper.scheduleNotification(context, birthday);
            }
        }).start();
    }
}