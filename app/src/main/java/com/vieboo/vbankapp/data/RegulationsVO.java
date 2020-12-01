package com.vieboo.vbankapp.data;

public class RegulationsVO {
    private int id;
    //标题
    private String title;
    //内容
    private String content;
    //0未读 1已读
    private int noticeStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content == null ? "" : content;
    }

    public int getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(int noticeStatus) {
        this.noticeStatus = noticeStatus;
    }
}
