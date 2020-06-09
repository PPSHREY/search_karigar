package com.scoutlabour.activities.labour;

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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.scoutlabour.custom.AppConstants;
import com.scoutlabour.custom.PostServiceCall;
import com.scoutlabour.custom.PrefUtils;
import com.scoutlabour.fragment.labour.OrdersHistoryFragment;
import com.scoutlabour.fragment.labour.OrdersInProgressFragment;
import com.scoutlabour.model.labour.StatusModel;
import com.scoutlabour.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    TextView tvIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tvIncome = findViewById(R.id.tvIncome);

        doPostNetworkOperation();
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_grid_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent i =new Intent(MainActivity.this,KarigarProfileActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void doPostNetworkOperation() {

        if (isNetworkConnected()) {

            JSONObject registrationObject = new JSONObject();

            try {
                registrationObject.put("worker_id", PrefUtils.getlabour(MainActivity.this).worker_id);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.e("registrationObject", registrationObject + "");

            new PostServiceCall(AppConstants.INCOME, registrationObject) {

                @Override
                public void response(String response) {
                    Log.e("  response", response + "");

                    StatusModel statusModel  = new GsonBuilder().create().fromJson(response, StatusModel.class);

                    tvIncome.setText("Total Income: ₹ "+statusModel.income+"");

                }


                @Override
                public void error(String error) {
                    Log.e(" response error...", error + "");
                }
            }.call();


        } else {
            Toast.makeText(MainActivity.this, "Internet Connection  not Avaiable", Toast.LENGTH_SHORT).show();

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OrdersInProgressFragment(), "Job Undone");
        adapter.addFrag(new OrdersHistoryFragment(), "Job Done");
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


    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("My Orders");
            setSupportActionBar(toolbar);
//            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    finish();
//                }
//            });
        }
    }

}
