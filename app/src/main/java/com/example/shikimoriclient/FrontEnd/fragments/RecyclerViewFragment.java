package com.example.shikimoriclient.FrontEnd.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.example.shikimoriclient.BackEnd.api.Api;
import com.example.shikimoriclient.BackEnd.api.anime.Animes;
import com.example.shikimoriclient.BackEnd.api.manga.Mangas;
import com.example.shikimoriclient.BackEnd.api.ranobe.Ranobes;
import com.example.shikimoriclient.BackEnd.dao.anime.AnimeSimple;
import com.example.shikimoriclient.BackEnd.dao.manga.MangaSimple;
import com.example.shikimoriclient.BackEnd.dao.ranobe.RanobeSimple;
import com.example.shikimoriclient.BackEnd.filter.SearchFilter;
import com.example.shikimoriclient.FrontEnd.adapters.RecyclerAdapter;
import com.example.shikimoriclient.R;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shikimoriclient.MainActivity.loggedIn;
import static com.example.shikimoriclient.BackEnd.data.UserInfoHandler.ACCESS_TOKEN;
import static com.github.florent37.materialviewpager.MaterialViewPagerHelper.registerRecyclerView;

public class RecyclerViewFragment extends android.support.v4.app.Fragment implements Updatable {
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;
    private SearchFilter filter;
    private int type;
    private AVLoadingIndicatorView progressBar;
    private TextView notFoundView;
    private PullRefreshLayout pullRefreshLayout;

    private static Bundle instanceBundle = new Bundle();

    private int pageNumber = 1;
    private boolean isLoading = true;
    private int pastVisibleItems = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private int previousTotal = 0;
    private int viewThreshold = 20;

    public static android.support.v4.app.Fragment newInstance(int id) {
        switch (id) {
            case 0: {
                instanceBundle.putInt("Type", 0);
                break;
            }
            case 1: {
                instanceBundle.putInt("Type", 1);
                break;
            }
            case 2: {
                instanceBundle.putInt("Type", 2);
                break;
            }
        }
        return new RecyclerViewFragment();
    }

    public RecyclerViewFragment() {
        type = instanceBundle.getInt("Type");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycle_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComp(view);
        initializeAdapter();
        registerRecyclerView(getActivity(), recyclerView);
    }

    private void initializeComp(View view) {
        recyclerView = (view.findViewById(R.id.recyclerView));
        progressBar = view.findViewById(R.id.avi);
        notFoundView = view.findViewById(R.id.notFoundText);
        pullRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        layoutManager = new GridLayoutManager(getContext(), 2);
        filter = new SearchFilter();
        progressBar.show();
        Api.initalize();
        setCompConfiguration();
        setListeners();
    }

