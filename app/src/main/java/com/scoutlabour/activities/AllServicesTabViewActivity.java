package com.scoutlabour.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.VolleyError;
import com.google.gson.GsonBuilder;
import com.scoutlabour.R;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.GetServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.custom.RVEmptyObserver;
import com.scoutlabour.fragment.AcServicesFragment;
import com.scoutlabour.fragment.CarpenterFragment;
import com.scoutlabour.fragment.CleaningFragment;
import com.scoutlabour.fragment.ElectricalWorkFragment;
import com.scoutlabour.fragment.MasonryWorkFragment;
import com.scoutlabour.fragment.PaintingWorkFragment;
import com.scoutlabour.fragment.PlumbingWorkFragment;
import com.scoutlabour.model.AddressDetailModel;
import com.scoutlabour.model.SubCategoryDetailListModel;
import com.scoutlabour.model.SubCategoryDetailModel;

import java.util.ArrayList;
import java.util.List;



public class AllServicesTabViewActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    int tabPositon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services_tab_view);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabPositon=getIntent().getIntExtra("tabPosition",0);
        Log.e("tabPosition",tabPositon+"");
        if (tabPositon==0){
            viewPager.setCurrentItem(0);
        }else if(tabPositon==1){
            viewPager.setCurrentItem(1);
        }else if(tabPositon==2){
            viewPager.setCurrentItem(2);
        }else if(tabPositon==3){
            viewPager.setCurrentItem(3);
        }else if(tabPositon==4){
            viewPager.setCurrentItem(4);
        }else if(tabPositon==5){
            viewPager.setCurrentItem(5);
        }else if(tabPositon==6){
            viewPager.setCurrentItem(6);
        }

        setToolbar();

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CleaningFragment(), "Cleaning");
        adapter.addFrag(new CarpenterFragment(), "Carpenter Work");
        adapter.addFrag(new MasonryWorkFragment(), "Masonry Work");
        adapter.addFrag(new PaintingWorkFragment(), "Painting Work");
        adapter.addFrag(new PlumbingWorkFragment(), "Plumbing Work");
        adapter.addFrag(new ElectricalWorkFragment(), "Electrical Work");
        adapter.addFrag(new AcServicesFragment(), "Ac Services");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_search_icon) {
//            Intent i = new Intent(AllServicesTabViewActivity.this,SearchServiceActivity.class);
//            startActivity(i);
//            return true;
//        }
//
//
//
//        return super.onOptionsItemSelected(item);
//    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        SupportMenuInflater inflater = (SupportMenuInflater) getMenuInflater();
//
//        inflater.inflate(R.menu.menu_search_icon, menu);
//
//
//        return true;
//    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("All Services");
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

}
