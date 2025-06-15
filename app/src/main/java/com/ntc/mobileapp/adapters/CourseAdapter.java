package com.ntc.mobileapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ntc.mobileapp.R;
import com.ntc.mobileapp.models.CourseEntry;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private List<CourseEntry> courses = new ArrayList<>();
    private OnInstructorClickListener listener;

    public interface OnInstructorClickListener {
        void onInstructorClick(String instructorName);
    }

    public void setOnInstructorClickListener(OnInstructorClickListener listener) {
        this.listener = listener;
    }

    public void updateCourses(List<CourseEntry> newCourses) {
        courses = newCourses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.bind(courses.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseCodeText;
        private final TextView descriptionText;
        private final TextView instructorText;

        CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseCodeText = itemView.findViewById(R.id.courseCodeText);
            descriptionText = itemView.findViewById(R.id.descriptionText);
            instructorText = itemView.findViewById(R.id.instructorText);
        }

        void bind(CourseEntry course, OnInstructorClickListener listener) {
            courseCodeText.setText(course.getCourseCode());
            descriptionText.setText(course.getDescription());
            instructorText.setText(course.getInstructor());

            if (listener != null) {
                instructorText.setOnClickListener(v -> {
                    listener.onInstructorClick(course.getInstructor());
                });
            }
        }
    }
} 