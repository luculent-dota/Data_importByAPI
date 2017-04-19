package com.luculent.data.entity;

import java.util.List;

/**
 * Created by luculent on 2017/4/19.
 */
public class Body {
    private  String total;
    private  String page;
    private  String sql;

    private List list;

    public Body() {
    }

    public Body(String total, String page, String sql,List list) {
        this.total = total;
        this.page = page;
        this.sql = sql;
        this.list=list;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
