package com.example.frontend.ui.history;


import android.graphics.drawable.Drawable;

public class HistoryListView {
    private Drawable iconDrawable ;
    private String titleStr;
    private String scoreStr;
    private String dateStr;
    private String idStr;

    public void setIcon(Drawable icon) {iconDrawable = icon;}
    public void setTitle(String title) {
        titleStr = title;
    }
    public void setScore(String score) {
        scoreStr = score;
    }
    public void setDate(String date) {
        dateStr = date;
    }
    public void setId(String id) {idStr = id;}

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr;
    }
    public String getScore() {
        return this.scoreStr;
    }
    public String getDate() {
        return this.dateStr;
    }
    public String getId() {return this.idStr;}
}