package zechat.android.training.zemoso.zechat.services;

import android.os.AsyncTask;
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

import io.realm.Realm;
import zechat.android.training.zemoso.zechat.java_beans.Startup;

/**
 * Created by zemoso on 26/7/17.
 */

public class ASyncDownload extends AsyncTask<String,Void,Integer> {

    //region Variable Declaration

    private static final String TAG = ASyncDownload.class.getSimpleName();

    //region Network to Database
    private ArrayList<String> results;
    private Startup startup;
    private BufferedReader bufferedReader;
    private String line;
    private String result;
    private Realm realm;
    private JSONObject jsonObject;
    //endregion

    //region Parsing Operations
    private ArrayList<String> items;
    private JSONArray posts;
    private JSONObject post;
    //endregion

    //endregion

    //region Inherited Methods

    @Override
    protected Integer doInBackground(String... strings) {

        try{
            String requestUrl = strings[0];
            InputStream inputStream;
            HttpURLConnection urlConnection;
            URL url = new URL(requestUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            Log.d("Connecting...",urlConnection.toString());
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestMethod("GET");
            Log.d("Download","Finished");
            int statusCode = urlConnection.getResponseCode();
            Log.d("Connecting...", String.valueOf(statusCode));
            if(statusCode == 200){
                Log.d("Trying","Parsing");
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertInputStreamToString(inputStream);
                Log.d(TAG,response);
                results = parseResult(response);
                return 1;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1){
            Log.d(TAG, String.valueOf(results.size()));
//                    TODO: Update Views with results.

//                    TODO: Update Realm with results.
            realm = Realm.getDefaultInstance();
            Log.d(TAG,realm.toString());
            realm.beginTransaction();
            for(String string : results) {
                try {
                    jsonObject = new JSONObject(string);
                    startup = new Startup();
                    startup.setJsonObject(jsonObject.getJSONObject("data").toString());
                    startup.setId(jsonObject.getInt("id"));
                    realm.insertOrUpdate(startup);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            realm.commitTransaction();
            Log.d(TAG, String.valueOf(realm.where(Startup.class).findAll().size()));
            realm.close();
        }
    }
    //endregion

    //region Private Methods

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
