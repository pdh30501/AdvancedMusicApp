package com.example.myapplication.ui.adapters.models;

import com.example.mediaplayer.model.Playlist;

public class PlaylistRecyclerViewItem extends BaseRecyclerViewItem{
    private Playlist mPlaylist;

    public PlaylistRecyclerViewItem(Playlist playlist) {
        super(playlist.getName(), ItemType.PLAYLIST);
        this.mPlaylist = playlist;
    }

    public Playlist getPlaylist() {
        return this.mPlaylist;
    }

    public Integer getPlaylistId() {
        return convertStringToInt(this.mPlaylist.getId());
    }
    public  String getLink()
    {
        return this.mPlaylist.getTracks().toString();
    }

    @Override
    public int getHashCode() {
        return mPlaylist.getId().hashCode();
    }

    public static int convertStringToInt(String input) {
        int base = 36; // Hệ cơ số (10 số và 26 chữ cái)
        int result = 0;

        // Chuyển đổi ký tự thành số theo hệ
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int digitValue;
            if (Character.isDigit(c)) {
                digitValue = Character.getNumericValue(c);
            } else if (Character.isLetter(c) && Character.toUpperCase(c) >= 'A' && Character.toUpperCase(c) <= 'Z') {
                digitValue = Character.toUpperCase(c) - 'A' + 10; // Giá trị bắt đầu từ 10 cho các chữ cái
            } else {
                // Bỏ qua các ký tự không phải chữ cái hoặc số, hoặc nằm ngoài phạm vi hệ
                continue;
            }
            // Cập nhật kết quả bằng cách nhân với hệ cơ số và cộng thêm giá trị ký tự
            result = result * base + digitValue;
        }

        return result;
    }

    public String getDescription() {
        return this.mPlaylist.getDescription();
    }

    public String getImg() {
        return this.mPlaylist.getImg();
    }
}
