package com.cs2340.WaterNet.Helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cs2340.WaterNet.R;

/**
 * Created by pulki on 4/5/2017.
 */

public class ReportHolder extends RecyclerView.ViewHolder {
    private final TextView waterTypeTV, waterConditionTV, locationTV, infoTV;

    public ReportHolder(View itemView) {
        super(itemView);
        waterTypeTV = (TextView) itemView.findViewById(R.id.watertype_view);
        waterConditionTV = (TextView) itemView.findViewById(R.id.watercondition_view);
        locationTV = (TextView) itemView.findViewById(R.id.location_view);
        infoTV = (TextView) itemView.findViewById(R.id.create_info_view);
    }

    public void setWaterTypeTV(String name) {
        waterTypeTV.setText(name);
    }

    public void setWaterConditionTV(String text) {
        waterConditionTV.setText(text);
    }

    public void setLocationTV(String text) {
        locationTV.setText(text);
    }

    public void setInfoTV(String text) { infoTV.setText(text); }
}