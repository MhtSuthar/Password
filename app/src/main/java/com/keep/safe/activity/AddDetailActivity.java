package com.keep.safe.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.keep.safe.R;
import com.keep.safe.databinding.ActivityAddsDetailBinding;
import com.keep.safe.helper.DatabaseHandler;
import com.keep.safe.model.Data;
import com.keep.safe.util.AppUtils;

public class AddDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adds_detail);
        AppUtils.preventScreenshot(this);

        final ActivityAddsDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_adds_detail);

        final Bundle b = getIntent().getExtras();
        if (b != null) {
            binding.txtHeader.setText("Update Your Credentials");
            binding.btnSave.setText("UPDATE");

            binding.edtTag.setText(b.getString("keyTag"));
            binding.edtWebName.setText(b.getString("keyWebsite"));
            binding.edtEmail.setText(b.getString("keyEmail"));
            binding.edtPass.setText(b.getString("keyPassword"));

            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = binding.edtTag.getText().toString().trim();
                    String web = binding.edtWebName.getText().toString().trim();
                    String email = binding.edtEmail.getText().toString().trim();
                    String pass = binding.edtPass.getText().toString().trim();

                    if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
                        DatabaseHandler db = new DatabaseHandler(AddDetailActivity.this);

                        Data d = new Data();
                        d.setId(b.getString("keyId"));
                        d.setPassword(pass);
                        d.setWebsite(web);
                        d.setEmailid(email);
                        d.setTag(tag);


                        db.updateDetail(d);

                        setResult(RESULT_OK);
                        finish();
                        Toast.makeText(AddDetailActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddDetailActivity.this, "Please fill all mandatory fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = binding.edtTag.getText().toString().trim();
                    String web = binding.edtWebName.getText().toString().trim();
                    String email = binding.edtEmail.getText().toString().trim();
                    String pass = binding.edtPass.getText().toString().trim();

                    if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
                        DatabaseHandler db = new DatabaseHandler(AddDetailActivity.this);

                        Data d = new Data();
                        d.setPassword(pass);
                        d.setWebsite(web);
                        d.setEmailid(email);
                        d.setTag(tag);

                        db.insertDetails(d);

                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(AddDetailActivity.this, "Please fill all mandatory fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
