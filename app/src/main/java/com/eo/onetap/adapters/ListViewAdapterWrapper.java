package com.eo.onetap.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.eo.onetap.R;

import me.henrytao.recyclerview.SimpleRecyclerViewAdapter;
import me.henrytao.recyclerview.holder.HeaderHolder;

/**
 * Created by tomiurankar on 06/04/16.
 */
public class ListViewAdapterWrapper extends SimpleRecyclerViewAdapter {

    public ListViewAdapterWrapper(RecyclerView.Adapter baseAdapter) {
        super(baseAdapter);
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new HeaderHolder(inflater, parent, R.layout.item_listview_header_spacing);
    }

    @Override
    public void setBaseAdapter(RecyclerView.Adapter baseAdapter, boolean notifyDataSetChanged) {
        super.setBaseAdapter(baseAdapter, notifyDataSetChanged);
    }
}