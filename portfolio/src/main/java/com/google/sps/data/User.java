package com.google.sps.data;

public final class User{

    private long ID;
    private String username;
    private String passwordHash;

    public User(long ID, String username, String password){
        this.ID = ID;
        this.username = username;
        this.passwordHash = hash(password);
    }

    //TODO: Secure hashing. Probably will use JAVA's SHA256 implementation. 
    private static String hash(String input){
        return Interger.toString(input.hashCode());
    }

    /**
    * This class will validate that the given password is the same as the stored hash. It will be used for user validation. 
    */
    public static Boolean validate(String password, String hash){
        return hash(password).equals(hash);
    }

}
