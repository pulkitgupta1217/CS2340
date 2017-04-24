package com.cs2340.WaterNet.Factory;

import android.content.Context;
import android.content.Intent;

/**
 * Created by pulki on 4/23/2017.
 */

public class ConsumerFactory {
    private ConsumerFactory(){}

    public static Consumer getConsumer(Intent i, Context c) {
        if (i == null || c == null) {
            throw new IllegalArgumentException("invalid intent or context");
        }
        String start = c.getClass().getName();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (start.contains("LoginActivity")) {
            return new LoginConsumer(i, c);
        } else if (start.contains("PReportActivity")) {
            return new PReportConsumer(i, c);
        } else if (start.contains("ReportActivity")) {
            return new ReportConsumer(i, c);
        } else if (start.contains("ResetPasswordActivity")) {
            return new PasswordResetConsumer(i, c);
        } else if (start.contains("SignUpActivity")) {
            return new SignUpConsumer(i, c);
        } else if (start.contains("ViewUsersActivity")) {
            return new ViewUsersConsumer(i, c);
        }
        throw new IllegalArgumentException("invalid intent or context");
    }
}
