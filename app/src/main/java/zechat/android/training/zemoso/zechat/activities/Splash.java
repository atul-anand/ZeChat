package zechat.android.training.zemoso.zechat.activities;

import android.content.Intent;
import android.os.Bundle;
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
import zechat.android.training.zemoso.zechat.R;
import zechat.android.training.zemoso.zechat.java_beans.Startup;

public class Splash extends FullScreenActivity {

    private static final String TAG = Splash.class.getSimpleName();

    private String mUrl;
    private RequestQueue mRequestQueue;
    private JsonArrayRequest mJsonArrayRequest;

    private JSONObject mJsonObject;
    private Realm mRealm;
    private Startup mStartup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mUrl = getResources().getString(R.string.chat_list_url);

//        Intent intent = new Intent(getApplicationContext(), VolleyJsonDownload.class);
//        intent.putExtra("url", url);
//        Log.d(intent.toString(),url);
//        startService(intent);
//        new ASyncDownload().execute(url);

        Log.d(TAG,"Started");
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        mJsonArrayRequest = new JsonArrayRequest(
                 mUrl, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,response.toString());

                mRealm = Realm.getDefaultInstance();
                mRealm.beginTransaction();
                for(int i=0;i<response.length();i++){
                    try {
                        mJsonObject = response.getJSONObject(i);
                        Log.d(TAG, mJsonObject.toString());
                        mStartup = new Startup();
                        mStartup.setJsonObject(mJsonObject.getJSONObject("data").toString());
                        mStartup.setId(mJsonObject.getInt("id"));
                        mRealm.insertOrUpdate(mStartup);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mRealm.commitTransaction();
                mRealm.close();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.toString());
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        mRequestQueue.add(mJsonArrayRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
