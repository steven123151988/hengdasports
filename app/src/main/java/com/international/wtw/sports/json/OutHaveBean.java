package com.international.wtw.sports.json;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/11/3.
 */

public class OutHaveBean {

    private int pageNo;
    private int pageSize;
    private int count;
    private List<OutHaveListBean> list;
    private int firstResult;
    private int maxResults;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OutHaveListBean> getList() {
        return list;
    }

    public void setList(List<OutHaveListBean> list) {
        this.list = list;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public OutHaveBean() {
        super();
    }

    public OutHaveBean(int pageNo, int pageSize, int count, List<OutHaveListBean> list, int firstResult, int maxResults) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.count = count;
        this.list = list;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
    }

    @Override
    public String toString() {
        return "OutHaveBean{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", count=" + count +
                ", list=" + list +
                ", firstResult=" + firstResult +
                ", maxResults=" + maxResults +
                '}';
    }
}
