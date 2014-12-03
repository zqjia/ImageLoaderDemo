package com.example.imageloaderdemo;

import java.util.List;

import com.example.imageloaderdemo.GridViewAdapter.Holder;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ThumbnailGridView extends BaseAdapter {
    private static final String TAG = "GridViewAdapter";
    
    private static boolean isDebug = true;
    
    private Context mContext;
    private List<String> mImageEntityList;
    
    private ImageLoader mImageLoader;
    private DisplayImageOptions mDisplayImageOptions;
    
    
    static class Holder {
        ImageView imageView;
    
    }
    
    
    public GridViewAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mImageEntityList = list;
        
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
                                                    .threadPoolSize(3)
                                                    .memoryCache(new WeakMemoryCache())
                                                    .build();
        this.mImageLoader.init(config);
        
        this.mDisplayImageOptions = new DisplayImageOptions.Builder()
                                                    .showImageForEmptyUri(R.drawable.ic_launcher)
                                                    .showImageOnFail(R.drawable.ic_launcher)
                                                    .showImageOnLoading(R.drawable.ic_launcher)
                                                    .cacheInMemory(true)
                                                    .cacheOnDisk(true)
                                                    .bitmapConfig(Bitmap.Config.RGB_565)
                                                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)     //不使用内存缓存
                                                    .build();
        
    }
    
    
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        Log.d(TAG, this.mImageEntityList.size() + "");
        return this.mImageEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return this.mImageEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        
        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        Holder holder = new Holder();
        ImageEntity imageEntity = this.mImageEntityList.get(position);
        
        
        
        
        if(convertView == null) {
            if(layoutInflater != null) {
                convertView = layoutInflater.inflate(R.layout.image_item, null);
            }
  
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_content);
            holder.imageName = (TextView) convertView.findViewById(R.id.image_name);
            holder.imageSize = (TextView) convertView.findViewById(R.id.image_size);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        
        if(imageEntity != null) {
            
            holder.imageName.setText(imageEntity.getName());
            holder.imageSize.setText(imageEntity.getSize() + "");
            //此处使用UIL来加载图片
             this.mImageLoader.displayImage(Scheme.FILE.wrap(imageEntity.getPath()), holder.imageView, this.mDisplayImageOptions);
        } else {
            if(isDebug) {
                Log.d(TAG, "image entity is empty");
            }
        }
        
        return convertView;
    }
}
