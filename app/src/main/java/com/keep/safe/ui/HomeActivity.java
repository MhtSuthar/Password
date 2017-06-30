package com.keep.safe.ui;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.keep.safe.R;
import com.keep.safe.databinding.ActivityHomesBinding;
import com.keep.safe.helper.DatabaseHandler;
import com.keep.safe.model.Data;
import com.keep.safe.ui.fragment.ChangePassFragment;
import com.keep.safe.ui.fragment.HomeFragment;
import com.keep.safe.util.AppUtils;
import com.keep.safe.util.BottomNavigationViewBehavior;
import com.keep.safe.util.PermissionUtil;
import java.lang.reflect.Field;
import java.util.List;

import static android.Manifest.permission.PROCESS_OUTGOING_CALLS;
import static com.keep.safe.util.AppUtils.isOnline;

public class HomeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ActivityHomesBinding binding;
    private DatabaseHandler db;
    private List<Data> list;
    private static final String TAG = "HomeActivity";
    private InterstitialAd mInterstitialAd;
    private boolean mFullAddDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_homes);

        AppUtils.preventScreenshot(this);

        initAds();

        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, new HomeFragment(), "").commit();

        disableShiftMode(binding.bottomNavigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) binding.bottomNavigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new HomeFragment(), "").commit();
                        break;
                    case R.id.menu_change_apss:
                        getSupportFragmentManager().beginTransaction().
                                replace(R.id.container, new ChangePassFragment(), "").commit();
                        break;
                    case R.id.menu_share:
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_SEND);
                        i.putExtra(Intent.EXTRA_TEXT,
                                "Hey check out Master Password at: https://play.google.com/store/apps/details?id=" + getPackageName());
                        i.setType("text/plain");
                        startActivity(i);
                        break;
                    case R.id.menu_hide:
                        String[] strPermissions = new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS};
                        boolean permissionGrant = PermissionUtil.checkPermission(HomeActivity.this, strPermissions);
                        if (!permissionGrant) {
                            PermissionUtil.requestPermission(HomeActivity.this, strPermissions, 100);
                        } else {
                            PackageManager p = getPackageManager();
                            ComponentName cName = new ComponentName(HomeActivity.this, "com.keep.safe.activity.Splashscreen");
                            p.setComponentEnabledSetting(cName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                            Toast.makeText(HomeActivity.this, "Master Password Hide", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.menu_logout:
                        logout();
                        break;
                }
                return true;
            }
        });
    }

    private void initAds() {
        AdRequest adRequest = new AdRequest.Builder()
                .build();

        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {

            }
        });
    }

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    private void logout() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0) {

                    boolean process_outgoing = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (process_outgoing)
                        Toast.makeText(this, "Permission Granted!!!", Toast.LENGTH_LONG).show();
                    else {

                        Toast.makeText(this, "Permission Denied...", Toast.LENGTH_LONG).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.PROCESS_OUTGOING_CALLS)) {
                                showMessageOKCancel("You need to allow access to permission",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{PROCESS_OUTGOING_CALLS},
                                                            100);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }

                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", onClickListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //adapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded() && !mFullAddDisplayed) {
            mInterstitialAd.show();
            mFullAddDisplayed = true;
        }else
            finish();
    }



}
