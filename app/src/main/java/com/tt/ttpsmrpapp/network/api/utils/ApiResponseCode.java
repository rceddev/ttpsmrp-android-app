package com.tt.ttpsmrpapp.network.api.utils;

public interface ApiResponseCode {
    /**
     * Code for Login response.
     */
    //Errors
    public static final String USER_NOT_ACTIVATE = "2001";
    public static final String EMAIL_NOT_EXIST = "2002";
    public static final String INCORRECT_PASSWORD = "2003";


    /**
     * Code for Register response.
     */
    //Success
    public static final String USER_REGISTERED="1001";
    //Errors
    public static final String USERNAME_REPEATED = "2004";
    public static final String IMAGE_EMPTY = "2005";
    public static final String EMAIL_REPEATED= "2006";
    public static final String PASSWORD_EMPTY = "2007";
    public static final String EMAIL_EMPTY= "2008";

    /**
     * Code for Update User response.
     */
    //Success
    public static final String USER_UPDATE = "1002";

    /**
     * Code for getting user information response
     */
    //Errors
    public static final String  USER_INFORMATION_NOT_FOUND = "2009";

    /**
     * Code for Validate confirmation response.
     */
    //Success
    public static final String LINK_SENT = "1003";
    public static final String ACCOUNT_ACTIVATED = "1005";
    public static final String CODE_VALID = "1006";
    //Errors
    public static final String ERROR_SEND_EMAIL = "2010";
    public static final String CODE_IS_EMPTY = "2011";
    public static final String CODE_INVALID = "2012";

    /**
     * Code for Changing of password response
     */
    //Success
    public static final String PASSWORD_CHANGED = "1004";

}
