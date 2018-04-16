package com.conways.pickphoto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Conways on 2018/4/13.
 */

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void toTargetActivity(Class<? extends BaseActivity> c){
        Intent intent=new Intent(this,c);
        startActivity(intent);
    }


}
