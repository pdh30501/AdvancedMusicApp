package com.example.myapplication.views.panels;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.StateFragmentAdapter;
import com.example.myapplication.ui.fragments.FragmentHome;
import com.example.myapplication.ui.fragments.FragmentLibrary;
import com.example.myapplication.ui.fragments.FragmentSettings;
import com.realgear.multislidinguppanel.BasePanelView;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.readable_bottom_bar.ReadableBottomBar;

public class RootNavigationBarPanel extends BasePanelView {

    private ViewPager2 rootViewPager;
    private ReadableBottomBar rootNavigationBar;

    public RootNavigationBarPanel(@NonNull Context context, MultiSlidingUpPanelLayout panelLayout) {
        super(context, panelLayout);

        getContext().setTheme(R.style.Theme_MyApplication);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_root_navigation_bar, this,true);
    }

    @Override
    public void onCreateView() {
        // The panel will be collapsed on start of application
        this.setPanelState(MultiSlidingUpPanelLayout.COLLAPSED);
        // The panel will slide up and down
        this.setSlideDirection(MultiSlidingUpPanelLayout.SLIDE_VERTICAL);
        // Sets the panels peak height
        this.setPeakHeight(getResources().getDimensionPixelSize(R.dimen.navigation_bar_height));
    }

    @Override
    public void onBindView() {
        rootViewPager =getMultiSlidingUpPanel().findViewById(R.id.root_view_pager);
        rootNavigationBar=findViewById(R.id.root_navigation_bar);

        StateFragmentAdapter adapter=new StateFragmentAdapter(getSupportFragmentManager(),getLifecycle());

        adapter.addFragment(new FragmentHome());
        adapter.addFragment(new FragmentLibrary());
        adapter.addFragment(new FragmentSettings());

        rootViewPager.setAdapter(adapter);
        rootNavigationBar.setupWithViewPager2(rootViewPager);



    }

    @Override
    public void onPanelStateChanged(int panelSate) {

    }
}
