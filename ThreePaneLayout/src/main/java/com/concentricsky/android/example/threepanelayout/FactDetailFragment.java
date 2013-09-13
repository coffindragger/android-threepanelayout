package com.concentricsky.android.example.threepanelayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

/**
 * Created by wiggins on 9/13/13.
 */
public class FactDetailFragment extends Fragment
{
    private TextView mTextView;
    private String mFact;

    public static FactDetailFragment newInstance(String fact)
    {
        FactDetailFragment fragment = new FactDetailFragment();

        Bundle args = new Bundle();
        args.putString("fact", fact);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(android.R.layout.simple_list_item_1, container, false);

        mTextView = (TextView)view.findViewById(android.R.id.text1);

        load_adapter();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();

        Bundle args = getArguments();
        if (args != null) {
            setFact(args.getString("fact",null));
        }
    }

    public void setFact(String fact)
    {
        mFact = fact;

        load_adapter();
    }

    public void load_adapter()
    {
        if (mTextView == null) {
            return;
        }

        mTextView.setText(mFact);
        getActivity().getActionBar().setTitle(mFact);
    }
}
