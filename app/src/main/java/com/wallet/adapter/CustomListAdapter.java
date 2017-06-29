package com.wallet.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.wallet.R;
import com.wallet.databinding.CustomListRowBinding;
import com.wallet.model.Data;

import java.util.ArrayList;
import java.util.List;


public class CustomListAdapter extends BaseAdapter implements Filterable {
    private Activity a;
    private List<Data> values, filterList;
    private ItemFilter filter;


    public CustomListAdapter(Activity a, List<Data> values) {
        this.a = a;
        this.values = values;
        filterList = new ArrayList<>(values);
    }

    @Override
    public void notifyDataSetChanged() {
        // TODO Auto-generated method stub
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public Object getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomListRowBinding binding = DataBindingUtil.inflate(a.getLayoutInflater(), R.layout.custom_list_row, parent, false);

        convertView = binding.getRoot();

        binding.setData(filterList.get(position));
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new ItemFilter(values);
        return filter;
    }

    private class ItemFilter extends Filter {
        private List<Data> originalData;
        private List<Data> filteredData;

        public ItemFilter(List<Data> originalData) {
            this.originalData = originalData;
            this.filteredData = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            String filterString = constraint.toString().toLowerCase().trim();

            if (filterString == null || filterString.length() == 0) {

                // No filter implemented we return all the list
                results.values = originalData;
                results.count = originalData.size();
            } else {

                // We perform filtering operation
                filteredData.clear();
                for (Data model : originalData) {
                    if (model.getTag().toLowerCase().trim().contains(filterString))
                        filteredData.add(model);
                }
                results.values = filteredData;
                results.count = filteredData.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterList = (ArrayList<Data>) results.values;
            notifyDataSetChanged();
        }

    }
}


