package zechat.android.training.zemoso.zechat.adapters;

import android.content.Context;
import android.graphics.Color;
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
import zechat.android.training.zemoso.zechat.java_beans.Startup;

/**
 * Created by zemoso on 25/7/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>  {

    private Context mContext;
    private List<Startup> mItems;
//    private Drawable drawable;
//    private Bitmap bitmap;
//    private Canvas canvas;
//    private Path path;
//    private int targetWidth;
//    private int targetHeight;
//    private Bitmap targetBitmap;

    public RecyclerViewAdapter(Context mContext, List<Startup> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.chat_card_view,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        try {
            Startup startup = mItems.get(position);
            JSONObject json = new JSONObject(startup.getJsonObject());
            Log.d("Object",json.toString());
            String image = json.getString("imageUrl");
//            bitmap = getRoundedShape(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.loading));
//            drawable = new BitmapDrawable(mContext.getResources(),bitmap);
            if(!image.equals(""))
                Glide.with(mContext)
                    .load(image)
                    .into(holder.imageView);
            else
                Glide.with(mContext)
                    .load(Color.BLACK)
                    .into(holder.imageView);
            Log.d("mItems", String.valueOf(mItems.size()));
//            holder.imageView.setImageDrawable(drawable);
            holder.heading.setText(json.getString("heading"));
            holder.description.setText(json.getString("description"));
            holder.status.setText(json.getString("status"));
            holder.cardView.setBackgroundColor(Color.BLUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

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
}
