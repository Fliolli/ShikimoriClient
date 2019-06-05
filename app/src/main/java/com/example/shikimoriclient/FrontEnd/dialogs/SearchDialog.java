package com.example.shikimoriclient.FrontEnd.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shikimoriclient.BackEnd.api.anime.Animes;
import com.example.shikimoriclient.BackEnd.api.Api;
import com.example.shikimoriclient.BackEnd.api.manga.Mangas;
import com.example.shikimoriclient.BackEnd.api.ranobe.Ranobes;
import com.example.shikimoriclient.BackEnd.dao.anime.AnimeSimple;
import com.example.shikimoriclient.BackEnd.dao.manga.MangaSimple;
import com.example.shikimoriclient.BackEnd.dao.ranobe.RanobeSimple;
import com.example.shikimoriclient.BackEnd.filter.SearchFilter;
import com.example.shikimoriclient.R;
import com.example.shikimoriclient.BackEnd.data.UserInfoHandler;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shikimoriclient.BackEnd.util.Util.updateRecycleView;

public class SearchDialog {
    private AVLoadingIndicatorView progressBar;
    private Activity activity;
    private View view;
    private AlertDialog alertDialog;
    private ViewPager viewPager;
    private EditText searchBox;
    private ListView listView;
    private TextView notFoundText;
    private ArrayAdapter<String> searchListAdapter;

    private SearchFilter searchFilter;
    private SearchFilter namesFilter;

    public SearchDialog(Activity activity, ViewPager viewPager) {
        this.activity = activity;
        this.viewPager = viewPager;
    }

    public void setFilter(SearchFilter searchFilter) {
        this.searchFilter = searchFilter;
        namesFilter = new SearchFilter(searchFilter.getParams());
        namesFilter.delParam("page");
    }


    private void searchByString(String str) {
        searchFilter.setParam("search", str);
        updateRecycleView(viewPager, searchFilter);
    }

    public void show() {
        final AlertDialog.Builder adb = new AlertDialog.Builder(activity);
        initializeComp();
        String searchText = searchFilter.getParams().get("search");
        if (searchText != null) {
            searchBox.setText(searchFilter.getParams().get("search"));
            searchBox.setSelection(searchBox.getText().length());
        }
        adb.setView(view);
        alertDialog = adb.create();
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        alertDialog.show();
    }

    private void initializeComp() {
        view = activity.getLayoutInflater().inflate(R.layout.search_dialog, null);
        listView = view.findViewById(R.id.list);
        searchBox = view.findViewById(R.id.searchBox);
        progressBar = view.findViewById(R.id.avi);
        notFoundText = view.findViewById(R.id.notFoundText);
        setListeners();
    }

