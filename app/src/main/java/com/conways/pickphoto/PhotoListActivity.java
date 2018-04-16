package com.conways.pickphoto;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoListActivity extends BaseActivity {

    private RecyclerView rvList;
    private PhotoAllAdapter photoAllAdapter;
    private List<String> paths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        init();
        new LoadPhotosTask().execute();
    }


    private void init() {
        rvList = findViewById(R.id.photo_list);
        rvList.setLayoutManager(new GridLayoutManager(this, 4));
    }

    private void update(List<String> updatePaths) {
        if (null == paths) {
            paths = new ArrayList<>();
        }
        paths.clear();
        paths.addAll(updatePaths);
        if (null == photoAllAdapter) {
            photoAllAdapter = new PhotoAllAdapter(this, paths);
            rvList.setAdapter(photoAllAdapter);
        } else {
            photoAllAdapter.notifyDataSetChanged();
        }
    }


    private void getList() {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = this.getContentResolver();

        //只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_TAKEN);

        if (mCursor == null) {
            return;
        }

        while (mCursor.moveToNext()) {
            String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            Log.d("zzzzzzzzzz", "getList: " + path);
        }
    }

    class LoadPhotosTask extends AsyncTask<String, String, List<String>> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(PhotoListActivity.this);
            dialog.setMessage("加载中。。");
            dialog.show();
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = PhotoListActivity.this.getContentResolver();
            Cursor mCursor = mContentResolver.query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
            List<String> list = new ArrayList<>();
            if (mCursor == null) {
                return list;
            }
            while (mCursor.moveToNext()) {
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                list.add(path);
            }
            return list;
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            dialog.dismiss();
            update(strings);
        }

    }

}
