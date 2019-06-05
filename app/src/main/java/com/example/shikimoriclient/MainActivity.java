package com.example.shikimoriclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shikimoriclient.BackEnd.data.ExpandableListAnimeData;
import com.example.shikimoriclient.BackEnd.data.ExpandableListMangaData;
import com.example.shikimoriclient.BackEnd.data.ExpandableListRanobeData;
import com.example.shikimoriclient.BackEnd.data.SearchableAnimeData;
import com.example.shikimoriclient.BackEnd.data.SearchableMangaData;
import com.example.shikimoriclient.BackEnd.data.SearchableRanobeData;
import com.example.shikimoriclient.BackEnd.filter.FilterAdapter;
import com.example.shikimoriclient.BackEnd.filter.SearchFilter;
import com.example.shikimoriclient.FrontEnd.adapters.PagerAdapter;
import com.example.shikimoriclient.FrontEnd.dialogs.FilterDialog;
import com.example.shikimoriclient.FrontEnd.dialogs.SearchDialog;
import com.example.shikimoriclient.FrontEnd.dialogs.SigInDialog;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static com.example.shikimoriclient.BackEnd.util.Util.updateRecycleView;
import static com.example.shikimoriclient.BackEnd.data.UserInfoHandler.ACCESS_TOKEN;
import static com.example.shikimoriclient.BackEnd.data.UserInfoHandler.CREATED_AT;
import static com.example.shikimoriclient.BackEnd.data.UserInfoHandler.EXPIRES_IN;
import static com.example.shikimoriclient.BackEnd.data.UserInfoHandler.REFRESH_TOKEN;
import static com.example.shikimoriclient.BackEnd.data.UserInfoHandler.USER_ICON_URL;
import static com.example.shikimoriclient.BackEnd.data.UserInfoHandler.USER_ID;
import static com.example.shikimoriclient.BackEnd.data.UserInfoHandler.USER_NICKNAME;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private FloatingActionsMenu fabMenu;
    private FloatingActionButton fabFilter;
    private FloatingActionButton fabSearch;
    private FloatingActionButton fabBack;
    private ViewPager viewPager;
    private SearchDialog searchDialog;
    private FilterDialog filterDialog;
    private SigInDialog sigInDialog;
    private SearchFilter animeFilter;
    private SearchFilter mangaFilter;
    private SearchFilter ranobeFilter;


    public static boolean loggedIn;

    private static final int TAB_COUNT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComp();
        setLayout();
    }


    private void initializeComp() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        actionBar = getSupportActionBar();
        fabMenu = findViewById(R.id.fabMenu);
        fabFilter = findViewById(R.id.fabFilter);
        fabSearch = findViewById(R.id.fabSearch);
        fabBack = findViewById(R.id.fabBack);
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        searchDialog = new SearchDialog(this, viewPager);
        filterDialog = new FilterDialog(this, viewPager);
        sigInDialog = new SigInDialog(this);
        setCompConfiguration();
        fillComp();
        setListeners();
    }

    private void setLayout() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void fillComp() {
        LinkedHashMap<String, List<String>> expandableListDetail = ExpandableListAnimeData.getData();
        List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        List<Integer> childrensCount = new LinkedList<>();
        for (int i = 0; i < expandableListTitle.size(); i++) {
            childrensCount.add(expandableListDetail.get(expandableListTitle.get(i)).size());
        }

        FilterAdapter animeFilterAdapter = new FilterAdapter(expandableListTitle.size(), childrensCount, Collections.singletonList(2));
        animeFilter = new SearchFilter(animeFilterAdapter, new SearchableAnimeData());

        expandableListDetail = ExpandableListMangaData.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        childrensCount = new LinkedList<>();
        for (int i = 0; i < expandableListTitle.size(); i++) {
            childrensCount.add(expandableListDetail.get(expandableListTitle.get(i)).size());
        }
        FilterAdapter mangaFilterAdapter = new FilterAdapter(expandableListTitle.size(), childrensCount, Collections.singletonList(2));
        mangaFilter = new SearchFilter(mangaFilterAdapter, new SearchableMangaData());

        expandableListDetail = ExpandableListRanobeData.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        childrensCount = new LinkedList<>();
        for (int i = 0; i < expandableListTitle.size(); i++) {
            childrensCount.add(expandableListDetail.get(expandableListTitle.get(i)).size());
        }
        FilterAdapter ranobeFilterAdapter = new FilterAdapter(expandableListTitle.size(), childrensCount, Collections.singletonList(1));
        ranobeFilter = new SearchFilter(ranobeFilterAdapter, new SearchableRanobeData());
        checkUserData();
    }

    public void checkUserData() {
        File file = new File(getFilesDir(), "userInfo");
        HashMap<String, String> userData = new HashMap<>();
        if (file.exists()) {
            try {
                BufferedReader bis = new BufferedReader(new FileReader(file));
                String line;
                while ((line = bis.readLine()) != null) {
                    userData.put(line.substring(0, line.indexOf(":")), line.substring(line.indexOf(":") + 1));
                }
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            USER_ID = userData.get("user_id");
            USER_NICKNAME = userData.get("user_nickname");
            USER_ICON_URL = userData.get("user_icon_url");
            ACCESS_TOKEN = "Bearer " + userData.get("access_token");
            REFRESH_TOKEN = userData.get("refresh_token");
            CREATED_AT = userData.get("created_at");
            EXPIRES_IN = userData.get("expires_in");
        }
        updateTabs();
    }

    private void updateTabs() {
        File file = new File(getFilesDir(), "userInfo");
        if (file.exists()) {
            loggedIn = true;
            navigationView.getMenu().getItem(0).setVisible(true);
            navigationView.getMenu().getItem(0).setTitle(USER_NICKNAME);
            navigationView.getMenu().getItem(4).setTitle("Выйти");
            Toast.makeText(this, "Вы вошли в личный кабинет", Toast.LENGTH_LONG).show();
        } else {
            loggedIn = false;
            navigationView.getMenu().getItem(0).setVisible(false);
            navigationView.getMenu().getItem(4).setTitle("Войти");
        }
    }

    private void setCompConfiguration() {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), TAB_COUNT);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(TAB_COUNT);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setHomeButtonEnabled(true);
        navigationView.getMenu().getItem(1).setChecked(true);
    }

    private void setListeners() {
        fabMenu.setOnClickListener(view -> {
            if (!fabMenu.isExpanded()) {
                showFABMenu();
            } else {
                fabMenu.collapse();
            }
        });

        fabSearch.setOnClickListener(view -> {
            switch (viewPager.getCurrentItem()) {
                case 0: {
                    searchDialog.setFilter(animeFilter);
                    break;
                }
                case 1: {
                    searchDialog.setFilter(mangaFilter);
                    break;
                }
                case 2: {
                    searchDialog.setFilter(ranobeFilter);
                    break;
                }
            }
            fabMenu.collapse();
            searchDialog.show();
        });

        fabFilter.setOnClickListener(view -> {
            switch (viewPager.getCurrentItem()) {
                case 0: {
                    filterDialog.setFilter(animeFilter);
                    break;
                }
                case 1: {
                    filterDialog.setFilter(mangaFilter);
                    break;
                }
                case 2: {
                    filterDialog.setFilter(ranobeFilter);
                    break;
                }
            }
            fabMenu.collapse();
            filterDialog.show();
        });
        fabBack.setOnClickListener(view -> {
            switch (viewPager.getCurrentItem()) {
                case 0: {
                    animeFilter.rest();
                    updateRecycleView(viewPager, animeFilter);
                    break;
                }
                case 1: {
                    mangaFilter.rest();
                    updateRecycleView(viewPager, mangaFilter);
                    break;
                }
                case 2: {
                    ranobeFilter.rest();
                    updateRecycleView(viewPager, ranobeFilter);
                    break;
                }
            }
            fabMenu.collapse();
        });
    }

    private void showFABMenu() {
        if (fabMenu.isExpanded()) {
            fabBack.animate().translationY(-100);
            fabFilter.animate().translationY(-55);
            fabSearch.animate().translationY(-10);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_list) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else if (id == R.id.calendar) {
            Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG).show();
            return false;
        } else if (id == R.id.notification) {
            Toast.makeText(this, "Coming soon", Toast.LENGTH_LONG).show();
            return false;
        } else if (id == R.id.enter_exit) {
            if (!loggedIn) {
                sigInDialog.show();

            } else {
                File file = new File(getFilesDir(), "userInfo");
                if (file.delete()) {
                    Toast.makeText(this, "Вы вышли из личного кабинета", Toast.LENGTH_LONG).show();
                }
                updateTabs();
                ACCESS_TOKEN = null;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        return true;
    }
}
