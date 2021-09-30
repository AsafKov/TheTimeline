package com.asafk.thetimeline.Fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.asafk.thetimeline.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewUserFragment extends TimelineFragment{

    public static final String TAG = NewUserFragment.class.getSimpleName();

    private TextInputEditText mDate;
    private MaterialAutoCompleteTextView mCountryInput, mGender;

    private NavController mNavController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_user, null);
        initViews(layout);
        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();

        mNavController = Navigation.findNavController(requireActivity(), R.id.auth_host_fragment);
    }

    @Override
    void initViews(@NonNull View layout) {
        mDate = layout.findViewById(R.id.fragment_new_user_date);

        mDate.setInputType(InputType.TYPE_NULL);
        mDate.setOnClickListener(this);
    }

    @Override
    void observeData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_new_user_date: {
                MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().
                        setTitleText(R.string.fragment_new_user_date_picker_title).build();
                datePicker.addOnPositiveButtonClickListener(selection -> {
                    String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(selection));
                    mDate.setText(date);
                });
                datePicker.show(getChildFragmentManager(), TAG);
                break;
            }
        }
    }
}
