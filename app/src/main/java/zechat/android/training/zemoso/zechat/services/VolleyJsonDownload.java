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

    //region Variable Declaration

    private static final String TAG = VolleyJsonDownload.class.getSimpleName();

    //region Network Operations
    private RequestQueue mRequestQueue;
    private JsonArrayRequest mJsonArrayRequest;
    private JSONObject jsonObject;
    private String url;
    //endregion

    //region Database Operations
    private Startup startup;
    Realm realm;
    //endregion

    //endregion

    public VolleyJsonDownload() { super("VolleyJsonDownload"); }
    public VolleyJsonDownload(String name) {
        super(name);
    }

    //region Inherited Methods
    @Override
    protected void onHandleIntent(Intent intent) {
        url = intent.getStringExtra("mUrl");
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mJsonArrayRequest = new JsonArrayRequest(
                url, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,response.toString());
                realm = Realm.getDefaultInstance();
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
        }) {
            //
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        mRequestQueue.add(mJsonArrayRequest);
    }
    //endregion

}
