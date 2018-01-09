package com.scoutlabour.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nkdroid on 5/12/17.
 */

public class AddressDetailModel {


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


    public AddressDetailModel(String address_name) {
        this.address_name = address_name;
    }
}
