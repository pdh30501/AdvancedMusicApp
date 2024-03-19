package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ui.UIThread;
import com.example.myapplication.utils.PermissionManager;
import com.example.myapplication.views.panels.RootMediaPlayerPanel;
import com.example.myapplication.views.panels.RootNavigationBarPanel;
import com.realgear.multislidinguppanel.Adapter;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.multislidinguppanel.PanelStateListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UIThread m_vThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionManager.requestPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE, 100);
        PermissionManager.requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, 100);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        {
            if(!Environment.isExternalStorageManager())
            {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        }

        this.m_vThread = new UIThread(this);
    }
}