package com.example.citybus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class adminbusfunc extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem addbus,deletebus,updatefare,seatrene;
    public PageAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminbusfunc);
        getSupportActionBar().setTitle("Bus Services");



        tabLayout = findViewById(R.id.bustab);
        addbus = findViewById(R.id.addbus);
        deletebus = findViewById(R.id.deletebus);
        updatefare = findViewById(R.id.updatefare);
        seatrene = findViewById(R.id.seat);
        viewPager = findViewById(R.id.viewpager);

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0)
                {
                    pagerAdapter.notifyDataSetChanged();
                }
                else if(tab.getPosition()==1)
                {
                    pagerAdapter.notifyDataSetChanged();
                }
                else if(tab.getPosition()==2)
                {
                    pagerAdapter.notifyDataSetChanged();
                }
                else if(tab.getPosition()==3)
                {
                    pagerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
















    }
}
