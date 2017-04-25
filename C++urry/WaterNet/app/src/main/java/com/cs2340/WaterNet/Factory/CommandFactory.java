package com.cs2340.WaterNet.Factory;

import android.content.Context;
import android.content.Intent;

import com.cs2340.WaterNet.Command.Command;
import com.cs2340.WaterNet.Command.LoginCommand;
import com.cs2340.WaterNet.Command.PReportCommand;
import com.cs2340.WaterNet.Command.PasswordResetCommand;
import com.cs2340.WaterNet.Command.ReportCommand;
import com.cs2340.WaterNet.Command.SignUpCommand;
import com.cs2340.WaterNet.Command.ViewUsersCommand;

/**
 * Created by pulki on 4/23/2017.
 */

public class CommandFactory {
    private CommandFactory(){}

    public static Command getConsumer(Intent i, Context c) {
        if (i == null || c == null) {
            throw new IllegalArgumentException("invalid intent or context");
        }
        String start = c.getClass().getName();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (start.contains("LoginActivity")) {
            return new LoginCommand(i, c);
        } else if (start.contains("PReportActivity")) {
            return new PReportCommand(i, c);
        } else if (start.contains("ReportActivity")) {
            return new ReportCommand(i, c);
        } else if (start.contains("ResetPasswordActivity")) {
            return new PasswordResetCommand(i, c);
        } else if (start.contains("SignUpActivity")) {
            return new SignUpCommand(i, c);
        } else if (start.contains("ViewUsersActivity")) {
            return new ViewUsersCommand(i, c);
        }
        throw new IllegalArgumentException("invalid intent or context");
    }
}
