package osoble.bloodhero.LearnFragmentFiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import osoble.bloodhero.R;


public class WhatFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.what_fragment, container, false);



        return root;

    }
}
