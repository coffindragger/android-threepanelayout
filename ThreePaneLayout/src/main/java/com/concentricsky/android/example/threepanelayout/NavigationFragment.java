package com.concentricsky.android.example.threepanelayout;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by wiggins on 9/13/13.
 */
public class NavigationFragment extends ListFragment
{

    private NavigationListener mNavigationListener;

    public interface NavigationListener
    {
        public void onNavigationItemClicked(ListView l, View v, int position, long id);
    };
    public void setNavigationListener(NavigationListener listener)
    {
        mNavigationListener = listener;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();

        try {
            mNavigationListener = (NavigationListener)activity;
        } catch (ClassCastException e) {  }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.planets_array));
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (mNavigationListener != null) {
            mNavigationListener.onNavigationItemClicked(l, v, position, id);
        }
    }
}
