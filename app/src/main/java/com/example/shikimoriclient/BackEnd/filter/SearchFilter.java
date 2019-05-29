package com.example.shikimoriclient.BackEnd.filter;

import com.example.shikimoriclient.BackEnd.data.Searchable;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class SearchFilter {
    private LinkedHashMap<String, String> params;
    private FilterAdapter filterAdapter;
    private Searchable searchFilterData;

    public SearchFilter(FilterAdapter filterAdapter, Searchable searchFilterData) {
        params = new LinkedHashMap<>();
        this.filterAdapter = filterAdapter;
        this.searchFilterData = searchFilterData;
    }

    public SearchFilter() {
        params = new LinkedHashMap<>();
    }

    public SearchFilter(HashMap<String, String> params) {
        this.params = new LinkedHashMap<>();
        this.params.putAll(params);
    }

    private void buildParamsByFilter() {
        for (FilterElement counter : filterAdapter.getCounter().keySet()) {
            if (filterAdapter.getCounter().get(new FilterElement(counter.getGroupId(), counter.getChildId())) == 1) {
                this.setCombineParam(searchFilterData.getParamsNameByColumnId(counter.getGroupId()),
                        searchFilterData.getParamsValueByColumnId().get(counter.getGroupId()).get(counter.getChildId()), false);
            }
            if (filterAdapter.getCounter().get(new FilterElement(counter.getGroupId(), counter.getChildId())) == 2) {
                this.setCombineParam(searchFilterData.getParamsNameByColumnId(counter.getGroupId()),
                        searchFilterData.getParamsValueByColumnId().get(counter.getGroupId()).get(counter.getChildId()), true);
            }
        }
    }

    public void setParam(String param, String value) {
        params.put(param, value);
    }

    public void delParam(String param) {
        params.remove(param);
    }

    private void setCombineParam(String param, String value, boolean subtract) {
        if (params.get(param) != null) {
            if (subtract) {
                params.put(param, params.get(param) + ",!" + value);
            } else {
                params.put(param, params.get(param) + "," + value);
            }
        } else {
            if (subtract) {
                params.put(param, "!" + value);
            } else {
                params.put(param, value);
            }
        }
    }

    public boolean isParamNotExist(String param) {
        return params.get(param) == null;
    }

    public HashMap<String, String> getParams() {
        if (filterAdapter != null) {
            String searchStr = params.get("search");
            String page = params.get("page");
            params.clear();
            if (searchStr != null) {
                params.put("search", searchStr);
            }
            if (page != null) {
                params.put("page", page);
            }
            buildParamsByFilter();
        }
        params.put("limit", "20");
        return params;
    }

    public FilterAdapter getFilterAdapter() {
        return filterAdapter;
    }

    public void rest() {
        filterAdapter.reset();
        params.clear();
    }
}
