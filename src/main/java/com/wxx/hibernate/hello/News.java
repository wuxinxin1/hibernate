package com.wxx.hibernate.hello;

import java.sql.Date;

/**
 * Created by Administrator on 2018/12/22/022.
 */
public class News {
    private Integer id;
    private String title;
    private String auth;

    private Date date;

    public News() {
    }

    public News(String title, String auth, Date date) {
        this.title = title;
        this.auth = auth;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", auth='" + auth + '\'' +
                ", date=" + date +
                '}';
    }
}
