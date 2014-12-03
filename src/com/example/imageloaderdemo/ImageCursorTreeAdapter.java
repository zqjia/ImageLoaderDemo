package com.example.imageloaderdemo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;

public class ImageCursorTreeAdapter extends CursorTreeAdapter {

    private static final String TAG = "ImageCursorTreeAdapter";
    private LayoutInflater mInflater;
    private Context mContext;
    private GridView mGridView;
    private int mGroupIdColumnIndex;
    
    private List<ImageEntity> mImageEntityList;
    
    
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDisplayImageOptions;
    
    private static boolean isDebug = true;
    
    public ImageCursorTreeAdapter(Cursor cursor, Context context, boolean autoRequery) {
        //初始化的cursor是group的cursor，要注意
        
        super(cursor, context, autoRequery);
        this.mContext = context;
        this.mInflater = (LayoutInflater)this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mGroupIdColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
        this.mImageEntityList = new ArrayList<ImageEntity>();
        //初始化UIL
        initImageLoader();
    }
    
    private void initImageLoader() {
        this.mImageLoader = ImageLoader.getInstance();
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                                                    .Builder(this.mContext)
                                                    .threadPriority(Thread.NORM_PRIORITY - 2)
                                                    .denyCacheImageMultipleSizesInMemory()
                                                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                                                    .build();
        this.mImageLoader.init(config);
        
        this.mDisplayImageOptions = new DisplayImageOptions.Builder()
                                                    .showImageForEmptyUri(R.drawable.ic_launcher)
                                                    .showImageOnFail(R.drawable.ic_launcher)
                                                    .cacheInMemory(true)
                                                    .build();
        
    }
    
    
    @Override
    protected Cursor getChildrenCursor(Cursor groupCursor) {
        // TODO Auto-generated method stub
        Uri.Builder builder = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon();
//        ContentUris.appendId(builder, groupCursor.getLong(mGroupIdColumnIndex));

        Uri imageContentUri = builder.build();

        // The returned Cursor MUST be managed by us, so we use Activity's helper
        // functionality to manage it for us.
        
        Cursor childCursor =  this.mContext.getContentResolver().query(imageContentUri, new String[]{
                                MediaStore.Images.Media.DATA, MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME},
                                null , null, null);
        
        if(isDebug) {
            Log.d(TAG, childCursor.getCount() + "");
        }
        
        return childCursor;
    }

    @Override
    protected View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent) {
        // TODO Auto-generated method stub
        
        View view = this.mInflater.inflate(R.layout.group_item, null);
        bindGroupView(view, context , cursor, isExpanded);
        
        return view;
    }

    @Override
    protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
        // TODO Auto-generated method stub
        if(isDebug) {
            Log.d(TAG, "bindGroupView start");
        }
 
        TextView groupName = (TextView)view.findViewById(R.id.group_name);
        String groupText = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
        groupName.setText(groupText);
        
    }

    @Override
    protected View newChildView(Context context, Cursor cursor, boolean isLastChild,
            ViewGroup parent) {
        // TODO Auto-generated method stub
        //没有使用GridView显示
//        View view = this.mInflater.inflate(R.layout.image_item, null);
        View view = this.mInflater.inflate(R.layout.child_item, null);
        bindChildView(view, context, cursor, isLastChild);
         
        return view;
    }

    @Override
    protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild) {
        // TODO Auto-generated method stub
        if(isDebug) {
            Log.d(TAG, "bindChildView start");
        }
        
/*        //使用GridView实现
        GridView gridView = (GridView) view.findViewById(R.id.image_gridview);
        this.mImageEntityList.clear();
        
        for(int i=0; i<3; ++i) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
            int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
            
            ImageEntity imageEntity = new ImageEntity(path, name, size);
            this.mImageEntityList.add(imageEntity);
            if(!cursor.isLast()){
                cursor.moveToNext();
            }
        }
        
        GridViewAdapter adapter = new GridViewAdapter(this.mContext, this.mImageEntityList);
        gridView.setAdapter(adapter);*/
        
        
        
        //现在直接使用xml布局实现效果，还没有加入GridView的实现
        ImageView imageView = (ImageView)view.findViewById(R.id.image_content);
        TextView imageName = (TextView)view.findViewById(R.id.image_name);
        TextView imageSize = (TextView)view.findViewById(R.id.image_size);
        
/*        RelativeLayout rl = new RelativeLayout(this.mContext);
        
        int screenWidth = ScreenUtil.getScreenWidth(this.mContext);
        RelativeLayout.LayoutParams rlParam = new RelativeLayout.LayoutParams(screenWidth/3, 100);*/
        
        
        
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
        int size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
        
        imageName.setText(name);
        imageSize.setText(String.valueOf(size));
        
        path = Scheme.FILE.wrap(path); 
        if(isDebug) {
            Log.d(TAG, path);
        }
        
        this.mImageLoader.displayImage(path, imageView, this.mDisplayImageOptions);
        
        
//        this.mGridView = (GridView)view.findViewById(R.id.image_gridview);
        
        
        
    }

}
