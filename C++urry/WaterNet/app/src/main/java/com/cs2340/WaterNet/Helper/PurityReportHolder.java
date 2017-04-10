package com.cs2340.WaterNet.Helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cs2340.WaterNet.R;

/**
 * Created by pulki on 4/6/2017.
 */

public class PurityReportHolder extends RecyclerView.ViewHolder {
    private final TextView overallconditionTV, locationTV, infoTV, contaminantppmTV, virusppmTV;

    public PurityReportHolder(View itemView) {
        super(itemView);
        overallconditionTV = (TextView) itemView.findViewById(R.id.overallcondition_view);
        locationTV = (TextView) itemView.findViewById(R.id.plocation_view);
        infoTV = (TextView) itemView.findViewById(R.id.pcreate_info_view);
        virusppmTV = (TextView) itemView.findViewById(R.id.virus_ppm_view);
        contaminantppmTV = (TextView) itemView.findViewById(R.id.contaminant_ppm_view);
    }

    public void setOverallConditionTV(String name) {
        overallconditionTV.setText(name);
    }

    public void setLocationTV(String text) {
        locationTV.setText(text);
    }

    public void setInfoTV(String text) { infoTV.setText(text); }

    public void setContaminantppmTV(String text) {
        contaminantppmTV.setText(text);
    }

    public void setVirusppmTV(String text) {
        virusppmTV.setText(text);
    }


}
