package com.project.unibibliotek;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogController{
	
	public void makeDialog (String title, String message, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
	}
	
}
