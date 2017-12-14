package com.GCHS.greencanyonlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity
        implements
        SearchFragment.OnFragmentInteractionListener,
        LibraryMapFragment.OnFragmentInteractionListener,
        DueDatesFragment.OnFragmentInteractionListener,
        HoldsFragment.OnFragmentInteractionListener,
        NavigationView.OnNavigationItemSelectedListener,
        BookInfoFragment.OnFragmentInteractionListener{

    private final String prefs = "Prefs";
    private SharedPreferences sp;

    private int fragsSwitched = 0;

    public void updateFragsSwitched(){
        fragsSwitched += 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp = getSharedPreferences(prefs, Context.MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.mainFrame, new SearchFragment()).addToBackStack("fragBack");
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.users_name);
        nav_user.setText(sp.getString("fn", null) + " " + sp.getString("ln", null));
        hView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            updateFragsSwitched();
            getFragmentManager().popBackStack();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new SearchFragment()).addToBackStack("fragBack");
            ft.commit();
        } else if (id == R.id.nav_due_dates) {
            updateFragsSwitched();
            getFragmentManager().popBackStack();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new DueDatesFragment()).addToBackStack("fragBack");
            ft.commit();
        } else if (id == R.id.nav_holds) {
            updateFragsSwitched();
            getFragmentManager().popBackStack();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new HoldsFragment()).addToBackStack("fragBack");
            ft.commit();
        } else if (id == R.id.nav_library_map) {
            updateFragsSwitched();
            getFragmentManager().popBackStack();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame, new LibraryMapFragment()).addToBackStack("fragBack");
            ft.commit();
        } else if (id == R.id.nav_logout) {
            logout();
        } else if (id == R.id.nav_report) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFragmentInteraction(String title) {
        getSupportActionBar().setTitle(title);
    }

}