package com.tagx5.geotag;

import android.app.ProgressDialog;
import android.content.Context;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

/**
 * Created by coryatwater on 6/5/16.
 */
public class LoadingCallback<T> implements AsyncCallback<T> {

    private Context c;
    private ProgressDialog p;

    public LoadingCallback(Context c)
    {
        this.c=c;
        p = new ProgressDialog(c);
        p.setMessage("LOADING LOADING LOADING");

    }
    public LoadingCallback(Context c, String message)
    {
        this.c=c;
        p = new ProgressDialog(c);
        p.setMessage(message);

    }

    @Override
    public void handleResponse(T response) {
        p.dismiss();


    }

    @Override
    public void handleFault(BackendlessFault fault) {
        p.dismiss();
        DialogHelper.createErrorDialog(c,"Backendless Fault", fault.getMessage(), "close").show();

    }

    public void showDialog()
    {
        p.show();
    }

    public void hideDialog(){
        p.hide();
    }
}
