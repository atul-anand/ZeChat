package zechat.android.training.zemoso.zechat.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import zechat.android.training.zemoso.zechat.java_beans.Startup;

/**
 * Created by zemoso on 25/7/17.
 */

public class DownloadResultReceiver extends ResultReceiver {

    //region Variable Declaration

    private final static String TAG = DownloadResultReceiver.class.getCanonicalName();

    //region Database Operations
    private ArrayList<String> results;
    private Realm realm;
    private int id;
    private String data;
    private JSONObject jsonObject;
    private Startup startup;
    //endregion

    //endregion

    public DownloadResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        Log.d(TAG,"Received");
        switch (resultCode) {
            case DownloadDataService.STATUS_RUNNING:
//                    setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadDataService.STATUS_FINISHED:
                results = resultData.getStringArrayList("result");
                Log.d(TAG, String.valueOf(results.size()));
//                    TODO: Update Views with results.

//                    TODO: Update Realm with results.

                realm = Realm.getDefaultInstance();
                Log.d(TAG,realm.toString());
                realm.beginTransaction();
                for(String string : results) {
                    try {
                        jsonObject = new JSONObject(string);
                        data = (String) jsonObject.get("data");
                        id = realm.where(Startup.class).findAll().size();
                        if(realm.where(Startup.class).contains("jsonObject", data).count()==0){
                            startup = realm.createObject(Startup.class, id);
                            startup.setJsonObject(data);
                            Log.d(TAG,data);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                realm.commitTransaction();
                Log.d(TAG,"Populated "+realm.where(Startup.class).findAll().size());
                realm.close();
                break;
            case DownloadDataService.STATUS_ERROR:
                Log.d(TAG,"Status Errors");
                break;

        }
    }
    
}
