package com.scoutlabour.custom;


import android.content.Context;

import com.scoutlabour.model.AddressDetailListModel;
import com.scoutlabour.model.NewRequestDetailListModel;
import com.scoutlabour.model.RegistrationDetailModel;
import com.scoutlabour.model.SubCategoryDetailListModel;
import com.scoutlabour.model.SubCategoryDetailModel;

import java.util.ArrayList;


public class PrefUtils {

    public static void setUser(RegistrationDetailModel currentUser, Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
        complexPreferences.putObject("user_pref_value", currentUser);
        complexPreferences.commit();
    }

    public static void clearCurrentUser(Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }


    public static RegistrationDetailModel getUser(Context ctx) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_pref", 0);
        RegistrationDetailModel currentUser = complexPreferences.getObject("user_pref_value", RegistrationDetailModel.class);
        return currentUser;
    }

    public static void setAddressList(AddressDetailListModel currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "address_list_prefs", 0);
        complexPreferences.putObject("address_list_prefs_value", currentUser);
        complexPreferences.commit();
    }
    public static AddressDetailListModel getAddressList(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "address_list_prefs", 0);
        AddressDetailListModel currentUser = complexPreferences.getObject("address_list_prefs_value", AddressDetailListModel.class);
        return currentUser;
    }

    public static void setSubCategoryListDetail(SubCategoryDetailListModel currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "sub_category_list_prefs", 0);
        complexPreferences.putObject("sub_category_list_prefs_value", currentUser);
        complexPreferences.commit();
    }
    public static SubCategoryDetailListModel getSubCategoryListDetail(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "sub_category_list_prefs", 0);
        SubCategoryDetailListModel currentUser = complexPreferences.getObject("sub_category_list_prefs_value", SubCategoryDetailListModel.class);
        return currentUser;
    }

    public static void setSubCategoryDetail(SubCategoryDetailModel currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "sub_category_detail_prefs", 0);
        complexPreferences.putObject("sub_category_detail_prefs_value", currentUser);
        complexPreferences.commit();
    }
    public static SubCategoryDetailModel getSubCategoryDetail(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "sub_category_detail_prefs", 0);
        SubCategoryDetailModel currentUser = complexPreferences.getObject("sub_category_detail_prefs_value", SubCategoryDetailModel.class);
        return currentUser;
    }
    public static void setRequestListDetail(NewRequestDetailListModel currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "request_list_prefs", 0);
        complexPreferences.putObject("request_list_prefs_value", currentUser);
        complexPreferences.commit();
    }
    public static NewRequestDetailListModel getRequestListDetail(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "request_list_prefs", 0);
        NewRequestDetailListModel currentUser = complexPreferences.getObject("request_list_prefs_value", NewRequestDetailListModel.class);
        return currentUser;
    }


}
