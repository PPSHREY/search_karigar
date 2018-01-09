package com.scoutlabour.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nkDroid Solutions on 18-Aug-17.
 */

public class CategoryDetailModel {


    @SerializedName("category_id")
    public String category_id;
    @SerializedName("category_name")
    public String category_name;
    @SerializedName("category_image")
    public String category_image;
    @SerializedName("products")
    private String products;


}
