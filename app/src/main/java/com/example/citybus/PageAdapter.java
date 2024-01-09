package com.example.citybus;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter
{

    private int noOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new addbus();
            case 1:
                return new deletebus();
            case 2 :
                return new updateFare();
            case 3 :
                return new seatrene();
            default :
                return null;
        }

    }

    @Override
    public int getCount() {
        return noOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}