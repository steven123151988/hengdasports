package com.international.wtw.sports.json;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/23.
 */

public class SummaryDetailsResListBean {

    private List<SummaryDetailsResBean> reslist;

    public List<SummaryDetailsResBean> getReslist() {
        return reslist;
    }

    public void setReslist(List<SummaryDetailsResBean> reslist) {
        this.reslist = reslist;
    }

    public SummaryDetailsResListBean() {
        super();
    }

    public SummaryDetailsResListBean(List<SummaryDetailsResBean> reslist) {
        this.reslist = reslist;
    }

    @Override
    public String toString() {
        return "SummaryDetailsResListBean{" +
                "reslist=" + reslist +
                '}';
    }
}
