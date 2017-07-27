package zechat.android.training.zemoso.zechat.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import zechat.android.training.zemoso.zechat.java_beans.Startup;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class VolleyJsonDownload extends IntentService {

    private static final String TAG = VolleyJsonDownload.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private JsonArrayRequest mJsonArrayRequest;
    private JSONObject jsonObject;
    private String url;

    private Startup startup;

    public VolleyJsonDownload(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        url = intent.getStringExtra("url");
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        mJsonArrayRequest = new JsonArrayRequest(
                url, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,response.toString());
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                for(int i=0;i<response.length();i++){
                    try {
                        jsonObject = response.getJSONObject(i);
                        Log.d(TAG,jsonObject.toString());
                        startup = new Startup();
                        startup.setJsonObject(jsonObject.getJSONObject("data").toString());
                        startup.setId(jsonObject.getInt("id"));
                        realm.insertOrUpdate(startup);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                realm.commitTransaction();
                realm.close();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.toString());
            }
        });
        mRequestQueue.add(mJsonArrayRequest);
    }

}
