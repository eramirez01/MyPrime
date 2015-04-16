package com.erz.myprimes.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.erz.myprimes.R;
import com.erz.myprimes.data.Result;

public class ResultDialog extends DialogFragment {

    Result result;

    public static ResultDialog newInstance(Result result) {
        ResultDialog dialog = new ResultDialog();
        dialog.result = result;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle){
        Dialog d = super.onCreateDialog(bundle);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        return d;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        View V = inflater.inflate(R.layout.result_dialog, group, false);
        TextView name = (TextView) V.findViewById(R.id.name);
        TextView time = (TextView) V.findViewById(R.id.time);
        name.setText("Number of Primes = " + result.getCount());
        time.setText("Search time = " + result.getTime() + "ms");
        return V;
    }
}
