package com.tt.ttpsmrpapp.network.api.utils;

public interface ApiResponseCode {
    /**
     * Code of Login response.
     */
    public static final int EMAIL_NOT_EXIST = 2002;
    public static final int INCORRECT_PASSWORD = 2003;
    public static final int USER_NOT_ACTIVATE = 2009;

    /**
     * Code of Register response.
     */
    public static final int USER_REGISTERED = 1001;
    public static final int USERNAME_EXISTS = 2001;
    public static final int IMAGE_NOT_INSERTED = 2004;
    public static final int USER_REGISTERED = 2005;
    public static final int EMAIL_EXISTS= 2006;
    public static final int PASSWORD_NOT_INSERTED = 2007;
    public static final int EMAIL_INVALID= 2008;

    /**
     * Code of Update User response.
     */
    public static final int USER_UPDATE = 1002;
    public static final int LINK_SENT = 1003;
    public static final int ERRROR_SEND_EMAIL = 2010;
    public static final int PASSWORD_CHANGED = 2012;

    /**
     * Code of Validate confirmation code response.
     */
    public static final int ACCOUNT_ACTIVATED = 1004;
    public static final int CODE_IS_EMPTY = 2011;
    public static final int INVALID_CODE = 2012;


}
