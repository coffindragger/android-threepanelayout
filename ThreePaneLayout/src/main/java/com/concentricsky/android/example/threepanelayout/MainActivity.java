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

public class MainActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private boolean mIsTablet;
    private ActionBarDrawerToggle mDrawerToggle;
    private ArrayAdapter<String> mNavigationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mNavigationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.planets_array));
        mDrawerList = (ListView) findViewById(R.id.navigation_list);
        mDrawerList.setAdapter(mNavigationAdapter);
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showFactsForPlanet(i);
                if (mDrawerLayout != null) {
                    mDrawerLayout.closeDrawers();
                }
            }
        });





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


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        FactListFragment frag = new FactListFragment();
        frag.setPlanet(0);
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
    
}
