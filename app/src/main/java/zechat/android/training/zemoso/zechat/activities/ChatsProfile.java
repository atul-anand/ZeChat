package zechat.android.training.zemoso.zechat.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import zechat.android.training.zemoso.zechat.R;

public class ChatsProfile extends AppCompatActivity {

    private static final String TAG = ChatsProfile.class.getSimpleName();
    private View decorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        decorView = getWindow().getDecorView();
//        decorView.post(new Runnable() {
//            @Override
//            public void run() {
//                HideStatusBars();
//            }
//        });

        setContentView(R.layout.activity_chats_profile);
//        Toolbar toolbar = findViewById(R.id.app_bar);
//        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        String mUrl = (String) getIntent().getExtras().getCharSequence("imageUrl");
        String mHeading = (String) getIntent().getExtras().getCharSequence("heading");
        String mDescription = (String) getIntent().getExtras().getCharSequence("description");
        String mStatus = (String) getIntent().getExtras().getCharSequence("status");
        setTitle(mHeading);
        Log.d(TAG, mUrl);
        ImageView imageView = (ImageView) findViewById(R.id.collapsing_image);
        TextView textViewHeading = (TextView) findViewById(R.id.profile_heading);
        TextView textViewDescription = (TextView) findViewById(R.id.profile_description);
        TextView textViewStatus = (TextView) findViewById(R.id.profile_status);
        textViewHeading.setText(mHeading);
        textViewDescription.setText(mDescription);
        textViewStatus.setText(mStatus);
        Log.d(TAG,imageView.toString());
        Glide.with(this)
                .load(mUrl)
                .override(720,720)
                .into(imageView);

    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            HideStatusBars();
        }
    }

    private void HideStatusBars(){
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

    }
}
