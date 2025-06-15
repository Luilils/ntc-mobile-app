package com.ntc.mobileapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.ntc.mobileapp.fragments.PersonalDataFragment;
import com.ntc.mobileapp.fragments.ContactInfoFragment;
import com.ntc.mobileapp.fragments.EmergencyContactFragment;
import com.ntc.mobileapp.fragments.OtherInfoFragment;
import com.ntc.mobileapp.models.PersonalData;
import java.util.HashMap;
import java.util.Map;

public class PersonalDataPagerAdapter extends FragmentStateAdapter {
    private static final int TAB_COUNT = 4;
    private PersonalData personalData;
    private final Map<Integer, Fragment> fragments = new HashMap<>();

    public PersonalDataPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void updatePersonalData(PersonalData personalData) {
        this.personalData = personalData;
        // Iterate through existing fragments and update their data
        for (int i = 0; i < TAB_COUNT; i++) {
            Fragment fragment = fragments.get(i);
            if (fragment instanceof PersonalDataFragment) {
                ((PersonalDataFragment) fragment).setPersonalData(personalData);
            } else if (fragment instanceof ContactInfoFragment) {
                ((ContactInfoFragment) fragment).setPersonalData(personalData);
            } else if (fragment instanceof EmergencyContactFragment) {
                ((EmergencyContactFragment) fragment).setPersonalData(personalData);
            } else if (fragment instanceof OtherInfoFragment) {
                ((OtherInfoFragment) fragment).setPersonalData(personalData);
            }
        }
        // notifyDataSetChanged(); // No longer strictly needed for data update if fragments are updated directly
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new PersonalDataFragment();
                break;
            case 1:
                fragment = new ContactInfoFragment();
                break;
            case 2:
                fragment = new EmergencyContactFragment();
                break;
            case 3:
                fragment = new OtherInfoFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected position " + position);
        }
        // Pass the initial data during fragment creation
        if (personalData != null) {
            if (fragment instanceof PersonalDataFragment) {
                ((PersonalDataFragment) fragment).setPersonalData(personalData);
            } else if (fragment instanceof ContactInfoFragment) {
                ((ContactInfoFragment) fragment).setPersonalData(personalData);
            } else if (fragment instanceof EmergencyContactFragment) {
                ((EmergencyContactFragment) fragment).setPersonalData(personalData);
            } else if (fragment instanceof OtherInfoFragment) {
                ((OtherInfoFragment) fragment).setPersonalData(personalData);
            }
        }
        fragments.put(position, fragment); // Store the created fragment
        return fragment;
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }
} 