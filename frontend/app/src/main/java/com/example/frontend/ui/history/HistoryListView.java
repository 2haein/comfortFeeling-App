package com.example.frontend.ui.history;


public class HistoryListView {
    private String titleStr;
    private String scoreStr;
    private String dateStr;
    private String idStr;

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