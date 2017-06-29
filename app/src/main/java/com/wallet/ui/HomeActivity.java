package com.wallet.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

import com.wallet.R;
import com.wallet.activity.AddDetailActivity;
import com.wallet.activity.ChangePasswordActivity;
import com.wallet.activity.SupportActivity;
import com.wallet.activity.TCActivity;
import com.wallet.adapter.CustomListAdapter;
import com.wallet.databinding.ActivityHomeBinding;
import com.wallet.databinding.DialogViewBinding;
import com.wallet.helper.DatabaseHandler;
import com.wallet.model.Data;
import com.wallet.ui.fragment.ChangePassFragment;
import com.wallet.ui.fragment.HomeFragment;
import com.wallet.util.AppUtils;
import com.wallet.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.PROCESS_OUTGOING_CALLS;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, SearchView.OnQueryTextListener {

    private ActivityHomeBinding binding;
    private DatabaseHandler db;
    private List<Data> list;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        AppUtils.preventScreenshot(this);

        db = new DatabaseHandler(this);

        list = new ArrayList<>();
        list = db.getAllDetails();

        if (list.size() > 0) {
            adapter = new CustomListAdapter(this, list);
            binding.listview.setAdapter(adapter);
        } else {
            binding.listview.setVisibility(View.GONE);
            binding.relNoResult.setVisibility(View.VISIBLE);

            binding.relNoResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HomeActivity.this, AddDetailActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }

        binding.listview.setOnItemClickListener(this);
        binding.listview.setOnItemLongClickListener(this);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, new HomeFragment(), "").commit();

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
                    case R.id.menu_logout:
                        finish();
                        break;
                }
                return true;
            }
        });
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        if (list.size() > 0) {
            MenuItem itemSearch = menu.findItem(R.id.action_search);
            itemSearch.setVisible(true);
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(itemSearch);
            searchView.setOnQueryTextListener(this);
        }
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.add:
                i = new Intent(this, AddDetailActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.share:
                i = new Intent();
                i.setAction(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out Master Password at: https://play.google.com/store/apps/details?id=" + getPackageName());
                i.setType("text/plain");
                startActivity(i);
                return true;

            case R.id.hide:

                String[] strPermissions = new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS};

                boolean permissionGrant = PermissionUtil.checkPermission(this, strPermissions);

                if (!permissionGrant) {
                    PermissionUtil.requestPermission(this, strPermissions, 100);
                } else {

                    PackageManager p = this.getPackageManager();
                    ComponentName cName = new ComponentName(this, "com.wallet.activity.Splashscreen");
                    p.setComponentEnabledSetting(cName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                    Toast.makeText(this, "Master Password Hide", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.changePass:
                i = new Intent(this, ChangePasswordActivity.class);
                startActivity(i);
                return true;

            case R.id.support:
                i = new Intent(this, SupportActivity.class);
                startActivity(i);
                return true;

            case R.id.logout:
                finish();
                return true;

            case R.id.tc:
                i = new Intent(this, TCActivity.class);
                i.putExtra("keyHome", "fromHome");
                startActivity(i);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Dialog d = new Dialog(this);


        final DialogViewBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_view, null, false);
        d.setContentView(viewBinding.getRoot());

        viewBinding.setData(list.get(position));

        viewBinding.copyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("email", viewBinding.etEmail.getText().toString().trim());
                clipboardManager.setPrimaryClip(data);
                Toast.makeText(HomeActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });


        viewBinding.copyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData data = ClipData.newPlainText("password", viewBinding.etPass.getText().toString().trim());
                clipboardManager.setPrimaryClip(data);
                Toast.makeText(HomeActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.show();
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final String[] items = new String[]{"Update", "Delete"};
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle(list.get(position).getTag());


        alert.setItems(items,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which].equalsIgnoreCase("Update")) {
                            Intent i = new Intent(HomeActivity.this, AddDetailActivity.class);
                            i.putExtra("keyId", list.get(position).getId());
                            i.putExtra("keyTag", list.get(position).getTag());
                            i.putExtra("keyWebsite", list.get(position).getWebsite());
                            i.putExtra("keyEmail", list.get(position).getEmailid());
                            i.putExtra("keyPassword", list.get(position).getPassword());
                            startActivity(i);
                            finish();
                        } else {
                            showDeleteDialog(position);
                        }
                    }
                });

        alert.show();

        return true;
    }

    private void showDeleteDialog(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this item?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteContact(list.get(position).getId());
                list = db.getAllDetails();

                adapter = new CustomListAdapter(HomeActivity.this, list);
                binding.listview.setAdapter(adapter);
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
        adapter.getFilter().filter(newText);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 105){
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.container, new HomeFragment(), "").commit();
            }
        }
    }
}
