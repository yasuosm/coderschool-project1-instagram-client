package xyz.annt.instagramclient;

/**
 * Created by annt on 3/13/16.
 */
public class Comment {
    private int createdTime;
    private String text;
    private String username;
    private String profilePicture;

    public Comment() {
    }

    public Comment(int createdTime, String text, String username, String profilePicture) {
        this.createdTime = createdTime;
        this.text = text;
        this.username = username;
        this.profilePicture = profilePicture;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
