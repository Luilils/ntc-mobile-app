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

public class OtherInfoFragment extends Fragment {
    private static final String TAG = "OtherInfoFragment";
    private PersonalData personalData;

    // UI Elements
    private EditText medicalConditionsInput, allergiesInput, medicationsInput,
            hobbiesInput, talentsInput, awardsAchievedInput, organizationsJoinedInput,
            disabilityInput, indigenousGroupInput, voucherTypeInput, scholarshipTypeInput;

    public void setPersonalData(PersonalData personalData) {
        Log.d(TAG, "setPersonalData: called");
        this.personalData = personalData;
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
        return inflater.inflate(R.layout.fragment_other_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        medicalConditionsInput = view.findViewById(R.id.medicalConditionsInput);
        allergiesInput = view.findViewById(R.id.allergiesInput);
        medicationsInput = view.findViewById(R.id.medicationsInput);
        hobbiesInput = view.findViewById(R.id.hobbiesInput);
        talentsInput = view.findViewById(R.id.talentsInput);
        awardsAchievedInput = view.findViewById(R.id.awardsAchievedInput);
        organizationsJoinedInput = view.findViewById(R.id.organizationsJoinedInput);

        // Initialize new UI elements
        disabilityInput = view.findViewById(R.id.disabilityInput);
        indigenousGroupInput = view.findViewById(R.id.indigenousGroupInput);
        voucherTypeInput = view.findViewById(R.id.voucherTypeInput);
        scholarshipTypeInput = view.findViewById(R.id.scholarshipTypeInput);

        // Populate views if data is already available
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

        Log.d(TAG, "populateViews: Medical Conditions: " + personalData.getMedicalConditions());
        Log.d(TAG, "populateViews: Allergies: " + personalData.getAllergies());
        Log.d(TAG, "populateViews: Medications: " + personalData.getMedications());
        Log.d(TAG, "populateViews: Hobbies: " + personalData.getHobbies());
        Log.d(TAG, "populateViews: Talents: " + personalData.getTalents());
        Log.d(TAG, "populateViews: Awards Achieved: " + personalData.getAwardsAchieved());
        Log.d(TAG, "populateViews: Organizations Joined: " + personalData.getOrganizationsJoined());
        Log.d(TAG, "populateViews: Disability: " + personalData.getDisability());
        Log.d(TAG, "populateViews: Indigenous Group: " + personalData.getIndigenousGroup());
        Log.d(TAG, "populateViews: Voucher Type: " + personalData.getVoucherType());
        Log.d(TAG, "populateViews: Scholarship Type: " + personalData.getScholarshipType());

        // Populate EditText fields
        if (medicalConditionsInput != null) medicalConditionsInput.setText(personalData.getMedicalConditions());
        if (allergiesInput != null) allergiesInput.setText(personalData.getAllergies());
        if (medicationsInput != null) medicationsInput.setText(personalData.getMedications());
        if (hobbiesInput != null) hobbiesInput.setText(personalData.getHobbies());
        if (talentsInput != null) talentsInput.setText(personalData.getTalents());
        if (awardsAchievedInput != null) awardsAchievedInput.setText(personalData.getAwardsAchieved());
        if (organizationsJoinedInput != null) organizationsJoinedInput.setText(personalData.getOrganizationsJoined());

        // Populate new fields
        if (disabilityInput != null) disabilityInput.setText(personalData.getDisability());
        if (indigenousGroupInput != null) indigenousGroupInput.setText(personalData.getIndigenousGroup());
        if (voucherTypeInput != null) voucherTypeInput.setText(personalData.getVoucherType());
        if (scholarshipTypeInput != null) scholarshipTypeInput.setText(personalData.getScholarshipType());
    }
} 