package com.msf.bakingtime.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.msf.bakingtime.R;

public class FragmentsRecipe extends Fragment {

    private RecyclerView.LayoutManager mLayoutManager;

    void setupRecyclerView(RecyclerView mRecyclerView) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        setUpItemDecoration(mRecyclerView);
    }

    private void setUpItemDecoration(RecyclerView mRecyclerView) {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),mLayoutManager.getLayoutDirection());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_recycler));
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    boolean hasConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
