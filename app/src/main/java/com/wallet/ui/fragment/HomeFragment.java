package com.wallet.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.wallet.R;
import com.wallet.activity.AddDetailActivity;
import com.wallet.adapter.CustomListAdapter;
import com.wallet.helper.DatabaseHandler;
import com.wallet.model.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 28-Jun-17.
 */

public class HomeFragment extends Fragment {

    private ListView listView;
    private DatabaseHandler db;
    private List<Data> list;
    private CustomListAdapter adapter;
    private ImageView mImageAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listview);
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
            adapter = new CustomListAdapter(getActivity(), list);
            listView.setAdapter(adapter);
        } else {
           /* binding.listview.setVisibility(View.GONE);
            binding.relNoResult.setVisibility(View.VISIBLE);

            binding.relNoResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(HomeActivity.this, AddDetailActivity.class);
                    startActivity(i);
                    finish();
                }
            });*/
        }
    }
}
