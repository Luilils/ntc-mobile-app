package com.ntc.mobileapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.ntc.mobileapp.R;
import com.ntc.mobileapp.models.GradeEntry;
import java.util.ArrayList;
import java.util.List;

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradeViewHolder> {
    private List<GradeEntry> grades = new ArrayList<>();

    public void updateGrades(List<GradeEntry> newGrades) {
        grades = newGrades;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grade, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        holder.bind(grades.get(position));
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    static class GradeViewHolder extends RecyclerView.ViewHolder {
        private final TextView subjectCodeText;
        private final TextView subjectNameText;
        private final TextView unitsText;
        private final TextView gradeText;
        private final TextView remarksText;

        GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectCodeText = itemView.findViewById(R.id.subjectCodeText);
            subjectNameText = itemView.findViewById(R.id.subjectNameText);
            unitsText = itemView.findViewById(R.id.unitsText);
            gradeText = itemView.findViewById(R.id.gradeText);
            remarksText = itemView.findViewById(R.id.remarksText);
        }

        void bind(GradeEntry grade) {
            subjectCodeText.setText(grade.getCode());
            subjectNameText.setText(grade.getName());
            unitsText.setText(grade.getUnits() + " units");

            String remarks = grade.getRemarks();
            remarksText.setText(remarks);

            // Set remarks text color based on the remark
            switch (remarks) {
                case "Passed":
                    remarksText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.green_500));
                    break;
                case "F":
                case "Failed":
                    remarksText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.purple_700));
                    break;
                default:
                    remarksText.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.text_secondary));
                    break;
            }

            // Determine how to display the grade number (75-100 scale)
            if ("Passed".equalsIgnoreCase(remarks)) {
                float originalGrade = grade.getGrade();
                int convertedGrade;

                if (originalGrade <= 1.0f) {
                    convertedGrade = 100;
                } else if (originalGrade >= 3.0f) {
                    convertedGrade = 75;
                } else {
                    convertedGrade = (int) Math.round(100 - ((originalGrade - 1.0f) / (3.0f - 1.0f)) * (100 - 75));
                }
                gradeText.setText(String.valueOf(convertedGrade));
            } else {
                // For non-numerical remarks, clear the grade text
                gradeText.setText("");
            }
        }
    }
} 