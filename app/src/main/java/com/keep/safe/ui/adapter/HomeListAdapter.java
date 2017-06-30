package com.keep.safe.ui.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keep.safe.R;
import com.keep.safe.model.Data;
import com.keep.safe.ui.fragment.HomeFragment;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {
 
    private List<Data> moviesList;
    private RecyclerView listView;
    private HomeFragment homeFragment;
 
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvList, txt_website, txt_email, txt_password, expandImage;
        ImageView imgCopyPassword, imgCopyEmail, imgCopywebsite, img_more;
        LinearLayout linMain;

        public MyViewHolder(View view) {
            super(view);
            expandImage = (TextView) view.findViewById(R.id.expandImage);
            imgCopyPassword = (ImageView) view.findViewById(R.id.img_copy_password);
            imgCopyEmail = (ImageView) view.findViewById(R.id.img_copy_email);
            imgCopywebsite = (ImageView) view.findViewById(R.id.img_copy_website);
            tvList = (TextView) view.findViewById(R.id.tvList);
            txt_password = (TextView) view.findViewById(R.id.txt_password);
            txt_website = (TextView) view.findViewById(R.id.txt_website);
            txt_email = (TextView) view.findViewById(R.id.txt_email);
            linMain = (LinearLayout) view.findViewById(R.id.expandLayout);
            img_more = (ImageView) view.findViewById(R.id.img_more);
        }
    }

    public HomeListAdapter(List<Data> moviesList, RecyclerView listView, HomeFragment homeFragment) {
        this.moviesList = moviesList;
        this.listView = listView;
        this.homeFragment = homeFragment;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_row, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Data data = moviesList.get(position);
        holder.tvList.setText(data.getTag());
        holder.txt_website.setText(data.getWebsite());
        holder.txt_email.setText(data.getEmailid());
        holder.txt_password.setText(data.getPassword());
        holder.linMain.setVisibility(View.GONE);
        holder.expandImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean shouldExpand = holder.linMain.getVisibility() == View.GONE;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    ChangeBounds transition = new ChangeBounds();
                    transition.setDuration(125);

                    if (shouldExpand) {
                        holder.linMain.setVisibility(View.VISIBLE);
                        holder.expandImage.setText("Less");
                    } else {
                        holder.linMain.setVisibility(View.GONE);
                        holder.expandImage.setText("View More");
                    }

                    TransitionManager.beginDelayedTransition(listView, transition);
                }
                holder.expandImage.setActivated(shouldExpand);
            }
        });

        holder.imgCopyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCopy("Email", data.getEmailid());
            }
        });

        holder.imgCopyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCopy("Password", data.getPassword());
            }
        });

        holder.imgCopywebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCopy("Website", data.getWebsite());
            }
        });

        holder.img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(listView.getContext(),  holder.img_more);
                popup.inflate(R.menu.menu_pass_item);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                homeFragment.onEdit(data);
                                break;
                            case R.id.menu_delete:
                                homeFragment.onDelete(data);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private void setCopy(String type, String msg){
        ClipboardManager clipboardManager = (ClipboardManager) listView.getContext().getSystemService(CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText(type, msg);
        clipboardManager.setPrimaryClip(data);
        Toast.makeText(listView.getContext(), "Copied", Toast.LENGTH_SHORT).show();
    }
}
