package com.example.imageloaderdemo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        if (savedInstanceState == null) {	
            getSupportFragmentManager().beginTransaction()
            			.add(R.id.container, new ImageFragment())
            			.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
/*package com.example.imageloaderdemo;

import android.app.ExpandableListActivity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CursorTreeAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ExpandableListActivity {
    private final String TAG = "Expandable1"; 
    ExpandableListView mExpandableList;
    ExpandableListAdapter mAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the expandable list view object
        mExpandableList = getExpandableListView();
        // Set our expandable list adapter
        String[] projection = new String[] {
                Phone._ID,
                Phone.DISPLAY_NAME,
                Phone.NUMBER 
                };
        
        @SuppressWarnings("deprecation")
        Cursor contactData = managedQuery(
                Phone.CONTENT_URI,
                projection, 
                null, 
                null, 
                null);
        //注意这里也只设置了一级项的数据
        mAdapter = new CursorTreeAdapterExample(contactData, this);
        setListAdapter(mAdapter);
        
        Toast.makeText(this, "Total contacts: " + contactData.getCount(), Toast.LENGTH_LONG);
        Log.d("EXPANDABLE", "Total contacts: " + contactData.getCount());
    }
    *//**
     * Adapter implementation
     * @author wk.kho
     *
     *//*
    public class CursorTreeAdapterExample extends CursorTreeAdapter {
        private int mGroupIdColumnIndex;
        private LayoutInflater mInflater;
        //注意这里的游标是一级项的
        public CursorTreeAdapterExample(Cursor cursor, Context context) {
            super(cursor, context);
            
            mGroupIdColumnIndex = cursor.getColumnIndexOrThrow(Phone._ID);
            mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        //注意这里的游标是二级项的
        @Override
        protected void bindChildView(View view, Context context, Cursor cursor, boolean isExpanded) {
            // Bind the related data with this child view
            ((TextView)view).setText(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
        }
        @Override
        protected void bindGroupView(View view, Context context, Cursor cursor, boolean isExpanded) {
            // Bind the related data with this group view
            Log.d(TAG, "bind group view");
            int name = cursor.getColumnIndex(Phone.DISPLAY_NAME);
            ((TextView)view).setText(cursor.getString(name));
        }
        //注意这里通过一次数据库查询才得到了二级项的数据
        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            Uri.Builder builder = Phone.CONTENT_URI.buildUpon();
            ContentUris.appendId(builder, groupCursor.getLong(mGroupIdColumnIndex));

            Uri phoneNumbersUri = builder.build();

            // The returned Cursor MUST be managed by us, so we use Activity's helper
            // functionality to manage it for us.
            return managedQuery(phoneNumbersUri, new String[] {Phone._ID, Phone.NUMBER}, null, null, null);
        }
        @Override
        protected View newChildView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent) {
            Log.d(TAG, "newChildView");
            
            TextView view = (TextView) mInflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            
            view.setText("  (" + cursor.getPosition() + ") " + cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
            
            return view;
        }
        @Override
        protected View newGroupView(Context context, Cursor cursor, boolean isExpanded, ViewGroup parent) {
            Log.d(TAG, "newGroupView");
            TextView view = (TextView) mInflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false);
            String text = "  (" + cursor.getPosition() + ") " + cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
            view.setText(text);
            
            return view;
        }
    }
}*/
