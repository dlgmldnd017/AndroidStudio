package com.example.mission10;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;


import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;

    DrawerLayout drawerLayout;

    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("카카오톡");
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.frag1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.frag2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.frag3:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        viewPager = findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(3);
        myAdapter adapter = new myAdapter(getSupportFragmentManager());

        fragment1 = new Fragment1();
        adapter.add(fragment1);

        fragment2 = new Fragment2();
        adapter.add(fragment2);

        fragment3 = new Fragment3();
        adapter.add(fragment3);

        viewPager.setAdapter(adapter);
    }

    class myAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> arrayList = new ArrayList<>();

        public myAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void add(Fragment item){
            arrayList.add(item);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.menu2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.menu3:
                viewPager.setCurrentItem(2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}