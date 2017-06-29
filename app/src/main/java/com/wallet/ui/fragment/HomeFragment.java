package com.wallet.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wallet.R;
import com.wallet.activity.AddDetailActivity;
import com.wallet.adapter.CustomListAdapter;
import com.wallet.helper.DatabaseHandler;
import com.wallet.model.Data;
import com.wallet.ui.adapter.HomeListAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

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
    }

    private void setAllData() {
        db = new DatabaseHandler(getActivity());
        list = new ArrayList<>();
        list = db.getAllDetails();
        if (list.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            txtEmpty.setVisibility(View.GONE);
            adapter = new HomeListAdapter(list, listView);
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
}
