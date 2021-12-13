package com.tt.ttpsmrpapp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.tt.ttpsmrpapp.R;

public class LoadingDialog {
    Activity activiy;
    AlertDialog dialog;

    public LoadingDialog(Activity activiy) {
        this.activiy = activiy;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activiy);

        LayoutInflater inflater = activiy.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_loading, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }

}
