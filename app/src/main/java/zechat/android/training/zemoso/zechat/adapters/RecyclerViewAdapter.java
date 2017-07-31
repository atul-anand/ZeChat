package zechat.android.training.zemoso.zechat.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.List;

import zechat.android.training.zemoso.zechat.R;
import zechat.android.training.zemoso.zechat.activities.ChatsProfile;
import zechat.android.training.zemoso.zechat.java_beans.Startup;

/**
 * Created by zemoso on 25/7/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    //region Variable Declaration

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    private Context mContext;

    //region Database Operations
    private List<Startup> mItems;
    private Startup mStartup;
    private JSONObject mJson;
    //endregion

    //region getRoundedShape Variables
//    private Drawable drawable;
//    private Bitmap bitmap;
//    private Canvas canvas;
//    private Path path;
//    private int targetWidth;
//    private int targetHeight;
//    private Bitmap targetBitmap;
    //endregion

    //endregion

    public RecyclerViewAdapter(Context mContext, List<Startup> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    //Helper Class to get correct view
    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView heading;
        TextView description;
        TextView status;
        CardView cardView;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.card_avatar);
            heading = itemView.findViewById(R.id.card_heading);
            description = itemView.findViewById(R.id.card_description);
            status = itemView.findViewById(R.id.card_status);
            cardView = itemView.findViewById(R.id.card_holder);
        }
    }

    //region Inherited Methods
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.chat_card_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
                try {
            mStartup = mItems.get(position);
            mJson = new JSONObject(mStartup.getJsonObject());
            Log.d(TAG, mJson.toString());


            final String mHeading = mJson.getString("heading");
            final String mDescription = mJson.getString("description");
            final String mStatus = mJson.getString("status");
            final String mImage = mJson.getString("imageUrl");
//            bitmap = getRoundedShape(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.loading));
//            drawable = new BitmapDrawable(mContext.getResources(),bitmap);
            if (!mImage.equals(""))
                Glide.with(mContext)
                        .load(mImage)
                        .into(holder.imageView);
            else
                Glide.with(mContext)
                        .load(Color.BLACK)
                        .into(holder.imageView);
            Log.d(TAG, String.valueOf(mItems.size()));
            holder.heading.setText(mHeading);
            holder.description.setText(mDescription);
            holder.status.setText(mStatus);
            holder.cardView.setBackgroundColor(Color.BLUE);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mIntent = new Intent(mContext, ChatsProfile.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putCharSequence("imageUrl",mImage);
                    mBundle.putCharSequence("heading",mHeading);
                    mBundle.putCharSequence("description", mDescription);
                    mBundle.putCharSequence("status",mStatus);
                    mIntent.putExtras(mBundle);
                    mContext.startActivity(mIntent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    //endregion

    //region Private Methods

//    private Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
//        //setting up height and width of the profile image
//        targetWidth = (int) mContext.getResources().getDimension(R.dimen.profile_pic_size);
//        targetHeight = (int) mContext.getResources().getDimension(R.dimen.profile_pic_size);
//        targetBitmap = Bitmap.createBitmap(targetWidth,
//                targetHeight,Bitmap.Config.ARGB_8888);
//        canvas = new Canvas(targetBitmap);
//        path = new Path();
//        path.addCircle(((float) targetWidth - 1) / 2,
//                ((float) targetHeight - 1) / 2,
//                (Math.min(((float) targetWidth),
//                        ((float) targetHeight)) / 2),
//                Path.Direction.CCW);
//        canvas.clipPath(path);
//        canvas.drawBitmap(scaleBitmapImage,
//                new Rect(0, 0, scaleBitmapImage.getWidth(),
//                        scaleBitmapImage.getHeight()),
//                new Rect(0, 0, targetWidth, targetHeight), null);
//        return targetBitmap;
//    }
    //endregion

}
