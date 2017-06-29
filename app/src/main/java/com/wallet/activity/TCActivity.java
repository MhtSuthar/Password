package com.wallet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wallet.R;
import com.wallet.util.AppUtils;
import com.wallet.util.SharedPrefUtil;

public class TCActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tc);

        TextView tv = (TextView) findViewById(R.id.web);
//        webView.loadData(getString(R.string.t_c),"text/html","UTF-8");
        tv.setText(getString(R.string.t_c));

        TextView agree = (TextView) findViewById(R.id.tvAgree);

        if (getIntent().getExtras() != null) {
            String key = getIntent().getExtras().getString("keyHome");
            if (key.equalsIgnoreCase("fromHome")) {
                agree.setText("OK");

                agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        } else {
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPrefUtil.putValue(AppUtils.TC, true);
                    SharedPrefUtil.save();

                    Intent i = new Intent(TCActivity.this, SetPinActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
