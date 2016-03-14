package xyz.annt.instagramclient;

import java.util.ArrayList;

/**
 * Created by annt on 3/10/16.
 */
public class MediaItem {
    private String id;
    private String type;
    private String captionText;
    private String captionFrom;
    private int likesCount;
    private String username;
    private String profilePicture;
    private int createdTime;
    private String imageStandardUrl;
    private int imageStandardWidth;
    private int imageStandardHeight;
    private String videoStandardUrl;
    private int commentsCount;
    private ArrayList<Comment> comments;

    public MediaItem() {

    }

    public MediaItem(String id, String type, String captionText, String captionFrom, int likesCount, String username, String profilePicture, int createdTime, String imageStandardUrl, int imageStandardWidth, int imageStandardHeight, String videoStandardUrl, int commentsCount, ArrayList<Comment> comments) {
        this.id = id;
        this.type = type;
        this.captionText = captionText;
        this.captionFrom = captionFrom;
        this.likesCount = likesCount;
        this.username = username;
        this.profilePicture = profilePicture;
        this.createdTime = createdTime;
        this.imageStandardUrl = imageStandardUrl;
        this.imageStandardWidth = imageStandardWidth;
        this.imageStandardHeight = imageStandardHeight;
        this.videoStandardUrl = videoStandardUrl;
        this.commentsCount = commentsCount;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCaptionText() {
        return captionText;
    }

    public void setCaptionText(String captionText) {
        this.captionText = captionText;
    }

    public String getCaptionFrom() {
        return captionFrom;
    }

    public void setCaptionFrom(String captionFrom) {
        this.captionFrom = captionFrom;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
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

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }

    public String getImageStandardUrl() {
        return imageStandardUrl;
    }

    public void setImageStandardUrl(String imageStandardUrl) {
        this.imageStandardUrl = imageStandardUrl;
    }

    public int getImageStandardWidth() {
        return imageStandardWidth;
    }

    public void setImageStandardWidth(int imageStandardWidth) {
        this.imageStandardWidth = imageStandardWidth;
    }

    public int getImageStandardHeight() {
        return imageStandardHeight;
    }

    public void setImageStandardHeight(int imageStandardHeight) {
        this.imageStandardHeight = imageStandardHeight;
    }

    public String getVideoStandardUrl() {
        return videoStandardUrl;
    }

    public void setVideoStandardUrl(String videoStandardUrl) {
        this.videoStandardUrl = videoStandardUrl;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
