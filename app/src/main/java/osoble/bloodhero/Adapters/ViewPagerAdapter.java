package osoble.bloodhero.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import osoble.bloodhero.Fragments.LearnFragment;
import osoble.bloodhero.LearnFragmentFiles.FactsFragment;
import osoble.bloodhero.LearnFragmentFiles.TypesFragment;
import osoble.bloodhero.LearnFragmentFiles.WhatFragment;
import osoble.bloodhero.LearnFragmentFiles.WhyFragment;


/**
 * Created by rashad on 5/23/16.
 */


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {

        super(fm);

    }


    @Override
    public Fragment getItem(int position) {

        if (position == 0) {

            return new WhatFragment();

        } else if (position == 1) {

            return new WhyFragment();


        } else if (position == 2) {

            return new TypesFragment();


        } else if (position == 3) {


            return new FactsFragment();

        } else {

            return new LearnFragment();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

}