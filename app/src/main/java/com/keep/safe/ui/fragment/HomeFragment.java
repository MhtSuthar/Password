package com.keep.safe.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.keep.safe.R;
import com.keep.safe.activity.AddDetailActivity;
import com.keep.safe.helper.DatabaseHandler;
import com.keep.safe.model.Data;
import com.keep.safe.ui.adapter.HomeListAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.keep.safe.util.AppUtils.isOnline;

/**
 * Created by Admin on 28-Jun-17.
 */

public class HomeFragment extends Fragment {

    private RecyclerView listView;
    private DatabaseHandler db;
    private List<Data> list;
    private HomeListAdapter adapter;
    private ImageView mImageAdd;
    private TextView txtEmpty;
    private AdView adView;
    private static final String TAG = "HomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (RecyclerView) view.findViewById(R.id.listview);
        txtEmpty = (TextView) view.findViewById(R.id.txt_empty);
        mImageAdd = (ImageView) view.findViewById(R.id.img_add);
        mImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddDetailActivity.class);
                startActivityForResult(i, 105);
            }
        });
        setAllData();
        initAds(view);
    }

    void initAds(View view){
        adView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
        if(!isOnline(getActivity()))
            adView.setVisibility(View.GONE);
    }

    private void setAllData() {
        db = new DatabaseHandler(getActivity());
        list = new ArrayList<>();
        list = db.getAllDetails();
        if (list.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            txtEmpty.setVisibility(View.GONE);
            adapter = new HomeListAdapter(list, listView, HomeFragment.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            listView.setLayoutManager(mLayoutManager);
            listView.setItemAnimator(new DefaultItemAnimator());
            listView.setAdapter(adapter);
        } else {
            listView.setVisibility(View.GONE);
            txtEmpty.setVisibility(View.VISIBLE);
            txtEmpty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), AddDetailActivity.class);
                    startActivityForResult(i, 105);
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        if (resultCode == RESULT_OK) {
            if (requestCode == 105) {
                setAllData();
            }
        }
    }

    public void onEdit(Data data) {
        Intent i = new Intent(getActivity(), AddDetailActivity.class);
        i.putExtra("keyId", data.getId());
        i.putExtra("keyTag", data.getTag());
        i.putExtra("keyWebsite", data.getWebsite());
        i.putExtra("keyEmail", data.getEmailid());
        i.putExtra("keyPassword", data.getPassword());
        startActivityForResult(i, 105);
    }

    public void onDelete(final Data data) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteContact(data.getId());
                setAllData();
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
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }


}
