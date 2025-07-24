package com.birthdayreminder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.BirthdayViewHolder> {
    private List<Birthday> birthdays = new ArrayList<>();
    private Context context;

    public BirthdayAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BirthdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_birthday, parent, false);
        return new BirthdayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayViewHolder holder, int position) {
        Birthday birthday = birthdays.get(position);
        holder.bind(birthday);
    }

    @Override
    public int getItemCount() {
        return birthdays.size();
    }

    public void setBirthdays(List<Birthday> birthdays) {
        this.birthdays = birthdays;
        notifyDataSetChanged();
    }

    static class BirthdayViewHolder extends RecyclerView.ViewHolder {
        private TextView textName;
        private TextView textDate;
        private TextView textAge;
        private TextView textCalendar;

        public BirthdayViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textDate = itemView.findViewById(R.id.textDate);
            textAge = itemView.findViewById(R.id.textAge);
            textCalendar = itemView.findViewById(R.id.textCalendar);
        }

        public void bind(Birthday birthday) {
            textName.setText(birthday.name);
            
            if (birthday.isLunar) {
                textDate.setText(LunarCalendar.formatLunarDate(birthday.month, birthday.day));
                textCalendar.setText("农历");
                textCalendar.setBackgroundResource(R.drawable.lunar_tag_background);
            } else {
                textDate.setText(String.format("%d月%d日", birthday.month, birthday.day));
                textCalendar.setText("阳历");
                textCalendar.setBackgroundResource(R.drawable.solar_tag_background);
            }
            
            textAge.setText(String.format("年龄: %d岁", birthday.getAge()));
        }
    }
}