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

public class EmergencyContactFragment extends Fragment {
    private static final String TAG = "EmergencyContactFragment";
    private PersonalData personalData;

    // UI Elements
    private EditText emergencyContactPersonInput, emergencyContactRelationshipInput,
            emergencyContactNumberInput, emergencyContactAddressInput, emergencyHomePhoneNumberInput;

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
        return inflater.inflate(R.layout.fragment_emergency_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        emergencyContactPersonInput = view.findViewById(R.id.contactPersonInput);
        emergencyContactRelationshipInput = view.findViewById(R.id.relationshipInput);
        emergencyContactNumberInput = view.findViewById(R.id.emergencyMobileInput);
        emergencyContactAddressInput = view.findViewById(R.id.emergencyContactAddressInput);
        emergencyHomePhoneNumberInput = view.findViewById(R.id.homePhoneInput);

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

        Log.d(TAG, "populateViews: Contact Person: " + personalData.getEmergencyContactPerson());
        Log.d(TAG, "populateViews: Relationship: " + personalData.getEmergencyContactRelationship());
        Log.d(TAG, "populateViews: Mobile Number: " + personalData.getEmergencyContactNumber());
        Log.d(TAG, "populateViews: Home Phone Number: " + personalData.getEmergencyHomePhoneNumber());
        Log.d(TAG, "populateViews: Contact Address: " + personalData.getEmergencyContactAddress());

        // Populate EditText fields
        if (emergencyContactPersonInput != null) emergencyContactPersonInput.setText(personalData.getEmergencyContactPerson());
        if (emergencyContactRelationshipInput != null) emergencyContactRelationshipInput.setText(personalData.getEmergencyContactRelationship());
        if (emergencyContactNumberInput != null) emergencyContactNumberInput.setText(personalData.getEmergencyContactNumber());
        if (emergencyContactAddressInput != null) emergencyContactAddressInput.setText(personalData.getEmergencyContactAddress());
        if (emergencyHomePhoneNumberInput != null) emergencyHomePhoneNumberInput.setText(personalData.getEmergencyHomePhoneNumber());
    }
} 