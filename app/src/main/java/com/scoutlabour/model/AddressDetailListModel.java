package com.scoutlabour.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nkdroid on 5/12/17.
 */

public class AddressDetailListModel {
    @SerializedName("address_list")
    public ArrayList<AddressDetailModel>addressDetailModelArrayList;
}
