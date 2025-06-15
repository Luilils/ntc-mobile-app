package com.ntc.mobileapp.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.ntc.mobileapp.R;

public class TeacherEvaluationFormDialog extends BottomSheetDialogFragment {
    private static final String ARG_INSTRUCTOR_NAME = "instructor_name";

    public interface TeacherEvaluationListener {
        void onSubmitEvaluation(String instructorName, int[] ratings, String comment);
    }

    private TeacherEvaluationListener listener;

    public static TeacherEvaluationFormDialog newInstance(String instructorName) {
        TeacherEvaluationFormDialog dialog = new TeacherEvaluationFormDialog();
        Bundle args = new Bundle();
        args.putString(ARG_INSTRUCTOR_NAME, instructorName);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            listener = (TeacherEvaluationListener) getTargetFragment();
            if (listener == null && getParentFragment() instanceof TeacherEvaluationListener) {
                listener = (TeacherEvaluationListener) getParentFragment();
            } else if (listener == null && getActivity() instanceof TeacherEvaluationListener) {
                listener = (TeacherEvaluationListener) getActivity();
            }
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment/activity must implement TeacherEvaluationListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_teacher_evaluation_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String instructorName = getArguments().getString(ARG_INSTRUCTOR_NAME);

        // Initialize rating radio groups
        RadioGroup[] ratingGroups = {
            view.findViewById(R.id.question1RadioGroup),
            view.findViewById(R.id.question2RadioGroup),
            view.findViewById(R.id.question3RadioGroup),
            view.findViewById(R.id.question4RadioGroup),
            view.findViewById(R.id.question5RadioGroup)
        };

        // Set up submit button click listener
        MaterialButton submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            // Get all ratings
            int[] ratings = new int[ratingGroups.length];
            for (int i = 0; i < ratingGroups.length; i++) {
                int selectedId = ratingGroups[i].getCheckedRadioButtonId();
                if (selectedId != -1) {
                    // Determine the rating value based on the selected radio button ID
                    if (selectedId == R.id.q1_radio1 || selectedId == R.id.q2_radio1 || selectedId == R.id.q3_radio1 || selectedId == R.id.q4_radio1 || selectedId == R.id.q5_radio1) {
                        ratings[i] = 1;
                    } else if (selectedId == R.id.q1_radio2 || selectedId == R.id.q2_radio2 || selectedId == R.id.q3_radio2 || selectedId == R.id.q4_radio2 || selectedId == R.id.q5_radio2) {
                        ratings[i] = 2;
                    } else if (selectedId == R.id.q1_radio3 || selectedId == R.id.q2_radio3 || selectedId == R.id.q3_radio3 || selectedId == R.id.q4_radio3 || selectedId == R.id.q5_radio3) {
                        ratings[i] = 3;
                    } else if (selectedId == R.id.q1_radio4 || selectedId == R.id.q2_radio4 || selectedId == R.id.q3_radio4 || selectedId == R.id.q4_radio4 || selectedId == R.id.q5_radio4) {
                        ratings[i] = 4;
                    } else if (selectedId == R.id.q1_radio5 || selectedId == R.id.q2_radio5 || selectedId == R.id.q3_radio5 || selectedId == R.id.q4_radio5 || selectedId == R.id.q5_radio5) {
                        ratings[i] = 5;
                    }
                }
            }

            // Get additional comments
            TextInputEditText commentsEditText = view.findViewById(R.id.commentEditText);
            String additionalComments = commentsEditText.getText().toString();

            if (listener != null) {
                listener.onSubmitEvaluation(instructorName, ratings, additionalComments);
            }

            // Dismiss the evaluation form dialog
            dismiss();
        });
    }
} 