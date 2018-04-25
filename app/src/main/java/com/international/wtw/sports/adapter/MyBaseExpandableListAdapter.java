package com.international.wtw.sports.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.sports.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/6/28.
 */

public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<TreeNode> treeNodes = new ArrayList<TreeNode>();
    private Drawable drawable1, drawable2, drawable3, drawable4;
    private int clickGroupPosition = -1;
    private int clickChildPosition = -1;

    public int getClickGroupPosiotion() {
        return clickGroupPosition;
    }

    public void setClickGroupPosiotion(int clickGroupPosiotion) {
        this.clickGroupPosition = clickGroupPosiotion;
    }

    public int getClickChildPosition() {
        return clickChildPosition;
    }

    public void setClickChildPosition(int clickChildPosition) {
        this.clickChildPosition = clickChildPosition;
    }

    public MyBaseExpandableListAdapter(Context mContext) {
        this.mContext = mContext;
        drawable1 = ContextCompat.getDrawable(mContext, R.mipmap.icon_union_pay);
        drawable2 = ContextCompat.getDrawable(mContext, R.mipmap.icon_alipay);
        drawable3 = ContextCompat.getDrawable(mContext, R.mipmap.icon_wechat_pay);
        drawable4 = ContextCompat.getDrawable(mContext, R.mipmap.icon_offline_pay);
    }

    public List<TreeNode> getTreeNode() {
        return treeNodes;
    }

    public void updateTreeNode(List<TreeNode> nodes) {
        treeNodes = nodes;
    }

    public void removeAll() {
        treeNodes.clear();
    }
//-------------------------------group -----------------------------------------
    @Override
    public int getGroupCount() {
        return treeNodes.size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return treeNodes.get(groupPosition).parent;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LinearLayout groupLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_pay_style, null);
        TextView tv_style = (TextView) groupLayout.findViewById(R.id.tv_style);
        tv_style.setText(treeNodes.get(groupPosition).parent.toString());
        if (treeNodes.get(groupPosition).parent.toString().contains("在线支付")){
            drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
            tv_style.setCompoundDrawables(drawable1, null, null, null);
        }else if (treeNodes.get(groupPosition).parent.toString().contains("支付宝支付")){
            drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
            tv_style.setCompoundDrawables(drawable2, null, null, null);
        }else if (treeNodes.get(groupPosition).parent.toString().contains("微信支付")){
            drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight());
            tv_style.setCompoundDrawables(drawable3, null, null, null);
        }else if (treeNodes.get(groupPosition).parent.toString().contains("手动入款")){
            drawable4.setBounds(0, 0, drawable4.getMinimumWidth(), drawable4.getMinimumHeight());
            tv_style.setCompoundDrawables(drawable4, null, null, null);
        }
        return null;
    }
    //----------------------------------Child----------------------------------------------------
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_child_style, null);
            holder.tv_child_style = (TextView) convertView.findViewById(R.id.tv_child_style);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_child_style.setText(treeNodes.get(groupPosition).childs.get(childPosition).toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return treeNodes.get(groupPosition).childs.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return treeNodes.get(groupPosition).childs.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    class ViewHolder {
        public TextView tv_child_style;
    }
    static public class TreeNode {
        /**
         * 组名
         */
        public Object parent;
        /**
         * 该组的item数据List
         */
        public List<Object> childs = new ArrayList<Object>();//该组的item数据List
    }
}
