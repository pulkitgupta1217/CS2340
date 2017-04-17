package com.cs2340.WaterNet.Facade;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cs2340.WaterNet.R;

/**
 * Created by Pulkit Gupta on 4/6/2017.
 */

public class PurityReportHolder extends RecyclerView.ViewHolder {
    private final TextView overallConditionTV, locationTV, infoTV, contaminant_PPM_TV, virus_PPM_TV;

    /**
     * purity report wrapper
     * @param itemView the items passed from the activity
     */
    public PurityReportHolder(View itemView) {
        super(itemView);
        overallConditionTV = (TextView) itemView.findViewById(R.id.overallcondition_view);
        locationTV = (TextView) itemView.findViewById(R.id.plocation_view);
        infoTV = (TextView) itemView.findViewById(R.id.pcreate_info_view);
        virus_PPM_TV = (TextView) itemView.findViewById(R.id.virus_ppm_view);
        contaminant_PPM_TV = (TextView) itemView.findViewById(R.id.contaminant_ppm_view);
    }

    /**
     * set text view for overall condition
     * @param name to string of overall condition
     */
    public void setOverallConditionTV(String name) {
        overallConditionTV.setText(name);
    }

    /**
     * set text view for location
     * @param text location as text
     */
    public void setLocationTV(String text) {
        locationTV.setText(text);
    }

    /**
     * set text view of info
     * @param text info as text view
     */
    public void setInfoTV(String text) { infoTV.setText(text); }

    /**
     * set text view of contaminant
     * @param text contaminant as text
     */
    public void setContaminant_PPM_TV(String text) {
        contaminant_PPM_TV.setText(text);
    }

    /**
     * set text view of virus
     * @param text virus as text
     */
    public void setVirus_PPM_TV(String text) {
        virus_PPM_TV.setText(text);
    }


}
