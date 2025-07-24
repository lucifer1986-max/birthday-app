package com.birthdayreminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import java.util.Calendar;

public class NotificationHelper {
    private static final String CHANNEL_ID = "birthday_reminder_channel";
    private static final String CHANNEL_NAME = "生日提醒";
    private static final String CHANNEL_DESCRIPTION = "生日和纪念日提醒通知";

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription(CHANNEL_DESCRIPTION);
            
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void scheduleNotification(Context context, Birthday birthday) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        
        // 为每个生日设置3天的提醒（提前3天、2天、1天）
        for (int daysBefore = 3; daysBefore >= 1; daysBefore--) {
            Calendar calendar = getNextBirthdayCalendar(birthday);
            calendar.add(Calendar.DAY_OF_YEAR, -daysBefore);
            calendar.set(Calendar.HOUR_OF_DAY, 9); // 上午9点提醒
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            // 如果时间已过，设置为明年
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.add(Calendar.YEAR, 1);
            }

            Intent intent = new Intent(context, BirthdayReceiver.class);
            intent.putExtra("birthday_id", birthday.id);
            intent.putExtra("birthday_name", birthday.name);
            intent.putExtra("days_before", daysBefore);

            int requestCode = birthday.id * 10 + daysBefore; // 确保每个提醒有唯一的requestCode
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context, 
                    requestCode, 
                    intent, 
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            if (alarmManager != null) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            }
        }
    }

    private static Calendar getNextBirthdayCalendar(Birthday birthday) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, birthday.month - 1); // Calendar月份从0开始
        calendar.set(Calendar.DAY_OF_MONTH, birthday.day);
        
        // 如果今年的生日已过，设置为明年
        Calendar today = Calendar.getInstance();
        if (calendar.before(today)) {
            calendar.add(Calendar.YEAR, 1);
        }
        
        return calendar;
    }

    public static void showNotification(Context context, String name, int daysBefore) {
        createNotificationChannel(context);
        
        String title = "生日提醒";
        String content;
        if (daysBefore == 0) {
            content = String.format("今天是%s的生日！", name);
        } else {
            content = String.format("%s的生日还有%d天", name, daysBefore);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = 
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}