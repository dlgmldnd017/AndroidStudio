package com.example.test001;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;

    DrawerLayout drawer;

    NavigationView navi;
    BottomNavigationView bottomNavi;
    ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navi = findViewById(R.id.nav_view);
        navi.setNavigationItemSelectedListener(this);

        bottomNavi = findViewById(R.id.bottom_nav);
        bottomNavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_menu1:
                        pager.setCurrentItem(0);
                        bottomNavi.getMenu().findItem(R.id.bottom_menu1).setChecked(true);
                        break;
                    case R.id.bottom_menu2:
                        pager.setCurrentItem(1);
                        bottomNavi.getMenu().findItem(R.id.bottom_menu2).setChecked(true);
                        break;
                    case R.id.bottom_menu3:
                        pager.setCurrentItem(2);
                        bottomNavi.getMenu().findItem(R.id.bottom_menu3).setChecked(true);
                        break;
                }
                return false;
            }
        });

        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        myAdapter adapter = new myAdapter(getSupportFragmentManager());

        Fragment1 fragment1 = new Fragment1();
        adapter.add(fragment1);

        Fragment2 fragment2 = new Fragment2();
        adapter.add(fragment2);

        Fragment3 fragment3 = new Fragment3();
        adapter.add(fragment3);

        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavi.getMenu().findItem(R.id.bottom_menu1).setChecked(true);
                        break;
                    case 1:
                        bottomNavi.getMenu().findItem(R.id.bottom_menu2).setChecked(true);
                        break;
                    case 2:
                        bottomNavi.getMenu().findItem(R.id.bottom_menu3).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setCurrentItem(1);
    }

    class myAdapter extends FragmentStatePagerAdapter{
        ArrayList<Fragment> items = new ArrayList<>();

        public myAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void add(Fragment item){
            items.add(item);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.draw_menu1:
                pager.setCurrentItem(0);
                bottomNavi.getMenu().findItem(R.id.bottom_menu1).setChecked(true);
                break;
            case R.id.draw_menu2:
                pager.setCurrentItem(1);
                bottomNavi.getMenu().findItem(R.id.bottom_menu2).setChecked(true);
                break;
            case R.id.draw_menu3:
                pager.setCurrentItem(2);
                bottomNavi.getMenu().findItem(R.id.bottom_menu3).setChecked(true);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}