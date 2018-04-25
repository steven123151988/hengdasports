package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.view.CustomListView;
import com.international.wtw.lottery.json.HasCloseBean;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/18.
 */

public class ExpandableListViewAdapter3 extends BaseExpandableListAdapter {

    private LayoutInflater mInflater;
    private Context context;
    private List<HasCloseBean> hasCloseBeanList;

    public ExpandableListViewAdapter3(Context context, List<HasCloseBean> hasCloseBeanList) {
        mInflater = mInflater.from(context);
        this.context = context;
        this.hasCloseBeanList = hasCloseBeanList;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return hasCloseBeanList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return hasCloseBeanList.size();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {
        if (view == null) {
            viewChildGroup = new ViewChildGroup();
            view = mInflater.inflate(R.layout.channel_expandablelistview3, null);
            viewChildGroup.tv_rq = (TextView) view.findViewById(R.id.tv_rq);
            viewChildGroup.tv_tzds = (TextView) view.findViewById(R.id.tv_tzds);
            viewChildGroup.tv_xzje = (TextView) view.findViewById(R.id.tv_xzje);
            viewChildGroup.tv_ksje = (TextView) view.findViewById(R.id.tv_ksje);
            viewChildGroup.tv_jrts = (TextView) view.findViewById(R.id.tv_jrts);
            viewChildGroup.img_orientation = (ImageView) view.findViewById(R.id.img_orientation);
            view.setTag(viewChildGroup);
        } else {
            viewChildGroup = (ViewChildGroup) view.getTag();
        }

        viewChildGroup.tv_rq.setText(hasCloseBeanList.get(groupPosition).getDateTime());
        viewChildGroup.tv_tzds.setText(hasCloseBeanList.get(groupPosition).getAllNum());
        viewChildGroup.tv_xzje.setText(hasCloseBeanList.get(groupPosition).getAllTotal());
        viewChildGroup.tv_ksje.setText(hasCloseBeanList.get(groupPosition).getAllWinMoney());
        viewChildGroup.tv_jrts.setText(hasCloseBeanList.get(groupPosition).getAllCut());

        if (isExpanded) {

        } else {

        }

        return view;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return hasCloseBeanList.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return hasCloseBeanList.size();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        if (view == null) {
            viewChildGroup = new ViewChildGroup();
            view = mInflater.inflate(R.layout.channel_expandablelistview_item, null);
            viewChildGroup.listview_item_child = (CustomListView) view.findViewById(R.id.listview_item_child);
            view.setTag(viewChildGroup);
        } else {
            viewChildGroup = (ViewChildGroup) view.getTag();
        }

//        ExpandableListViewChildAdapter adapter = new ExpandableListViewChildAdapter(context, outStandBeanList.get(childPosition).getRes());
//        viewChildGroup.listview_item_child.setAdapter(adapter);
//        setGridViewListener(viewChildGroup.listview_item_child);
//        viewChildGroup.listview_item_child.setSelector(new ColorDrawable(Color.TRANSPARENT));

        return view;
    }

    /**
     * 设置listview点击事件监听
     */
    private void setGridViewListener(final CustomListView customListView) {
        customListView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view instanceof TextView) {
//                    如果想要获取到哪一行，则自定义gridview的adapter，item设置2个textview一个隐藏设置id，显示哪一行
//                    TextView tv = (TextView) view;
                }
            }
        });
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    ViewChildGroup viewChildGroup;

    class ViewChildGroup {
        TextView tv_rq, tv_tzds, tv_xzje, tv_ksje, tv_jrts;
        ImageView img_orientation;
        CustomListView listview_item_child;
    }
}
