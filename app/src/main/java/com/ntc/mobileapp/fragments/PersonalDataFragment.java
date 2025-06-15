package com.ntc.mobileapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ntc.mobileapp.R;
import com.ntc.mobileapp.models.PersonalData;

public class PersonalDataFragment extends Fragment {
    private static final String TAG = "PersonalDataFragment";
    private PersonalData personalData;

    // UI Elements
    private EditText fullNameInput, studentIdInput, courseAndSectionInput,
            dateOfBirthInput, placeOfBirthInput, civilStatusInput, genderInput,
            citizenshipInput, religionInput, lrnInput, escInput, motherTongueInput,
            ethnicGroupInput, enrollmentStatusInput, entryLevelInput, studentBatchInput, studentEntrySemInput,
            studentClassificationInput, surnameInput, firstNameInput, middleNameInput, provinceOriginInput;

    public void setPersonalData(PersonalData personalData) {
        Log.d(TAG, "setPersonalData: called");
        this.personalData = personalData;
        // Only attempt to populate views if the view is already created
        if (getView() != null) {
            Log.d(TAG, "setPersonalData: View is not null, calling populateViews() immediately.");
            populateViews();
        } else {
            Log.d(TAG, "setPersonalData: View is null, will populate when onViewCreated is called.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        fullNameInput = view.findViewById(R.id.firstNameInput); // Using firstNameInput as part of fullName
        studentIdInput = view.findViewById(R.id.studentIdInput);
        courseAndSectionInput = view.findViewById(R.id.entryProgramInput); // Using entryProgramInput for course
        dateOfBirthInput = view.findViewById(R.id.dateOfBirthInput);
        placeOfBirthInput = view.findViewById(R.id.placeOfBirthInput);
        civilStatusInput = view.findViewById(R.id.civilStatusInput);
        genderInput = view.findViewById(R.id.sexInput); // Using sexInput for gender
        citizenshipInput = view.findViewById(R.id.nationalityInput); // Using nationalityInput for citizenship
        religionInput = view.findViewById(R.id.religionInput);
        lrnInput = view.findViewById(R.id.lrnInput);
        escInput = view.findViewById(R.id.escInput);
        motherTongueInput = view.findViewById(R.id.motherTongueInput);
        ethnicGroupInput = view.findViewById(R.id.ethnicGroupInput);

        // Initialize new UI elements
        enrollmentStatusInput = view.findViewById(R.id.enrollmentStatusInput);
        entryLevelInput = view.findViewById(R.id.entryLevelInput);
        studentBatchInput = view.findViewById(R.id.studentBatchInput);
        studentEntrySemInput = view.findViewById(R.id.studentEntrySemInput);
        studentClassificationInput = view.findViewById(R.id.studentClassificationInput);
        surnameInput = view.findViewById(R.id.surnameInput);
        firstNameInput = view.findViewById(R.id.firstNameInput);
        middleNameInput = view.findViewById(R.id.middleNameInput);
        provinceOriginInput = view.findViewById(R.id.provinceOriginInput);

        // Populate views if data is already available after views are initialized
        if (personalData != null) {
            Log.d(TAG, "onViewCreated: Data already available, calling populateViews()");
            populateViews();
        } else {
            Log.d(TAG, "onViewCreated: Data not yet available. Will wait for setPersonalData.");
        }
    }

    private void populateViews() {
        Log.d(TAG, "populateViews: called");
        if (personalData == null) {
            Log.d(TAG, "populateViews: personalData is null. Cannot populate.");
            return;
        }

        Log.d(TAG, "populateViews: Displaying data for: " + personalData.getFullName());
        Log.d(TAG, "populateViews: Student ID: " + personalData.getStudentId());
        Log.d(TAG, "populateViews: Course & Section: " + personalData.getCourseAndSection());

        // Log new fields
        Log.d(TAG, "populateViews: First Name: " + personalData.getFirstName());
        Log.d(TAG, "populateViews: Middle Name: " + personalData.getMiddleName());
        Log.d(TAG, "populateViews: Province Origin: " + personalData.getProvinceOrigin());

        // Populate other EditText fields, adding null checks if they can potentially be null
        if (fullNameInput != null) fullNameInput.setText(personalData.getFullName());
        if (studentIdInput != null) studentIdInput.setText(personalData.getStudentId());
        if (courseAndSectionInput != null) courseAndSectionInput.setText(personalData.getCourseAndSection());
        if (dateOfBirthInput != null) dateOfBirthInput.setText(personalData.getDateOfBirth());
        if (placeOfBirthInput != null) placeOfBirthInput.setText(personalData.getPlaceOfBirth());
        if (civilStatusInput != null) civilStatusInput.setText(personalData.getCivilStatus());
        if (genderInput != null) genderInput.setText(personalData.getGender());
        if (citizenshipInput != null) citizenshipInput.setText(personalData.getCitizenship());
        if (religionInput != null) religionInput.setText(personalData.getReligion());
        if (lrnInput != null) lrnInput.setText(personalData.getLrn());
        if (escInput != null) escInput.setText(personalData.getEsc());
        if (motherTongueInput != null) motherTongueInput.setText(personalData.getMotherTongue());
        if (ethnicGroupInput != null) ethnicGroupInput.setText(personalData.getEthnicGroup());

        // Populate new fields
        if (enrollmentStatusInput != null) enrollmentStatusInput.setText(personalData.getEnrollmentStatus());
        if (entryLevelInput != null) entryLevelInput.setText(personalData.getEntryLevel());
        if (studentBatchInput != null) studentBatchInput.setText(personalData.getStudentBatch());
        if (studentEntrySemInput != null) studentEntrySemInput.setText(personalData.getStudentEntrySem());
        if (studentClassificationInput != null) studentClassificationInput.setText(personalData.getStudentClassification());
        if (surnameInput != null) surnameInput.setText(personalData.getSurname());
        if (firstNameInput != null) firstNameInput.setText(personalData.getFirstName());
        if (middleNameInput != null) middleNameInput.setText(personalData.getMiddleName());
        if (provinceOriginInput != null) provinceOriginInput.setText(personalData.getProvinceOrigin());
    }
} 