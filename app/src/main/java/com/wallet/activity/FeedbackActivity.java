package com.wallet.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wallet.R;
import com.wallet.databinding.ActivityFeedbackBinding;

public class FeedbackActivity extends AppCompatActivity {

    String TO = "testingcaresolutions@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityFeedbackBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_feedback);

        setSupportActionBar(binding.toolbar);
        setTitle("Feedback");


        binding.btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edtName.getText().toString().trim();
                String issue = binding.edtIssues.getText().toString().trim();

                String myDeviceInfo = Build.BRAND + "\nversion: " + Build.VERSION.SDK_INT;


                String message = name + "\n\n" + issue + "\n\n\n\n\n" + myDeviceInfo;

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+TO));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                try {
                    startActivity(Intent.createChooser(intent, "Send mail..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(FeedbackActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
