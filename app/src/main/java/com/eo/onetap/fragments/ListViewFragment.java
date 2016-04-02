package com.eo.onetap.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eo.onetap.R;
import com.eo.onetap.interfaces.ActivityCallback;
import com.eo.onetap.utils.Logger;

/**
 * Created by tom on 29.3.2016.
 */
public class ListViewFragment extends BaseViewFragment {
    private static final java.lang.String TAG = ListViewFragment.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private ActivityCallback mActivityCallback;

    public static Fragment newInstance(int tabNumber) {
        Fragment fragment = new ListViewFragment();
        setup(fragment, tabNumber);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivityCallback = (ActivityCallback) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRecyclerView = new RecyclerViewAppBar(getContext(), mActivityCallback);
        mRecyclerView.setAdapter(new ListViewAdapter(mTabNumber));

        return mRecyclerView;
    }

    /**
     * Implementation of RecyclerView which will expand the collapse AppBar with downward fling
     */
    private static class RecyclerViewAppBar extends RecyclerView {
        private static String TAG = RecyclerViewAppBar.class.getSimpleName();
        private final ActivityCallback mActivityCallback;
        private boolean mIsFlingStarted;

        public RecyclerViewAppBar(Context context, ActivityCallback activityCallback) {
            super(context);
            mActivityCallback = activityCallback;

            setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            setLayoutManager(new LinearLayoutManager(context));

            addOnScrollListener(new OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    Logger.debug(TAG, "state: " + newState);
                    expandAppBarMaybe(newState);
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }

        @Override
        public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
            Logger.debug(TAG, "consumed: " + consumed + ", velocityX: " + velocityX + ", velocityY " + velocityY);

            // only watch for downward fling
            if (velocityY < 0)
                mIsFlingStarted = true;

            return super.dispatchNestedFling(velocityX, velocityY, consumed);
        }

        /**
         * Expands AppBar if downward fling and first item is visible.
         * @param newState
         */
        private void expandAppBarMaybe(int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && mIsFlingStarted) {
                // Below call assumes, the layout manager is of type LinearLayoutManager
                int firstVisibleItem = ((LinearLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition();

                //  if downward fling and first row is visible on screen, expand AppBar
                if (firstVisibleItem == 0) {
                    mIsFlingStarted = false;
                    mActivityCallback.expandAppBar();
//                    Logger.debug(TAG, "first visible item: " + firstVisibleItem);
                }
            }
        }
    }

    private static class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> {
        /**
         * item size
         */
        private final static int sData[] = new int[100];
        static {
            for (int i = 0; i < sData.length; i++) {
                sData[i] = i + 1;
            }
        }

        private final int mTabNumber;

        public ListViewAdapter(int tabNumber) {
            mTabNumber = tabNumber;
        }

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
}
