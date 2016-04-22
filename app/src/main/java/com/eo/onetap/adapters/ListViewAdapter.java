package com.eo.onetap.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eo.onetap.R;
import com.eo.onetap.models.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomiurankar on 05/04/16.
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
    // initial value set to 0, so we can avoid null checks
    private List<Weather> mWeatherData = new ArrayList<>(0);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup container = (ViewGroup) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listview, parent, false);            // set the view's size, margins, paddings and layout parameters

        ViewHolder holder = new ViewHolder(container);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindView(mWeatherData.get(position));
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return mWeatherData.size();
    }

    public Weather getItem(int position) {
        return mWeatherData.get(position);
    }


    public void setData(List<Weather> weatherData) {
        mWeatherData = weatherData;

        notifyDataSetChanged();
    }



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTitle, mDescription;

        public ViewHolder(ViewGroup itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.cardview_title);
            mDescription = (TextView) itemView.findViewById(R.id.cardview_description);
        }

        public void bindView(Weather weather) {
            mTitle.setText(weather.getForecast());
            mDescription.setText(weather.getForecastDescription());
        }
    }
}