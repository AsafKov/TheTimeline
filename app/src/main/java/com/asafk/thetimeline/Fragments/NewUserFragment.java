package com.asafk.thetimeline.Fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.asafk.thetimeline.Model.User;
import com.asafk.thetimeline.R;
import com.asafk.thetimeline.Utils.FirebaseUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class NewUserFragment extends TimelineFragment{

    public static final String TAG = NewUserFragment.class.getSimpleName();

    private TextInputEditText mDate;
    private long mSelectedDate;

    private AutoCompleteTextView mCountryInput, mGenderInput, mOrientationInput, mFaithInput;

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

        mCountryInput = layout.findViewById(R.id.fragment_new_user_country_input);
        mGenderInput = layout.findViewById(R.id.fragment_new_user_gender_input);
        mOrientationInput = layout.findViewById(R.id.fragment_new_user_sexuality_input);
        mFaithInput = layout.findViewById(R.id.fragment_new_user_faith_input);

        MaterialButton submit = layout.findViewById(R.id.fragment_new_user_submit);
        TextInputLayout datePicker = layout.findViewById(R.id.fragment_new_user_date_picker);

        submit.setOnClickListener(this);

        mCountryInput.clearFocus();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_dropdown_item_1line, listOfCountries());
        mCountryInput.setAdapter(adapter);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.fragment_new_user_gender));
        mGenderInput.setAdapter(adapter);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.fragment_new_user_orientation));
        mOrientationInput.setAdapter(adapter);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.fragment_new_user_faith));
        mFaithInput.setAdapter(adapter);

        datePicker.setEndIconOnClickListener(view -> {
            MaterialDatePicker<Long> pickerDialog = MaterialDatePicker.Builder.datePicker()
                    .setTitleText(R.string.fragment_new_user_date_picker_title)
                    .build();

            pickerDialog.addOnPositiveButtonClickListener(selection -> {
                mSelectedDate = selection;
                String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(selection));
                mDate.setText(date);
            });
            pickerDialog.show(getChildFragmentManager(), TAG);
        });
    }

    @Override
    void observeData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_new_user_submit: {
                if(isInputValid()){
                    final String gender = mGenderInput.getText().toString();
                    final String orientation = mOrientationInput.getText().toString();
                    final String faith = mFaithInput.getText().toString();
                    final String country = mCountryInput.getText().toString();
                    final User user = new User(mSelectedDate, country, User.Gender.fromString(gender),
                            User.Orientation.fromString(orientation),
                            User.Faith.fromString(faith));

                    FirebaseUtils.writeUserToDatabase(user, isSuccessful -> {
                        if(isSuccessful){
                            NavDirections action = NewUserFragmentDirections.actionNewUserFragmentToSplashFragment();
                            mNavController.navigate(action);
                        } else {
                            //TODO: handle failure
                        }
                    });
                }
            }
        }
    }

    private boolean isInputValid(){
        return true;
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
