package com.example.corentin.justrun.v1;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.corentin.justrun.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Toolbar myToolbar;
    private static final String TAG_COMMENCER = "Commencer";
    private static final String TAG_SCHEDULE = "Schedule";
    private static final String TAG_LASTRUNS = "Lastruns";
    private static String TAG_CURRENT;
    private Fragment currentFragment;
    private Handler mHandler;


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        initUI();
    }

    private void initUI() {

        /*Function use for initialize all the UI component*/


        // Init toolbar
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        
        //Init Drawer layout and open and close trigger
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, myToolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        toggle.syncState();
        
        //Init Navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        
        //Init run fragment
        RunFragment runFragment = new RunFragment();// new fragment for the layout
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,runFragment).commit();


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //menu click
        int id = item.getItemId();
        switch(id){
            //replace actual fragment by the one selected
            case R.id.commencer:
                currentFragment = new RunFragment();
                TAG_CURRENT = TAG_COMMENCER;
                myToolbar.setTitle("Commencer");
                loadFragment();
                break;


            case R.id.last_runs:
                currentFragment = new LastRunsFragment();
                TAG_CURRENT = TAG_LASTRUNS;
                myToolbar.setTitle("Activitées");
                loadFragment();
                break;


            case R.id.schedule:
                currentFragment = new ScheduleFragment();
                TAG_CURRENT = TAG_SCHEDULE;
                loadFragment();
                myToolbar.setTitle("Programmes");
                break;



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment() {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out).replace(R.id.fragment_container,currentFragment,TAG_CURRENT).commitAllowingStateLoss();
            }
        };

        mHandler.post(mPendingRunnable);

    }


}
