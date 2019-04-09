package com.example.cris.programplaner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cris.programplaner.model.ChildData;

import java.util.ArrayList;
import java.util.List;



public class ExpandListViewAdapter  extends BaseExpandableListAdapter {
    private Context ctx = null;
    private LayoutInflater inflater = null;
    private ExpandableListView listview;
    private ArrayList<ExpandData> datalist = new ArrayList<ExpandData>();
    public ExpandListViewAdapter(Context ctx , ExpandableListView listview, List<ExpandData> datalist){
        this.listview = listview;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
        this.datalist.clear();
        this.datalist.addAll(datalist);
    }
    @Override
    public int getGroupCount() {
        return datalist.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return datalist.get(i).getMenus().size();
    }

    @Override
    public Object getGroup(int i) {
        return datalist.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return datalist.get(i).getMenus().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new GroupViewHolder();
            view = inflater.inflate(R.layout.item_parent, viewGroup,false );
            viewHolder.nameTV = view.findViewById(R.id.tv_name);
            viewHolder.arrowView = view.findViewById(R.id.arrowView);
            view.setTag(viewHolder);
        }else{
            viewHolder = (GroupViewHolder) view.getTag();
        }
        if(b){
            viewHolder.arrowView.setImageResource(R.drawable.up);
        }else{
            viewHolder.arrowView.setImageResource(R.drawable.down);
        }
        ExpandData  child = (ExpandData) getGroup(i);
        viewHolder.nameTV.setText(child.getName());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ChildViewHolder();
            view = inflater.inflate(R.layout.item_children, viewGroup,false );
            viewHolder.nameTV = (TextView)view.findViewById(R.id.tv_name);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ChildViewHolder) view.getTag();
        }
        ChildData child = (ChildData) getChild(i,i1);
        viewHolder.nameTV.setText(child.getName());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    class ChildViewHolder{
        TextView nameTV ;

    }
    class GroupViewHolder{
        TextView nameTV ;
        ImageView arrowView ;

    }
}
