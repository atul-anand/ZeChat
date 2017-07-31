package zechat.android.training.zemoso.zechat.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import zechat.android.training.zemoso.zechat.R;

public class ChatsProfile extends AppCompatActivity {

    private static final String TAG = ChatsProfile.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chats_profile);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        String mUrl = (String) getIntent().getExtras().getCharSequence("imageUrl");
        String mHeading = (String) getIntent().getExtras().getCharSequence("heading");
        String mDescription = (String) getIntent().getExtras().getCharSequence("description");
        String mStatus = (String) getIntent().getExtras().getCharSequence("status");
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

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();

    }
}
