package zechat.android.training.zemoso.zechat.utils;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by vin on 24/7/17.
 */

public class ZeChatApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application","Started");
        Fabric.with(this, new Crashlytics());
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
//        Realm.deleteRealm(configuration);
        Realm.setDefaultConfiguration(configuration);
    }



}
