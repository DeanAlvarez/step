package com.google.sps.data;

public final class Comment{

    private long commentID;
    private String username;
    private String comment;

    public Comment(long ID, String username, String comment){
        this.commentID = ID;
        this.username = username;
        this.comment = comment;
    }
}