package com.fdgproject.firedge.proyectointegrado.tabslider;

/**
 * Created by si2soluciones on 7/4/15.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fdgproject.firedge.proyectointegrado.tabs.Tab1;
import com.fdgproject.firedge.proyectointegrado.tabs.Tab2;
import com.fdgproject.firedge.proyectointegrado.tabs.Tab3;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Tab1 tab1;
    private Tab2 tab2;
    private Tab3 tab3;

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) {
            tab1 = new Tab1();
            return tab1;
        } else if(position == 1){
            tab2 = new Tab2();
            return tab2;
        } else {
            tab3 = new Tab3();
            return tab3;
        }


    }

    public Tab1 getTab1() {
        return tab1;
    }

    public Tab2 getTab2() {
        return tab2;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
