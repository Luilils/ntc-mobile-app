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

public class ContactInfoFragment extends Fragment {
    private static final String TAG = "ContactInfoFragment";
    private PersonalData personalData;

    // UI Elements
    private EditText currentAddressInput, permanentAddressInput, mobileNumberInput,
            emailAddressInput, facebookAccountInput, guardianInput, guardianJobInput;

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
        return inflater.inflate(R.layout.fragment_contact_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI elements
        currentAddressInput = view.findViewById(R.id.residentialAddressInput);
        permanentAddressInput = view.findViewById(R.id.postalAddressInput);
        mobileNumberInput = view.findViewById(R.id.mobilePhoneInput);
        emailAddressInput = view.findViewById(R.id.emailAddressInput);
        facebookAccountInput = view.findViewById(R.id.facebookAccountInput);
        guardianInput = view.findViewById(R.id.guardianInput);
        guardianJobInput = view.findViewById(R.id.guardianJobInput);

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

        Log.d(TAG, "populateViews: Current Address: " + personalData.getCurrentAddress());
        Log.d(TAG, "populateViews: Permanent Address: " + personalData.getPermanentAddress());
        Log.d(TAG, "populateViews: Mobile Number: " + personalData.getMobileNumber());
        Log.d(TAG, "populateViews: Email Address: " + personalData.getEmailAddress());
        Log.d(TAG, "populateViews: Facebook Account: " + personalData.getFacebookAccount());
        Log.d(TAG, "populateViews: Guardian: " + personalData.getGuardian());
        Log.d(TAG, "populateViews: Guardian Job: " + personalData.getGuardianJob());

        // Populate EditText fields
        if (currentAddressInput != null) currentAddressInput.setText(personalData.getCurrentAddress());
        if (permanentAddressInput != null) permanentAddressInput.setText(personalData.getPermanentAddress());
        if (mobileNumberInput != null) mobileNumberInput.setText(personalData.getMobileNumber());
        if (emailAddressInput != null) emailAddressInput.setText(personalData.getEmailAddress());
        if (facebookAccountInput != null) facebookAccountInput.setText(personalData.getFacebookAccount());
        if (guardianInput != null) guardianInput.setText(personalData.getGuardian());
        if (guardianJobInput != null) guardianJobInput.setText(personalData.getGuardianJob());
    }
} 