    private void initializeAdapter() {
        switch (type) {
            case 0: {
                Animes api = Api.getAnimes();
                Call<List<AnimeSimple>> call;
                if (!loggedIn) {
                    call = api.getList(filter.getParams(), null);
                } else {
                    call = api.getList(filter.getParams(), ACCESS_TOKEN);
                }
                call.enqueue(new Callback<List<AnimeSimple>>() {
                    @Override
                    public void onResponse(Call<List<AnimeSimple>> call, Response<List<AnimeSimple>> response) {
                        recyclerAdapter = new RecyclerAdapter(response.body());
                        recyclerView.setAdapter(recyclerAdapter);
                        progressBar.hide();
                        if (recyclerAdapter.getItemCount() == 0) {
                            notFoundView.setText(R.string.not_found);
                            notFoundView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AnimeSimple>> call, Throwable t) {
                        progressBar.hide();
                        recyclerAdapter = new RecyclerAdapter(Collections.emptyList());
                        recyclerView.setAdapter(recyclerAdapter);
                        notFoundView.setText(R.string.smth_wrong);
                        notFoundView.setVisibility(View.VISIBLE);
                    }
                });
                break;
            }
            case 1: {
                Mangas api = Api.getMangas();
                Call<List<MangaSimple>> call;
                if (!loggedIn) {
                    call = api.getList(filter.getParams(), null);
                } else {
                    call = api.getList(filter.getParams(), ACCESS_TOKEN);
                }
                call.enqueue(new Callback<List<MangaSimple>>() {
                    @Override
                    public void onResponse(Call<List<MangaSimple>> call, Response<List<MangaSimple>> response) {
                        recyclerAdapter = new RecyclerAdapter(response.body());
                        recyclerView.setAdapter(recyclerAdapter);
                        progressBar.hide();
                        if (recyclerAdapter.getItemCount() == 0) {
                            notFoundView.setText(R.string.not_found);
                            notFoundView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<MangaSimple>> call, Throwable t) {
                        recyclerAdapter = new RecyclerAdapter(Collections.emptyList());
                        recyclerView.setAdapter(recyclerAdapter);
                        progressBar.hide();
                        notFoundView.setText(R.string.smth_wrong);
                        notFoundView.setVisibility(View.VISIBLE);
                    }
                });
                break;
            }
            case 2: {
                Ranobes api = Api.getRanobe();
                Call<List<RanobeSimple>> call;
                if (!loggedIn) {
                    call = api.getList(filter.getParams(), null);
                } else {
                    call = api.getList(filter.getParams(), ACCESS_TOKEN);
                }
                call.enqueue(new Callback<List<RanobeSimple>>() {
                    @Override
                    public void onResponse(Call<List<RanobeSimple>> call, Response<List<RanobeSimple>> response) {
                        recyclerAdapter = new RecyclerAdapter(response.body());
                        recyclerView.setAdapter(recyclerAdapter);
                        progressBar.hide();
                        if (recyclerAdapter.getItemCount() == 0) {
                            notFoundView.setText(R.string.not_found);
                            notFoundView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RanobeSimple>> call, Throwable t) {
                        recyclerAdapter = new RecyclerAdapter(Collections.emptyList());
                        recyclerView.setAdapter(recyclerAdapter);
                        progressBar.hide();
                        notFoundView.setText(R.string.smth_wrong);
                        notFoundView.setVisibility(View.VISIBLE);
                    }
                });
                break;
            }
        }
    }

    private void setCompConfiguration() {
        recyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void setListeners() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItems + viewThreshold)) {
                        pageNumber++;
                        performPagination();
                        isLoading = true;
                    }
                }
            }
        });
        pullRefreshLayout.setOnRefreshListener(() -> {
            update(filter);
            pullRefreshLayout.setRefreshing(false);
        });
    }

    private void performPagination() {
        switch (type) {
            case 0: {
                Animes api = Api.getAnimes();
                filter.setParam("page", Integer.toString(pageNumber));
                Call<List<AnimeSimple>> call;
                if (!loggedIn) {
                    call = api.getList(filter.getParams(), null);
                } else {
                    call = api.getList(filter.getParams(), ACCESS_TOKEN);
                }
                call.enqueue(new Callback<List<AnimeSimple>>() {
                    @Override
                    public void onResponse(Call<List<AnimeSimple>> call, Response<List<AnimeSimple>> response) {
                        recyclerAdapter.addItems(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<AnimeSimple>> call, Throwable t) {

                    }
                });
                break;
            }
            case 1: {
                Mangas api = Api.getMangas();
                filter.setParam("page", Integer.toString(pageNumber));
                Call<List<MangaSimple>> call;
                if (!loggedIn) {
                    call = api.getList(filter.getParams(), null);
                } else {
                    call = api.getList(filter.getParams(), ACCESS_TOKEN);
                }
                call.enqueue(new Callback<List<MangaSimple>>() {
                    @Override
                    public void onResponse(Call<List<MangaSimple>> call, Response<List<MangaSimple>> response) {
                        recyclerAdapter.addItems(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<MangaSimple>> call, Throwable t) {

                    }
                });
                break;
            }
            case 2: {
                Ranobes api = Api.getRanobe();
                filter.setParam("page", Integer.toString(pageNumber));
                Call<List<RanobeSimple>> call;
                if (!loggedIn) {
                    call = api.getList(filter.getParams(), null);
                } else {
                    call = api.getList(filter.getParams(), ACCESS_TOKEN);
                }
                call.enqueue(new Callback<List<RanobeSimple>>() {
                    @Override
                    public void onResponse(Call<List<RanobeSimple>> call, Response<List<RanobeSimple>> response) {
                        recyclerAdapter.addItems(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<RanobeSimple>> call, Throwable t) {

                    }
                });
                break;
            }
        }
    }

    @Override
    public void update(SearchFilter searchFilter) {
        progressBar.show();
        filter = searchFilter;
        filter.getParams().put("page", "1");
        notFoundView.setVisibility(View.INVISIBLE);
        initializeAdapter();
        pageNumber = 1;
        isLoading = true;
        pastVisibleItems = 0;
        visibleItemCount = 0;
        totalItemCount = 0;
        previousTotal = 0;
        viewThreshold = 20;

    }
}


