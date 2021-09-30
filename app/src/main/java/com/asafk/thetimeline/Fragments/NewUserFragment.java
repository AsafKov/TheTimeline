package com.asafk.thetimeline.Fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.asafk.thetimeline.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class NewUserFragment extends TimelineFragment{

    public static final String TAG = NewUserFragment.class.getSimpleName();

    private TextInputEditText mDate;
    private AutoCompleteTextView mCountryInput;

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
        MaterialButton submit = layout.findViewById(R.id.fragment_new_user_submit);
        MaterialButton datePicker = layout.findViewById(R.id.fragment_new_user_date_picker);

        datePicker.setOnClickListener(this);
        submit.setOnClickListener(this);
        mDate.setInputType(InputType.TYPE_NULL);

        mCountryInput = layout.findViewById(R.id.fragment_new_user_country_input);
        mCountryInput.clearFocus();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, listOfCountries());
        mCountryInput.setAdapter(adapter);
    }

    @Override
    void observeData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_new_user_date_picker: {
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

    private ArrayList<String> listOfCountries() {
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        return countries;
    }
}
