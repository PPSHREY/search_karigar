package com.scoutlabour.model.labour;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nkdroid on 27/11/17.
 */

public class RegistrationLabourDetailModel {

    @SerializedName("worker_id")
    public String worker_id;

    @SerializedName("name")
    public String name;

    @SerializedName("email")
    public String email;

    @SerializedName("mobile")
    public String mobile;

    @SerializedName("is_active")
    public String is_active;

    @SerializedName("message")
    public String message;

}
