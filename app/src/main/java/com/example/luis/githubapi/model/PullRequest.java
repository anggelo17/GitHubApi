package com.example.luis.githubapi.model;

import java.io.Serializable;

public class PullRequest implements Serializable{

    private String number;
    private String title;
    private String url;
    private String diff_url;

    private User user;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDiff_url() {
        return diff_url;
    }

    public void setDiff_url(String diff_url) {
        this.diff_url = diff_url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public class User implements Serializable{
        private String login;
        private String id;


        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
