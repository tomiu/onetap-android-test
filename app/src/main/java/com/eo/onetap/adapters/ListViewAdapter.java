package com.eo.onetap.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eo.onetap.R;

/**
 * Created by tomiurankar on 05/04/16.
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    /**
     * item size
     */
    private static final int sData[] = new int[100];


    private int mTabNumber;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup container = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listview, parent, false);            // set the view's size, margins, paddings and layout parameters

        ViewHolder holder = new ViewHolder(container);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText("tab: " + mTabNumber + " row: " + position);
        int color = (mTabNumber % 2 == 0) ? Color.BLUE : Color.RED;
        holder.mTextView.setTextColor(color);
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return sData.length;
    }

    public void setData(int tabNumber) {
        mTabNumber = tabNumber;
        for (int i=0; i<sData.length; i++)
            sData[i] = tabNumber;
        notifyItemRangeChanged(0, getItemCount());

    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;

        public ViewHolder(ViewGroup itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.cardview_textview);
        }
    }
}