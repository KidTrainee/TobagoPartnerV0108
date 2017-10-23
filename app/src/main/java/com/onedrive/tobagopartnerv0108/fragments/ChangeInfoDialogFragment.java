package com.onedrive.tobagopartnerv0108.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.activities.BaseAuthenticatedActivity;

public class ChangeInfoDialogFragment extends BaseDialogFragment implements AdapterView.OnItemSelectedListener {

    BaseAuthenticatedActivity activity;

    public static ChangeInfoDialogFragment newInstance() {

        Bundle args = new Bundle();

        ChangeInfoDialogFragment fragment = new ChangeInfoDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseAuthenticatedActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Window window = getDialog().getWindow();
        // chỉnh kích cỡ fragment khi show input keyboard
        if (window != null) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }

        View view = inflater.inflate(R.layout.fragment_dialog_change_info, container, false);
        (view.findViewById(R.id.fragment_dialog_change_info_button_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        (view.findViewById(R.id.fragment_dialog_change_info_button_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        final Spinner nameTitleSpinner = (Spinner) view.findViewById(R.id.fragment_dialog_change_info_spinner_nameTitle);
        final TextInputEditText addressEditText = (TextInputEditText) view.findViewById(R.id.fragment_dialog_change_info_textInputEditText_address);
        final TextInputEditText birthdayEditText = (TextInputEditText) view.findViewById(R.id.fragment_dialog_change_info_textInputEditText_birthday);
        final TextInputEditText phoneEditText = (TextInputEditText) view.findViewById(R.id.fragment_dialog_change_info_textInputEditText_phone);
        final EditText nameEditText = (EditText) view.findViewById(R.id.fragment_dialog_change_info_editText_name);

        addressEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    addressEditText.clearFocus();
                    nameTitleSpinner.requestFocus();
                    nameTitleSpinner.performClick();
                    nameTitleSpinner.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            nameEditText.requestFocus();
                            return true;
                        }
                    });
                }
                return true;
            }
        });
        getDialog().setTitle(getContext().getResources().getString(R.string.change_info));
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getSelectedItem().toString();
        Toast.makeText(activity, choice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(activity, "Nothing selected", Toast.LENGTH_SHORT).show();
    }
}
