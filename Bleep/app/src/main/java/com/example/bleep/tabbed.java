package com.example.bleep;

import android.os.Bundle;

import com.example.bleep.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class tabbed extends AppCompatActivity
{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.view_pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        sectionsPagerAdapter.addFragment(new tab1_fragment() , "BLOOD PRESSURE");   //tab1_fragment is the java file . Not the .xml file
        sectionsPagerAdapter.addFragment(new tab2_fragment() , "CALORIES");
        sectionsPagerAdapter.addFragment(new tab3_fragment() , "BLOOD SUGAR");

        viewPager.setAdapter(sectionsPagerAdapter);
    }
}