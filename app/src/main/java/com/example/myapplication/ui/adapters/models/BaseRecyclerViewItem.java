package com.example.myapplication.ui.adapters.models;

public abstract class BaseRecyclerViewItem {
    public  enum ItemType{
        PLAYLIST, SONG
    }

    private  final String m_vTitle;
    private  final ItemType m_vItemType;

    public BaseRecyclerViewItem(String title, ItemType itemType) {
        this.m_vTitle = title;
        this.m_vItemType = itemType;
    }

    public String getTitle()
    {
        return this.m_vTitle;
    }

    public ItemType getItemType()
    {
        return this.m_vItemType;
    }

    public abstract int getHashCode();

}
