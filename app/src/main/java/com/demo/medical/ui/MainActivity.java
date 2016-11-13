package com.demo.medical.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.demo.medical.R;
import com.demo.medical.ui.frag.CreatePatientFragment;
import com.demo.medical.ui.frag.PatientsListingFragment;
import com.demo.medical.util.SharedPref;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private TextView textViewName, textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.activity_main, new PatientsListingFragment()).commit();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        textViewName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.appUserNameNav);
        textViewEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.appUserEmailNav);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        textViewName.setText(SharedPref.getAppUser(this).getUserName());
        textViewEmail.setText(SharedPref.getAppUser(this).getEmail());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                getSupportFragmentManager().popBackStack();
                break;
            case R.id.nav_create_patient:
                changeFragment(new CreatePatientFragment());
                break;
            case R.id.nav_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.nav_logout:
                logout();
                break;
        }


        item.setCheckable(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        SharedPref.clearAll(this);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void changeFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().addToBackStack("").add(R.id.activity_main, fragment).commit();

    }
}
