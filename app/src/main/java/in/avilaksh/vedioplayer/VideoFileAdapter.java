package in.avilaksh.vedioplayer;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.List;

public class VideoFileAdapter extends RecyclerView.Adapter<VideoFileAdapter.ViewHolder> {
    private List<VedioFileModel> itemList;

    private Context mContext;
    private ItemClickListener clickListener;


    public VideoFileAdapter(List<VedioFileModel> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videofilelistitem, null);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        try {
            VedioFileModel item = itemList.get(i);
            viewHolder.title.setText(Html.fromHtml(item.getmDisplayName()));
            Uri uri = Uri.fromFile(new File(item.getmUrl_FilePath()));
            if (item.getmContentType().equalsIgnoreCase("video")) {
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(android.R.drawable.stat_notify_error)
                        .error(android.R.drawable.stat_notify_error);

                Glide.with(mContext)
                        .load(item.getmUrl_FilePath())
                        .apply(options)
                        .into(viewHolder.thumbnail);
            } else {
                itemList.remove(i);


            }


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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumbnail;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
            //the original code of intent for vediolistactivity is in mainacticvity of this click segment
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onItemClick(v, getAdapterPosition() ,false);

        }
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
