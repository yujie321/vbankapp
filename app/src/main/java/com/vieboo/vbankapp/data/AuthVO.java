package com.vieboo.vbankapp.data;


public class AuthVO {

    private Integer id;
    private String authExplain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthExplain() {
        return authExplain == null ? "" : authExplain;
    }

    public void setAuthExplain(String authExplain) {
        this.authExplain = authExplain == null ? "" : authExplain;
    }
}
