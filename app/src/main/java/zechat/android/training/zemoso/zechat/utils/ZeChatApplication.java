package zechat.android.training.zemoso.zechat.utils;

import android.app.Application;
import android.util.Log;

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
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.deleteRealm(configuration);
        Realm.setDefaultConfiguration(configuration);
    }



}
