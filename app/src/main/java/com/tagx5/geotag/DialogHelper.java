package com.tagx5.geotag;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by coryatwater on 6/5/16.
 */
public class DialogHelper {
    public static AlertDialog createErrorDialog(Context context, String title, String message, String yesButton){

        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(yesButton, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }})
                .create();
    }
}
