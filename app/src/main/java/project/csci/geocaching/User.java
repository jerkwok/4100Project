package project.csci.geocaching;

public class User {

    private int userID;
    private String username;
    private String password;
    private String caches;

    public User(int userID, String username, String password, String caches) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.caches = caches;
    }

    public User(int userID, String username, String caches) {
        this.userID = userID;
        this.username = username;
        this.caches = caches;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaches() {
        return caches;
    }

    public void setCaches(String caches) {
        this.caches = caches;
    }
}
