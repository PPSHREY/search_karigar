package com.scoutlabour.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nkdroid on 27/11/17.
 */

public class RegistrationDetailModel {

    @SerializedName("user_id")
    public String user_id;
    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("mobile")
    public String mobile;
    @SerializedName("is_active")
    public String is_active;
    @SerializedName("notification_id")
    public String notification_id;
    @SerializedName("message")
    public String message;

}
