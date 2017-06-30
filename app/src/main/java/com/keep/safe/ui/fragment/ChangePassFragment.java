package com.keep.safe.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keep.safe.util.AppUtils;
import com.keep.safe.util.SharedPrefUtil;
import com.safe.R;

import com.safe.databinding.ActivityChangePasswordBinding;

/**
 * Created by Admin on 28-Jun-17.
 */

public class ChangePassFragment  extends Fragment implements  View.OnClickListener{

    ActivityChangePasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_change_password, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnChangePass.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String curPass = binding.etCurPass.getText().toString().trim();
        String newPass = binding.etNewPass.getText().toString().trim();
        String confirmPass = binding.etConfirmPass.getText().toString().trim();
        if (curPass.length() <= 3) {
            Snackbar.make(binding.getRoot(), "Password length must be 4", Snackbar.LENGTH_LONG).show();
        }
        else if (!SharedPrefUtil.getString(AppUtils.PIN, "").equalsIgnoreCase(binding.etCurPass.getText().toString().trim())) {
            Snackbar.make(binding.getRoot(), "Current Password does not match", Snackbar.LENGTH_LONG).show();
        }else
            if (newPass.length() == 4) {
            if (newPass.equalsIgnoreCase(confirmPass)) {
                SharedPrefUtil.putValue(AppUtils.PIN, confirmPass);
                SharedPrefUtil.save();
                Snackbar.make(binding.getRoot(), "PIN changed", Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(binding.getRoot(), "New Password does not match", Snackbar.LENGTH_LONG).show();
            }
        } else {
                Snackbar.make(binding.getRoot(), "Password length must be 4", Snackbar.LENGTH_LONG).show();
        }
    }


}

