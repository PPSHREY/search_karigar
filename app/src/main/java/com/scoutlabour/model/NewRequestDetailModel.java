package com.scoutlabour.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nkdroid on 9/12/17.
 */

public class NewRequestDetailModel {

    @SerializedName("order_id")
    public String order_id;
    @SerializedName("category_id")
    public String category_id;
    @SerializedName("category_name")
    public String category_name;
    @SerializedName("sub_category_id")
    public String sub_category_id;
    @SerializedName("sub_category_name")
    public String sub_category_name;
    @SerializedName("charges")
    public String charges;
    @SerializedName("service_details")
    public String service_details;
    @SerializedName("address_id")
    public String address_id;
    @SerializedName("address_name")
    public String address_name;
    @SerializedName("address_1")
    public String address_1;
    @SerializedName("address_2")
    public String address_2;
    @SerializedName("address_3")
    public String address_3;
    @SerializedName("city")
    public String city;
    @SerializedName("state")
    public String state;
    @SerializedName("pincode")
    public String pincode;
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("user_name")
    public String user_name;
    @SerializedName("user_email")
    public String user_email;
    @SerializedName("user_mobile")
    public String user_mobile;
    @SerializedName("worker_id")
    public String worker_id;
    @SerializedName("worker_name")
    public String worker_name;
    @SerializedName("worker_mobile")
    public String worker_mobile;
    @SerializedName("worker_profile_image")
    public String worker_profile_image;
    @SerializedName("worker_profile_image_large")
    public String worker_profile_image_large;
    @SerializedName("worker_profile_image_med")
    public String worker_profile_image_med;
    @SerializedName("worker_id_image")
    public String worker_id_image;
    @SerializedName("worker_id_image_large")
    public String worker_id_image_large;
    @SerializedName("worker_id_image_med")
    public String worker_id_image_med;
    @SerializedName("problem_explaination")
    public String problem_explaination;

    @SerializedName("transaction_id")
    public String transaction_id;

    @SerializedName("transaction_amount")
    public String transaction_amount;

    @SerializedName("feedback_given")
    public String feedback_given;


    @SerializedName("service_date")
    public String service_date;
    @SerializedName("service_time")
    public String service_time;
    @SerializedName("assigned_at")
    public String assigned_at;
    @SerializedName("created_at")
    public String created_at;

}
