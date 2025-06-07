package com.ntc.mobileapp.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.ntc.mobileapp.R;
import com.ntc.mobileapp.models.ScheduleClass;
import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private List<ScheduleClass> scheduleClasses;
    private static final int SLOT_HEIGHT = 80; // Height of each hour slot in dp
    private static final int START_HOUR = 7; // 7 AM
    private static final int DAY_WIDTH = 150; // Width of each day column in dp

    public ScheduleAdapter() {
        this.scheduleClasses = new ArrayList<>();
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule_class, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        ScheduleClass scheduleClass = scheduleClasses.get(position);
        holder.bind(scheduleClass);
        
        // Calculate position and height
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.itemView.getLayoutParams();
        
        // Parse time and calculate position
        String[] startParts = scheduleClass.getStartTime().split(":");
        String[] endParts = scheduleClass.getEndTime().split(":");
        
        int startHour = Integer.parseInt(startParts[0]);
        int startMinute = Integer.parseInt(startParts[1]);
        int endHour = Integer.parseInt(endParts[0]);
        int endMinute = Integer.parseInt(endParts[1]);
        
        // Calculate top margin (position)
        float hoursSinceStart = (startHour - START_HOUR) + (startMinute / 60.0f);
        int topMargin = Math.round(hoursSinceStart * SLOT_HEIGHT);
        
        // Calculate height
        float durationHours = (endHour - startHour) + ((endMinute - startMinute) / 60.0f);
        int height = Math.round(durationHours * SLOT_HEIGHT) - 8; // Subtract 8dp for spacing
        
        // Set layout parameters
        params.height = height;
        params.topMargin = topMargin + 4; // Add 4dp top margin
        
        // Calculate horizontal position (day column)
        params.leftMargin = scheduleClass.getDayOfWeek() * DAY_WIDTH + 4; // Add 4dp left margin
        params.width = DAY_WIDTH - 8; // Subtract 8dp for spacing (4dp on each side)
        
        holder.itemView.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return scheduleClasses.size();
    }

    public void setSchedule(List<ScheduleClass> classes) {
        this.scheduleClasses = new ArrayList<>(classes);
        notifyDataSetChanged();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView courseCode;
        private final TextView className;
        private final TextView timeRange;
        private final TextView classRoom;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            courseCode = itemView.findViewById(R.id.courseCode);
            className = itemView.findViewById(R.id.className);
            timeRange = itemView.findViewById(R.id.timeRange);
            classRoom = itemView.findViewById(R.id.classRoom);
        }

        public void bind(ScheduleClass scheduleClass) {
            courseCode.setText(scheduleClass.getCourseCode());
            className.setText(scheduleClass.getName());
            timeRange.setText(String.format("%s - %s", scheduleClass.getStartTime(), scheduleClass.getEndTime()));
            classRoom.setText(scheduleClass.getRoom());
            cardView.setCardBackgroundColor(Color.parseColor(scheduleClass.getBackgroundColor()));
        }
    }
} 