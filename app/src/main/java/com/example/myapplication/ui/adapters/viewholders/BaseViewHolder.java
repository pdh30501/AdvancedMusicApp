package com.example.myapplication.ui.adapters.viewholders;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.adapters.models.BaseRecyclerViewItem;

public class BaseViewHolder extends RecyclerView.ViewHolder {


    public  enum ViewType {//This viewtype will be used to decide which type of song layout was created list/grids
        LIST
    }

    private  final ViewType m_vViewType;
    public BaseViewHolder(@NonNull View itemView, ViewType viewTye) {
        super(itemView);

        this.m_vViewType = viewTye;
    }

    public void onBindViewHolder(BaseRecyclerViewItem viewItem) {

    }

    public   <T extends android.view.View> T findViewById(@IdRes int id)
    {
        return this.itemView.findViewById(id);
    }
}
