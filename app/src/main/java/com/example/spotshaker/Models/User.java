package com.example.spotshaker.Models;

public class User {
    private String login;
    private String name;
    private String sessionId;

    private User()
    {
    }

    private  static User currentUser = null;

    public static User getCurrentUser()
    {
        if(currentUser == null)
        {
            currentUser = new User();
        }
        return currentUser;
    }

    public static void setCurrentUser(User user)
    {
        currentUser = user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
