package com.cs2340.WaterNet.Facade;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cs2340.WaterNet.R;

/**
 * Created by pulki on 4/6/2017.
 */

public class PurityReportHolder extends RecyclerView.ViewHolder {
    private final TextView overallconditionTV, locationTV, infoTV, contaminant_PPM_TV, virus_PPM_TV;

    public PurityReportHolder(View itemView) {
        super(itemView);
        overallconditionTV = (TextView) itemView.findViewById(R.id.overallcondition_view);
        locationTV = (TextView) itemView.findViewById(R.id.plocation_view);
        infoTV = (TextView) itemView.findViewById(R.id.pcreate_info_view);
        virus_PPM_TV = (TextView) itemView.findViewById(R.id.virus_ppm_view);
        contaminant_PPM_TV = (TextView) itemView.findViewById(R.id.contaminant_ppm_view);
    }

    public void setOverallConditionTV(String name) {
        overallconditionTV.setText(name);
    }

    public void setLocationTV(String text) {
        locationTV.setText(text);
    }

    public void setInfoTV(String text) { infoTV.setText(text); }

    public void setContaminant_PPM_TV(String text) {
        contaminant_PPM_TV.setText(text);
    }

    public void setVirus_PPM_TV(String text) {
        virus_PPM_TV.setText(text);
    }


}
