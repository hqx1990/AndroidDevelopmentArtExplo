package com.bestvike.androiddevelopmentartexploration.nestedList;

import java.util.List;

public class NestedListBean {
    private String name;
    private List<NestedItemBean> nestedItemBeans;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NestedItemBean> getNestedItemBeans() {
        return nestedItemBeans;
    }

    public void setNestedItemBeans(List<NestedItemBean> nestedItemBeans) {
        this.nestedItemBeans = nestedItemBeans;
    }
}