    private void setListeners() {
        listView.setOnItemClickListener((adapterView, view1, id, l) -> {
            searchByString(searchListAdapter.getItem(id));
            alertDialog.dismiss();
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 250;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                progressBar.show();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                timer.cancel();
                progressBar.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                notFoundText.setVisibility(View.GONE);
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                progressBar.show();
                                List<String> findLists = new LinkedList<>();
                                String searchText = searchBox.getText().toString();
                                if (!searchText.equals("")) {
                                    switch (viewPager.getCurrentItem()) {
                                        case 0: {
                                            Animes api = Api.getAnimes();
                                            namesFilter.setParam("search", searchText);
                                            Call<List<AnimeSimple>> call;
                                            if (namesFilter.isParamNotExist("mylist")) {
                                                call = api.getList(namesFilter.getParams(), null);
                                            } else {
                                                call = api.getList(namesFilter.getParams(), UserInfoHandler.ACCESS_TOKEN);
                                            }
                                            call.enqueue(new Callback<List<AnimeSimple>>() {
                                                @RequiresApi(api = Build.VERSION_CODES.N)
                                                @Override
                                                public void onResponse(Call<List<AnimeSimple>> call, Response<List<AnimeSimple>> response) {
                                                    if (response.body() != null) {
                                                        for (AnimeSimple anime : response.body()) {
                                                            if (anime.getRussian() != null) {
                                                                findLists.add(anime.getRussian());
                                                            } else {
                                                                findLists.add(anime.getName());
                                                            }
                                                        }
                                                        searchListAdapter = new ArrayAdapter<>(activity, R.layout.search_list_item, R.id.search_list_text, findLists);
                                                        listView.setAdapter(searchListAdapter);
                                                        progressBar.hide();
                                                        listView.setVisibility(View.VISIBLE);
                                                        if (findLists.isEmpty()) {
                                                            notFoundText.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<List<AnimeSimple>> call, Throwable t) {

                                                }
                                            });
                                            break;
                                        }
                                        case 1: {
                                            Mangas api = Api.getMangas();
                                            namesFilter.setParam("search", searchText);
                                            Call<List<MangaSimple>> call;
                                            if (namesFilter.isParamNotExist("mylist")) {
                                                call = api.getList(namesFilter.getParams(), null);
                                            } else {
                                                call = api.getList(namesFilter.getParams(), UserInfoHandler.ACCESS_TOKEN);
                                            }
                                            call.enqueue(new Callback<List<MangaSimple>>() {
                                                @RequiresApi(api = Build.VERSION_CODES.N)
                                                @Override
                                                public void onResponse(Call<List<MangaSimple>> call, Response<List<MangaSimple>> response) {
                                                    if (response.body() != null) {
                                                        for (MangaSimple manga : response.body()) {
                                                            if (manga.getRussian() != null) {
                                                                findLists.add(manga.getRussian());
                                                            } else {
                                                                findLists.add(manga.getName());
                                                            }
                                                        }
                                                        searchListAdapter = new ArrayAdapter<>(activity, R.layout.search_list_item, R.id.search_list_text, findLists);
                                                        listView.setAdapter(searchListAdapter);
                                                        progressBar.hide();
                                                        listView.setVisibility(View.VISIBLE);
                                                        if (findLists.isEmpty()) {
                                                            notFoundText.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<List<MangaSimple>> call, Throwable t) {

                                                }
                                            });
                                            break;
                                        }
                                        case 2: {
                                            Ranobes api = Api.getRanobe();
                                            namesFilter.setParam("search", searchText);
                                            Call<List<RanobeSimple>> call;
                                            if (namesFilter.isParamNotExist("mylist")) {
                                                call = api.getList(namesFilter.getParams(), null);
                                            } else {
                                                call = api.getList(namesFilter.getParams(), UserInfoHandler.ACCESS_TOKEN);
                                            }
                                            call.enqueue(new Callback<List<RanobeSimple>>() {
                                                @RequiresApi(api = Build.VERSION_CODES.N)
                                                @Override
                                                public void onResponse(Call<List<RanobeSimple>> call, Response<List<RanobeSimple>> response) {
                                                    if (response.body() != null) {
                                                        for (RanobeSimple ranobe : response.body()) {
                                                            if (ranobe.getRussian() != null) {
                                                                findLists.add(ranobe.getRussian());
                                                            } else {
                                                                findLists.add(ranobe.getName());
                                                            }
                                                        }
                                                        searchListAdapter = new ArrayAdapter<>(activity, R.layout.search_list_item, R.id.search_list_text, findLists);
                                                        listView.setAdapter(searchListAdapter);
                                                        progressBar.hide();
                                                        listView.setVisibility(View.VISIBLE);
                                                        if (findLists.isEmpty()) {
                                                            notFoundText.setVisibility(View.VISIBLE);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<List<RanobeSimple>> call, Throwable t) {

                                                }
                                            });
                                            break;
                                        }
                                    }
                                }
                            }
                        },
                        DELAY
                );
                if (searchBox.getText().toString().equals("")) {
                    searchListAdapter = new ArrayAdapter<>(activity, R.layout.items_view_layout, R.id.text1, Collections.emptyList());
                    listView.setAdapter(searchListAdapter);
                    progressBar.hide();
                }
            }
        });

        searchBox.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchByString(searchBox.getText().toString());
                alertDialog.dismiss();
                handled = true;
            }
            return handled;
        });
    }
}