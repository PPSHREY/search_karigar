package com.scoutlabour.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.scoutlabour.R;


public class SearchServiceActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_service);
        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
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

//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        final MenuItem searchItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setQueryHint("Search for a service");
//        //*** setOnQueryTextFocusChangeListener ***
//        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//
//            }
//        });
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(final String query) {
////                Toast.makeText(MainActivity.this, "called", Toast.LENGTH_SHORT).show();
////                swipe_refresh_layout.setColorSchemeResources(R.color.blue, R.color.blue, R.color.blue);
////                swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
////                    @Override
////                    public void onRefresh() {
////                        new Handler().postDelayed(new Runnable() {
////                            @Override
////                            public void run() {
////                                swipe_refresh_layout.setRefreshing(true);
////                                doPostNetworkOperation(query.toString().trim());
////                                Log.e("keyword",query);
////
////                            }
////                        }, 0);
////                    }
////                });
////
////                doPostNetworkOperation(query.toString().trim());
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(final String searchQuery) {
//
//
////                searchUserDetailModels.getFilter().filter(searchQuery);
////                doPostNetworkOperation(query.toString().trim());
//                return true;
//            }
//        });
//
//
//        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                // Do something when collapsed
//                return true;  // Return true to collapse action view
//            }
//
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                // Do something when expanded
//                return true;  // Return true to expand action view
//            }
//        });
//        return true;
//    }
    @Override
    public void onResume() {
        super.onResume();
        //swipe refresh logic start
//        swipe_refresh_layout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipe_refresh_layout.setRefreshing(false);
//
//            }
//        });
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
