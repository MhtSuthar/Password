package com.wallet.ui.fragment;

import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.wallet.R;
import com.wallet.adapter.CustomListAdapter;
import com.wallet.databinding.ActivityChangePasswordBinding;
import com.wallet.helper.DatabaseHandler;
import com.wallet.model.Data;
import com.wallet.util.AppUtils;
import com.wallet.util.SharedPrefUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 28-Jun-17.
 */

public class ChangePassFragment  extends Fragment implements View.OnFocusChangeListener, View.OnClickListener{

    ActivityChangePasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_change_password, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.etCurPass.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        binding.etConfirmPass.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        binding.etNewPass.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        binding.etCurPass.setOnFocusChangeListener(this);
        binding.btnChangePass.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            if (!SharedPrefUtil.getString(AppUtils.PIN, "").equalsIgnoreCase(binding.etCurPass.getText().toString().trim())) {
                binding.etCurPass.setError("Current Password does not match");
            }
        }
    }

    @Override
    public void onClick(View v) {
        String newPass = binding.etNewPass.getText().toString().trim();
        String confirmPass = binding.etConfirmPass.getText().toString().trim();

        if (newPass.length() == 4) {
            if (newPass.equalsIgnoreCase(confirmPass)) {
                SharedPrefUtil.putValue(AppUtils.PIN, confirmPass);
                SharedPrefUtil.save();
                Toast.makeText(getActivity(), "PIN changed", Toast.LENGTH_SHORT).show();
            } else {
                binding.etConfirmPass.setError("New Password does not match");
            }
        } else {
            binding.etNewPass.setError("Password length must be 4");
        }
    }


}

