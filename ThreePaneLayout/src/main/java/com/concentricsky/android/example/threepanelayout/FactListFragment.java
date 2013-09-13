package com.concentricsky.android.example.threepanelayout;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by wiggins on 9/12/13.
 */
public class FactListFragment extends android.support.v4.app.ListFragment
{
    private int[] mFactIds;
    private FactClickListener mFactClickListener;
    private String[] mPlanetNames;
    private int mPosition;

    public interface FactClickListener
    {
        public void onFactClicked(int position, long id);
    }

    public FactListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();
        try {
            mFactClickListener = (FactClickListener)activity;
        } catch (ClassCastException e) { }

        mFactIds = new int[] {R.array.mercury_items, R.array.venus_items, R.array.earth_items};
        mPlanetNames = getResources().getStringArray(R.array.planets_array);

        load_adapter();
    }

    public void setPlanet(int position)
    {
        mPosition = position;
        load_adapter();
    }

    private void load_adapter()
    {
        FragmentActivity activity = getActivity();
        if (activity != null && mPosition != -1) {

            String[] facts = getResources().getStringArray(mFactIds[mPosition]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, facts);
            setListAdapter(adapter);
            getActivity().getActionBar().setTitle(mPlanetNames[mPosition]);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mFactClickListener != null) {
            mFactClickListener.onFactClicked(position, id);
        }
    }
}
