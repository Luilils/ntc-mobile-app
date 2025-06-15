package com.ntc.mobileapp.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ntc.mobileapp.R;

public class AcceptFormDialog extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_accept_form, container, false);
        
        ImageView acceptFormImage = view.findViewById(R.id.acceptFormImage);
        acceptFormImage.setOnClickListener(v -> dismiss());
        
        return view;
    }
} 