package com.conways.pickphoto;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements PhotoPickedAdapter.OnItemClick {

    private RecyclerView rvPicdedPhotos;
    private List<String> pickedPhotos;
    private PhotoPickedAdapter photoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        update(new ArrayList<String>());
    }

    private void init() {
        rvPicdedPhotos = findViewById(R.id.pick_list);
        rvPicdedPhotos.setLayoutManager(new GridLayoutManager(this, 4));
    }

    private void update(List<String> Photos) {
        if (null == pickedPhotos) {
            pickedPhotos = new ArrayList<>();
        }
        pickedPhotos.clear();
        pickedPhotos.addAll(Photos);
        if (null == photoAdapter) {
            photoAdapter = new PhotoPickedAdapter(pickedPhotos, this);
            rvPicdedPhotos.setAdapter(photoAdapter);
            photoAdapter.setOnItemClick(this);
        } else {
            photoAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    return;
                } else {
                    toPhoto();
                }
                break;

            default:
                break;
        }
    }

    private void getReadPhotoPermission() {
        int has = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (has == PackageManager.PERMISSION_GRANTED) {
            toPhoto();
        } else {
            String[] permissionList = {Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, permissionList, 0);
        }
    }

    private void toPhoto() {
        toTargetActivity(PhotoListActivity.class);
    }



    @Override
    public void itemClick(int position, boolean canPick) {
        if (canPick){
            getReadPhotoPermission();
        }
    }
}
