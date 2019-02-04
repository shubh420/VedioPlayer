package in.avilaksh.vedioplayer;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoFolderAdapter extends RecyclerView.Adapter<VideoFolderAdapter.ViewHolder> {
    private List<VideoItem> itemList;

    private Context mContext;
    private ItemClickListener clickListener;
  //  LinearLayout ll_fr_bottom_bar;


    public VideoFolderAdapter(List<VideoItem> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

       // ll_fr_bottom_bar =(LinearLayout) viewGroup.getContext().findViewById(R.id.id_fr_ll_bottm_bar );
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        try {
            VideoItem item = itemList.get(i);
            viewHolder.title.setText(Html.fromHtml(item.getDISPLAY_NAME()));
            viewHolder.videoCount.setText(Html.fromHtml(String.valueOf(item.getVIDEO_COUNT())+" "+"Videos"));
        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView videoCount;
        RelativeLayout rl_long_click_selected_layr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            videoCount=(TextView)itemView.findViewById(R.id.vedioCount);
            rl_long_click_selected_layr = (RelativeLayout) itemView.findViewById(R.id.id_fr_rl_long_press_selest_foldr);



            //itemView.setOnClickListener(this);
            //shubhahsish code for long click------------------------------------------------------
for(int i=0;i<itemList.size();i++){
    Log.w("###############",String.valueOf( itemList.get(i).getDATA()));

}
      //      String[] selectionArgs = new String[]{"%" + itemList.get(0).getDISPLAY_NAME() + "%"};
         //   Log.w("%%%%%%%%%%%%%%%",selectionArgs.toString());

/*            String str = String.valueOf( itemList.get(1).getDATA());
            String findStr = ".mp4";
            int lastIndex = 0;
            int count = 0;

            while(lastIndex != -1){

                lastIndex = str.indexOf(findStr,lastIndex);

                if(lastIndex != -1){
                    count ++;
                    lastIndex += findStr.length();
                }
            }
            Log.w("%%%%%%%%%%%%%%%",String.valueOf( itemList.get(1).getDATA()));
            Log.w("%%%%%%%%%%%%%%%", String.valueOf(count));*/

//-----------------------------------------------------------------------------------
            ArrayList<Boolean> list_of_booleans_of_status_of_folders_whether_LongPrss_slctd_or_not =  new ArrayList<Boolean>();
for(int i =0; i<itemList.size() ;i++){
    list_of_booleans_of_status_of_folders_whether_LongPrss_slctd_or_not.add(false);
}
           // final Boolean[] just_long_clicked = new Boolean[1];

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if( list_of_booleans_of_status_of_folders_whether_LongPrss_slctd_or_not.get(getAdapterPosition()).equals(false) ) {
                        if (clickListener != null)
                            clickListener.onItemClick(view, getAdapterPosition() , false);
                    }else{
                     //   if(  just_long_clicked[0] ==false) {
                            list_of_booleans_of_status_of_folders_whether_LongPrss_slctd_or_not.set(getAdapterPosition(), false);
                            rl_long_click_selected_layr.setVisibility(View.GONE);
                        clickListener.onItemClick(view, getAdapterPosition(),true);
                    //    ll_fr_bottom_bar.setVisibility(View.GONE);
                     //       just_long_clicked[0] =false;
                     //   }
                    }
                }
            });



            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (clickListener != null)
                    {clickListener.onLongItemClick(view, getAdapterPosition());}
rl_long_click_selected_layr.setVisibility(View.VISIBLE);
//ll_fr_bottom_bar.setVisibility(View.VISIBLE);

                    list_of_booleans_of_status_of_folders_whether_LongPrss_slctd_or_not.set(getAdapterPosition() ,true);
                  //  just_long_clicked[0] =true;
                    return true;
                }
            });
        }




       /* @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onItemClick(v, getAdapterPosition());


        }*/
    }


//    private List<MediaFileInfo> itemList;
//
//    private Context mContext;
//
//    public VideoFolderAdapter(Context context, List<MediaFileInfo> itemList) {
//        this.itemList = itemList;
//        this.mContext = context;
//    }
//
//    @Override
//    public MediaListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
//        MediaListRowHolder mh = new MediaListRowHolder(v);
//
//        return mh;
//    }
//
//    @Override
//    public void onBindViewHolder(MediaListRowHolder mediaListRowHolder, int i) {
//        try{
//            MediaFileInfo item = itemList.get(i);
//            mediaListRowHolder.title.setText(Html.fromHtml(item.getFileName()));
//            Uri uri = Uri.fromFile(new File(item.getFilePath()));
//            if(item.getFileType().equalsIgnoreCase("video")) {
//                Bitmap bmThumbnail = ThumbnailUtils.
//                        extractThumbnail(ThumbnailUtils.createVideoThumbnail(item.getFilePath(),
//                                MediaStore.Video.Thumbnails.MINI_KIND), 80, 50);
//                if(bmThumbnail != null) {
//                    mediaListRowHolder.thumbnail.setImageBitmap(bmThumbnail);
//                }
//            }
//            else {
//                Picasso.with(mContext).load(uri)
//                        .centerCrop()
//                        .resize(80, 50)
//                        .into(mediaListRowHolder.thumbnail);
//
//            }
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return (null != itemList ? itemList.size() : 0);
//    }
}
