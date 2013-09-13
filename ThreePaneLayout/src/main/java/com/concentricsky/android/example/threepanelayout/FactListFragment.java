package com.concentricsky.android.example.threepanelayout;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by wiggins on 9/12/13.
 */
public class FactListFragment extends android.support.v4.app.ListFragment
{
    private int[] mFactIds;
    private FactClickListener mFactClickListener;
    private String[] mPlanetNames;
    private int mPosition;

    public void setLayoutWidth(int width) {
        ListView lv = getListView();
        if (lv != null)
            lv.setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public interface FactClickListener
    {
        public void onFactClicked(String fact, int position, long id);
    }
    public void setFactClickListener(FactClickListener listener) {
        mFactClickListener = listener;
    }


    public static FactListFragment newInstance(int planet_number)
    {
        FactListFragment fragment = new FactListFragment();

        Bundle args = new Bundle();
        args.putInt("planet_number", planet_number);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment, container, false);
        return v;
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

        Bundle args = getArguments();
        if (args != null) {
            setPlanet(args.getInt("planet_number", 0));
        }
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
            String item = (String)getListAdapter().getItem(position);
            mFactClickListener.onFactClicked(item, position, id);
        }
    }
}
