package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.views.panels.RootMediaPlayerPanel;
import com.example.myapplication.views.panels.RootNavigationBarPanel;
import com.realgear.multislidinguppanel.Adapter;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.multislidinguppanel.PanelStateListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MultiSlidingUpPanelLayout panelLayout=findViewById(R.id.root_sliding_up_panel);


        List<Class<?>> items = new ArrayList<>();

        // You add your panels here it can be different classes with different layouts
        // but they all should extend the BasePanelView class with same constructors
        // You can add 1 or more then 1 panels

        items.add(RootMediaPlayerPanel.class);
        items.add(RootNavigationBarPanel.class);


        // This is to listen on all the panels you can add methods or extend the class
        panelLayout.setPanelStateListener(new PanelStateListener(panelLayout));


        panelLayout.setAdapter(new Adapter(this,items));


    }
}