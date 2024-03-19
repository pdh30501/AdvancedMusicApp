package com.example.myapplication.ui;

import androidx.annotation.IdRes;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.views.panels.RootMediaPlayerPanel;
import com.example.myapplication.views.panels.RootNavigationBarPanel;
import com.realgear.multislidinguppanel.Adapter;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.multislidinguppanel.PanelStateListener;

import java.util.ArrayList;
import java.util.List;

public class UIThread {
    private final MainActivity m_vMainActivity;
    private MultiSlidingUpPanelLayout m_vMultiSlidingPanel;
    public UIThread(MainActivity activity)
    {
        this.m_vMainActivity = activity;
        onCreate();
    }

    public  void  onCreate()
    {
        MultiSlidingUpPanelLayout panelLayout=findViewById(R.id.root_sliding_up_panel);


        List<Class<?>> items = new ArrayList<>();

        // You add your panels here it can be different classes with different layouts
        // but they all should extend the BasePanelView class with same constructors
        // You can add 1 or more then 1 panels

        items.add(RootMediaPlayerPanel.class);
        items.add(RootNavigationBarPanel.class);


        // This is to listen on all the panels you can add methods or extend the class
        panelLayout.setPanelStateListener(new PanelStateListener(panelLayout));


        panelLayout.setAdapter(new Adapter(this.m_vMainActivity,items));
    }

    public   <T extends android.view.View> T findViewById(@IdRes int id)
    {
        return this.m_vMainActivity.findViewById(id);
    }
}
