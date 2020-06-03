package com.google.sps.data;

public final class User{

    private int ID;
    private String username;
    private String passwordHash;

    public User(int ID, String username, String password){
        this.ID = ID;
        this.username = username;
        this.passwordHash = hash(password);
    }

    //TODO: Secure hashing. Probably will use JAVA's SHA256 implementation. 
    private String hash(String input){
        return input;
    }

}
