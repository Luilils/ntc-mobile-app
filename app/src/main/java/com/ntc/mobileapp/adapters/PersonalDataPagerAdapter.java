package com.ntc.mobileapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.ntc.mobileapp.fragments.PersonalDataFragment;
import com.ntc.mobileapp.fragments.ContactInfoFragment;
import com.ntc.mobileapp.fragments.EmergencyContactFragment;
import com.ntc.mobileapp.fragments.OtherInfoFragment;

public class PersonalDataPagerAdapter extends FragmentStateAdapter {
    private static final int TAB_COUNT = 4;

    public PersonalDataPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PersonalDataFragment();
            case 1:
                return new ContactInfoFragment();
            case 2:
                return new EmergencyContactFragment();
            case 3:
                return new OtherInfoFragment();
            default:
                throw new IllegalStateException("Unexpected position " + position);
        }
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }
} 