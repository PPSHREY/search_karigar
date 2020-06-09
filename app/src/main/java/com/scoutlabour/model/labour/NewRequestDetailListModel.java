package com.scoutlabour.model.labour;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nkdroid on 9/12/17.
 */

public class NewRequestDetailListModel {
    @SerializedName("result")
    public ArrayList<NewRequestDetailModel>newRequestDetailModelArrayList;
    @SerializedName("total")
    public int total;
}
