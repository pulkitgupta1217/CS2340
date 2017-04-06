package com.cs2340.WaterNet.Model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cs2340.WaterNet.R;

/**
 * Created by pulki on 4/6/2017.
 */

public class PurityReportHolder extends RecyclerView.ViewHolder {
    private final TextView contaminantTV, overallconditionTV, virusTV, locationTV, infoTV;

    public PurityReportHolder(View itemView) {
        super(itemView);
        contaminantTV = (TextView) itemView.findViewById(R.id.contaminant_view);
        virusTV = (TextView) itemView.findViewById(R.id.virus_view);
        overallconditionTV = (TextView) itemView.findViewById(R.id.overallcondition_view);
        locationTV = (TextView) itemView.findViewById(R.id.plocation_view);
        infoTV = (TextView) itemView.findViewById(R.id.pcreate_info_view);
    }

    public void setOverallConditionTV(String name) {
        overallconditionTV.setText(name);
    }

    public void setVirusTV(String text) {
        virusTV.setText(text);
    }

    public void setContaminantTV(String text) {
        contaminantTV.setText(text);
    }

    public void setLocationTV(String text) {
        locationTV.setText(text);
    }

    public void setInfoTV(String text) { infoTV.setText(text); }
}