package com.concentricsky.android.example.threepanelayout;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends FragmentActivity
                          implements    NavigationFragment.NavigationListener,
                                        FactListFragment.FactClickListener, FragmentManager.OnBackStackChangedListener {

    private boolean mIsTabletLayout;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationFragment mNavigationFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            mIsTabletLayout = true;
        } else {
            mIsTabletLayout = false;

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, android.R.string.ok, android.R.string.cancel);
            mDrawerLayout.setDrawerListener(mDrawerToggle);

            ActionBar ab = getActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }

        mNavigationFragment = new NavigationFragment();


        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(this);

        //initialize back stack
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction trans = fragmentManager.beginTransaction();
        FactListFragment frag = FactListFragment.newInstance(0);
        trans.replace(R.id.navigation_frame, mNavigationFragment);
        trans.replace(R.id.list_frame, frag, "Home");
        trans.commit();

    }

    public void showFactsForPlanet(int pos) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FactListFragment frag = (FactListFragment) fragmentManager.findFragmentByTag("FactList");
        if (frag == null) { //only add to backstack if there isnt one yet.
            FragmentTransaction trans = fragmentManager.beginTransaction();
            frag = FactListFragment.newInstance(pos);
            trans.replace(R.id.list_frame, frag, "FactList");
            trans.addToBackStack(null);
            trans.commit();
        } else {
            frag.setPlanet(pos);
        }
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

    @Override
    public void onFactClicked(String fact, int position, long id) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        FactDetailFragment frag = FactDetailFragment.newInstance(fact);
        if (mIsTabletLayout) {
            FactListFragment listFragment = (FactListFragment) manager.findFragmentById(R.id.list_frame);
            listFragment.setLayoutWidth(300);
            trans.replace(R.id.detail_frame, frag, "FactDetail");
            trans.hide(mNavigationFragment);

        } else {
            trans.replace(R.id.list_frame, frag, "FactDetail");
        }
        trans.addToBackStack(null);
        trans.commit();

    }

    @Override
    public void onBackStackChanged() {
        if (mIsTabletLayout) {
            FactListFragment list_fragment = (FactListFragment) mFragmentManager.findFragmentById(R.id.list_frame);
            Fragment detail_fragment = mFragmentManager.findFragmentById(R.id.detail_frame);
            if (detail_fragment == null && list_fragment != null) {
                list_fragment.setLayoutWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            }
        }

    }
}
