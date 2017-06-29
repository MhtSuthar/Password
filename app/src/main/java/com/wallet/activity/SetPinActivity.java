package com.wallet.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.wallet.R;
import com.wallet.databinding.ActivitySetPinBinding;
import com.wallet.ui.HomeActivity;
import com.wallet.util.AppUtils;
import com.wallet.util.SharedPrefUtil;

public class SetPinActivity extends AppCompatActivity  {

    boolean status;
    private String pin = "";
    ActivitySetPinBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.preventScreenshot(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_set_pin);

        binding.etPin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    binding.imgClose.setVisibility(View.VISIBLE);
                }else{
                    binding.imgClose.setVisibility(View.GONE);
                }
                if (s.length() == 4) {
                    if (SharedPrefUtil.getBoolean(AppUtils.STATUS, false)) {
                        if (s.toString().equals(SharedPrefUtil.getString(AppUtils.PIN, ""))) {
                            Intent i = new Intent(SetPinActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(SetPinActivity.this, "Wrong PIN", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (pin.length() == 0) {
                            pin = s.toString();
                            binding.tvPin.setText("Please Re-Enter Your PIN");
                            binding.etPin1.setText("");
                        } else if (pin.length() > 0 && pin.equals(s.toString())) {
                            Toast.makeText(SetPinActivity.this, "match", Toast.LENGTH_SHORT).show();
                            SharedPrefUtil.putValue(AppUtils.STATUS, true);
                            SharedPrefUtil.putValue(AppUtils.PIN, s.toString());
                            SharedPrefUtil.save();
                            Intent i = new Intent(SetPinActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(SetPinActivity.this, "Not match", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void onClickRemove(View view){
        int length = binding.etPin1.getText().length();
        if (length > 0) {
            binding.etPin1.getText().delete(length - 1, length);
            pin = binding.etPin1.getText().toString();
        }
    }

    public void onClickZero(View view){
        pin += "0";
        binding.etPin1.setText(pin);
    }

    public void onClickOne(View view){
        pin += "1";
        binding.etPin1.setText(pin);
    }

    public void onClickTwo(View view){
        pin += "2";
        binding.etPin1.setText(pin);
    }

    public void onClickThree(View view){
        pin += "3";
        binding.etPin1.setText(pin);
    }

    public void onClickFour(View view){
        pin += "4";
        binding.etPin1.setText(pin);
    }

    public void onClickFive(View view){
        pin += "5";
        binding.etPin1.setText(pin);
    }

    public void onClickSix(View view){
        pin += "6";
        binding.etPin1.setText(pin);
    }

    public void onClickSeven(View view){
        pin += "7";
        binding.etPin1.setText(pin);
    }

    public void onClickEight(View view){
        pin += "8";
        binding.etPin1.setText(pin);
    }

    public void onClickNine(View view){
        pin += "9";
        binding.etPin1.setText(pin);
    }
}