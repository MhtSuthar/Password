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

public class SetPinActivity extends AppCompatActivity implements View.OnClickListener {

    boolean status;
    private String pin = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.preventScreenshot(this);

        final ActivitySetPinBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_set_pin);
        setSupportActionBar(binding.toolbar);
        setTitle("SECURE PIN");

//        SharedPrefUtil.init(this);
//        SharedPrefUtil.save();


        binding.etPin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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



     /*   sharedPreferences = getSharedPreferences(mypreferences, MODE_PRIVATE);
        lstatus = sharedPreferences.getBoolean(loginstatus, false);
        if (lstatus) {
            Intent intent = new Intent(this, AddLogout.class);
            startActivity(intent);
            finish();
        }*/
    }


    @Override
    public void onClick(View v) {

     /*   String n = edtEmail.getText().toString();
        String p = edtPass.getText().toString();

        DatabaseHandler db = new DatabaseHandler(this);

        int id = db.login(n, p);

        if ((id == 0) | (n.length() == 0) | (p.length() == 0)) {
//            lstatus = false;
            Snackbar snackbar = Snackbar.make(linear, "Login Unsuccessful", Snackbar.LENGTH_LONG);
            snackbar.show();


        } else {
            Toast.makeText(SetPinActivity.this, "You are logged In", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();

//            lstatus = true;
            editor.putBoolean(loginstatus, true);
            editor.putInt("id", id);
            editor.commit();

            Intent intent = new Intent(SetPinActivity.this, AddLogout.class);
            startActivity(intent);
            finish();
        }*/
    }
}