package com.concentricsky.android.example.threepanelayout;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends FragmentActivity
                          implements NavigationFragment.NavigationListener
{

    private boolean mIsTablet;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationFragment mNavigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            mIsTablet = true;
        } else {
            mIsTablet = false;

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, android.R.string.ok, android.R.string.cancel);
            mDrawerLayout.setDrawerListener(mDrawerToggle);

            ActionBar ab = getActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }

        mNavigationFragment = new NavigationFragment();


        //initialize back stack
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        FactListFragment frag = new FactListFragment();
        frag.setPlanet(0);
        trans.replace(R.id.navigation_frame, mNavigationFragment);
        trans.replace(R.id.list_frame, frag);
        trans.commit();

    }

    public void showFactsForPlanet(int pos) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        FactListFragment frag = new FactListFragment();
        frag.setPlanet(pos);
        trans.replace(R.id.list_frame, frag, "FactList");
        trans.addToBackStack(null);
        trans.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle != null && mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onNavigationItemClicked(ListView l, View v, int position, long id) {
        showFactsForPlanet(position);
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawers();
        }
    }
}
