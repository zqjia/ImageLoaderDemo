package com.example.imageloaderdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.imageloaderdemo.GridViewAdapter.Holder;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ImageAdapter extends BaseExpandableListAdapter {

	private static final String TAG = "ImageAdapter";
	
	private Context mContext;
	private List<String> mGroupList;
	private List<ArrayList<ImageEntity>> mImageList;
	private LayoutInflater mLayoutInfalte;
	
	private TextView mGroupTextView;
	private MyGridView mGridView;
	
	private ArrayList<HashMap<String, Object> > listImageItem = new ArrayList<HashMap<String, Object>>();
	
	private static final int GRAOUP_ITEM_HEIGHT = 75;
	

	
	public ImageAdapter(Context context, List<String> groupList, List<ArrayList<ImageEntity>> imageList){
		this.mContext = context;
		this.mGroupList = groupList;
		this.mImageList = imageList;
		this.mLayoutInfalte = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}
	
/*	private void initGridViewData(ArrayList<ImageEntity> list) {
	    
	    
		for(int i=0; i<9; ++i) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.ic_launcher);
			map.put("ItemText", "NO." + String.valueOf(i));
			listImageItem.add(map);
		}
	    
	    
	    
	}*/
	
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return this.mGroupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
//		return this.mImageList.get(groupPosition).size();
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
		return this.mImageList.get(groupPosition).get(childPosition);
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
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		TextView textView = getTextView(this.mContext);
		textView.setText(getGroup(groupPosition).toString());
		return textView;
		
	}

	 static class Holder {
	    GridView gridView;
	 }
	
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
	    Holder holder = new Holder();
	    
		if(convertView == null) {
			convertView = this.mLayoutInfalte.inflate(R.layout.child_item, null);
		
			this.mGridView =(MyGridView) convertView.findViewById(R.id.image_gridview);
			this.mGridView.setNumColumns(3);
			this.mGridView.setGravity(Gravity.CENTER);
			this.mGridView.setHorizontalSpacing(10);	
			
			convertView.setTag(holder);
		} else {
		    holder = (Holder) convertView.getTag();
		}
			
			ArrayList<ImageEntity> gridList = this.mImageList.get(groupPosition);
			Log.d(TAG,  "imagelist get group position size is " + gridList.size());
			
/*			initGridViewData(gridList);
			
			SimpleAdapter gridAdapter = new SimpleAdapter(this.mContext, this.listImageItem,
                                                        R.layout.image_item, 
                                                        new String[]{"ImagePath", "ImageName", "ImageSize"},
                                                        new int[]{R.id.image_content, R.id.image_name, R.id.image_size});

			this.mGridView.setAdapter(gridAdapter);*/
			
			
			GridViewAdapter gridAdapter = new GridViewAdapter(this.mContext, gridList);
			this.mGridView.setAdapter(gridAdapter);
			
			this.mGridView.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Toast.makeText(mContext, "be clicked", Toast.LENGTH_SHORT).show();
				}
				
			});
			gridAdapter.notifyDataSetChanged();
			

		return convertView;
		
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
