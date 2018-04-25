package com.international.wtw.sports.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.international.wtw.sports.R;
import com.international.wtw.sports.json.OfflinePayModel;

import java.util.List;

/**
 * Created by XiaoXin on 2017/10/18.
 * 描述：
 */

public class OfflinePayAdapter extends BaseQuickAdapter<OfflinePayModel, BaseViewHolder> {

    private int selectedType;

    public OfflinePayAdapter(int type, @Nullable List<OfflinePayModel> data) {
        super(R.layout.item_offline_paytype, data);
        selectedType = type;
    }

    public void setSelectedType(int selectedType) {
        this.selectedType = selectedType;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, OfflinePayModel item) {
        helper.setImageResource(R.id.ib_pay_type, item.getLargeLogoRes());
        helper.getView(R.id.ib_pay_type).setSelected(selectedType == item.payTypeId);
    }
}
