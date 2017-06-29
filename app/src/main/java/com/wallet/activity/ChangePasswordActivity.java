package com.wallet.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wallet.R;
import com.wallet.databinding.ActivityChangePasswordBinding;
import com.wallet.util.AppUtils;
import com.wallet.util.SharedPrefUtil;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {

    private ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change_password);


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

                Toast.makeText(this, "PIN changed", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                binding.etConfirmPass.setError("New Password does not match");
            }
        } else {
            binding.etNewPass.setError("Password length must be 4");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
