package com.example.imageloaderdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ThumbnailImageAdapter extends BaseExpandableListAdapter {

    private static final String TAG= "ThumbnailImageAdapter";
    
    private Context mContext;
    private LayoutInflater mLayoutInfalte;
    
    private TextView mGroupTextView;
    private MyGridView mGridView;
    
    private HashMap<String, List<String>> mGroupMap;
    private List<String> mGroupList;
    private List<ArrayList<String>> mChildList;
    
    private static final int GRAOUP_ITEM_HEIGHT = 75;
    
    public ThumbnailImageAdapter(Context context, HashMap<String, List<String>> groupMap) {
        this.mContext = context;
        this.mGroupMap = groupMap;
        
        initImageData(this.mGroupMap);
        
    }
    
    private void initImageData(HashMap<String, List<String>> groupMap) {
       
        if(groupMap.size() == 0) {
            return ;
        }
        
        Iterator<Map.Entry<String, List<String>>> it = groupMap.entrySet().iterator(); 
        
        while (it.hasNext()) {  
            Map.Entry<String, List<String>> entry = it.next();  
            
            String parentFileName = entry.getKey();  
            ArrayList<String> imagePath = (ArrayList<String>)entry.getValue();  
              
            this.mGroupList.add(parentFileName);
            this.mChildList.add(imagePath);             
        } 
    
    }
    
    
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return this.mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return this.mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return this.mChildList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    private TextView getTextView(Context context) {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, GRAOUP_ITEM_HEIGHT);
        
        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        textView.setPadding(30, 0, 0, 0);
        textView.setTextSize((float)20);
        return textView;
    }
    
    
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView textView = getTextView(this.mContext);
        textView.setText(getGroup(groupPosition).toString());
        return textView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return false;
    }

}
