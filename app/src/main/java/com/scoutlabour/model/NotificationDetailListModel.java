package com.scoutlabour.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nkDroid Solutions on 04-Sep-17.
 */

public class NotificationDetailListModel {

    @SerializedName("result")
    public ArrayList<NotificationDetailModel>notificationDetailModels;

    @SerializedName("total")
    public int total;


}
