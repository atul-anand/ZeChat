package zechat.android.training.zemoso.zechat.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class DownloadDataService extends IntentService {

    //region Variable Declaration

    private static final String TAG = DownloadDataService.class.getSimpleName();

    //region Status Codes
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;
    //endregion

    //region Network and Database
    String mUrl;
    Bundle bundle;
    ResultReceiver receiver;
    ArrayList<String> results;

    InputStream inputStream;
    JsonReader jsonReader;
    HttpURLConnection urlConnection;
    URL url;
    String response;
    int statusCode;

    BufferedReader bufferedReader;
    String line;
    String result;

    ArrayList<String> items;
    JSONArray posts;
    JSONObject post;
    //endregion

    //endregion

    public DownloadDataService() {
        super("DownloadDataService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mUrl = intent.getStringExtra("url");
        Log.d(TAG,"Service Started.");
        receiver = intent.getParcelableExtra("receiver");
        Log.d(TAG,receiver.toString());
        bundle = new Bundle();
        Log.d(TAG, mUrl);
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);
        try{
            results = downloadData(mUrl);
            if(results!=null&&results.size()>0){
                bundle.putStringArrayList("result",results);
                receiver.send(STATUS_FINISHED,bundle);
                Log.d(TAG,results.toString());
                Log.d(TAG,bundle.toString());
            }
        } catch(Exception e){
            bundle.putString(Intent.EXTRA_TEXT,e.getMessage());
            receiver.send(STATUS_ERROR,bundle);
        }
        Log.d(TAG,"Service Stopped");
        this.stopSelf();
    }

    //region Supporting Private Methods

    private ArrayList<String> downloadData(String requestUrl) throws Exception {
        Log.d(TAG,"Download");
        url = new URL(requestUrl);
        urlConnection = (HttpURLConnection) url.openConnection();
        Log.d(TAG,urlConnection.toString());
        urlConnection.setRequestProperty("Content-Type","application/json");
        urlConnection.setRequestProperty("Accept","application/json");
        urlConnection.setRequestMethod("GET");
        Log.d(TAG,"Finished");
        statusCode = urlConnection.getResponseCode();
        Log.d(TAG, String.valueOf(statusCode));
        if(statusCode == 200){
            Log.d(TAG,"Parsing");
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            jsonReader = new JsonReader(new InputStreamReader(inputStream));
            response = convertInputStreamToString(inputStream);
            Log.d(TAG,response);
            return parseResult(response);
        } else {
            throw new Exception("Unable to fetch data");
        }
    }

    private ArrayList<String> parseResult(String response) throws JSONException {
        items = new ArrayList<>();
        posts = new JSONArray(response);
        Log.d(TAG,posts.toString());

        for(int i=0;i<posts.length();i++){
            post = posts.optJSONObject(i);
            items.add(post.toString());
            Log.d(TAG,post.toString());
        }
        return items;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        result = "";
        while((line=bufferedReader.readLine())!=null)
            result+=line;
        inputStream.close();
        return result;
    }
    //endregion


}
