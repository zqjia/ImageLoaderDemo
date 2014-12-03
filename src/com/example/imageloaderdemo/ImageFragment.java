package com.example.imageloaderdemo;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

public class ImageFragment extends Fragment {

	private static final String TAG = "ImageFragment";
	
	private boolean isDebug = true;
	
	private ExpandableListView mListView;
	
	private List<String> mGroupList;
	private List<ArrayList<ImageEntity>> mChildList;
	private ImageAdapter mAdapter;
//	private ImageCursorTreeAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onStart();
		
		this.mGroupList = new ArrayList<String>();
		
		this.mChildList = new ArrayList<ArrayList<ImageEntity>>();
		
		
		
//		Uri imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;  
		Uri imageUri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        
        Cursor imageGroupCursor = getActivity().getContentResolver().query(imageUri, new String[]{MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME},  "0==0) group by "+ MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " -- (", null, "ASC");
        if(imageGroupCursor != null) {
            imageGroupCursor.moveToFirst();
           while(!imageGroupCursor.isLast()) {
               
               String groupName = imageGroupCursor.getString(imageGroupCursor.getColumnIndex(
                       MediaStore.Images.Media.BUCKET_DISPLAY_NAME)); 
               
               this.mGroupList.add(groupName);
               ArrayList<ImageEntity> list = new ArrayList<ImageEntity>();
               this.mChildList.add(list);
                              
               imageGroupCursor.moveToNext();
           } 
        }
        
        mAdapter = new ImageAdapter(getActivity(), this.mGroupList, this.mChildList);
       
		
	}
	
	




	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.image_loader_fragment, null);
		this.mListView = (ExpandableListView)view.findViewById(R.id.image_display);
		this.mListView.setAdapter(this.mAdapter);
		
		String firstGroupName = (String)this.mAdapter.getGroup(0);
		int firstGroupPosition = 0;
		 if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {
             new QueryTask(firstGroupName, firstGroupPosition).execute();
         } else {
             new QueryTask(firstGroupName, firstGroupPosition).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
         }
		
		this.mListView.setOnGroupClickListener(new OnGroupClickListener(){

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                    long id) {
                // TODO Auto-generated method stub
                boolean expanded = parent.isGroupExpanded(groupPosition);  
                if(!expanded) {
                    
                    String groupName = (String )mAdapter.getGroup(groupPosition);
                   
                    //使用异步获取图像数据
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        new QueryTask(groupName, groupPosition).execute();
                    } else {
                        new QueryTask(groupName, groupPosition).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                    
                    //使用同步方式获取图像数据
                   /* addChildData(groupName, groupPosition);
                    mAdapter.notifyDataSetChanged();*/
                    
                    
                    
                    
                    
                    /*Cursor childCursor =  getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{
                            MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME},
                            MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?" , new String[]{(String) mAdapter.getGroup(groupPosition)}, null);
                    
                    ArrayList<ImageEntity> list = new ArrayList<ImageEntity>();
                    
                    while(!childCursor.isLast()) {
                        String path = childCursor.getString(childCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        String name = childCursor.getString(childCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        int size = childCursor.getInt(childCursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                        
                        ImageEntity imageEntity = new ImageEntity(path, name, size);
                        list.add(imageEntity);
                        childCursor.moveToNext();
                    }
                    
                    mChildList.add(list);
                    mAdapter.notifyDataSetChanged();*/
                }
                
                
                return false;
            }
		    
		});
		
		return view;
	}
	
	private void addChildData(String groupName, int groupPosition) {
	    Cursor childCursor =  getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{
                MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME},
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?" , new String[]{ groupName }, null);
	    
	    int count = childCursor.getCount();
	    
	    childCursor.moveToFirst();
	    
	    for(int i=0; i<count; ++i) {
	        String path = childCursor.getString(childCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String name = childCursor.getString(childCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            int size = childCursor.getInt(childCursor.getColumnIndex(MediaStore.Images.Media.SIZE));
            
            ImageEntity imageEntity = new ImageEntity(path, name, size);
//            list.add(imageEntity);
            mChildList.get(groupPosition).add(imageEntity);
            childCursor.moveToNext();
	    }
	    
	    if(isDebug) {
	        Log.d(TAG, "child get poisition size is " + mChildList.get(groupPosition).size());
	    }
	    
	    
/*	    while(!childCursor.isLast()) {
            String path = childCursor.getString(childCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String name = childCursor.getString(childCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            int size = childCursor.getInt(childCursor.getColumnIndex(MediaStore.Images.Media.SIZE));
            
            ImageEntity imageEntity = new ImageEntity(path, name, size);
            list.add(imageEntity);
            childCursor.moveToNext();
        }
	    
	    if(list != null) {
	        Log.d(TAG,  "list size is " + list.size());
	    }
	    
	    mChildList.get(groupPosition).addAll(list);*/
        
	    
	}
	
	
	private class QueryTask extends AsyncTask<Void, Void, Void> {

	    private String groupName;
	    private int groupPosition;
	    private Cursor cursor;
	    
	    public QueryTask(String name, int position) {
	        this.groupName = name;
	        this.groupPosition = position;
	    }
	    
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            cursor =  getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{
                    MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME},
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME + "=?" , new String[]{ groupName }, null);
            
            cursor.moveToFirst();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            
            int count = cursor.getCount();
            
            for(int i=0; i<count; ++i) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                
                ImageEntity imageEntity = new ImageEntity(path, name, size);
                mChildList.get(this.groupPosition).add(imageEntity);
                cursor.moveToNext();
            }
            
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            
            mAdapter.notifyDataSetChanged();
        }
        
        
	    
	}
	

}
