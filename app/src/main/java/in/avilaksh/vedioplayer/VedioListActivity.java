package in.avilaksh.vedioplayer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class VedioListActivity extends AppCompatActivity implements ItemClickListener {
    RecyclerView vedioListRecylerViw;
    private ArrayList<VedioFileModel> videoItemByAlbum = new ArrayList<VedioFileModel>();
    private VideoFileAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_list);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String vedio_path = bundle.getString("FolderPath");
        vedioListRecylerViw = (RecyclerView) findViewById(R.id.recylerviewvediolist);
        vedioListRecylerViw.setHasFixedSize(true);
        vedioListRecylerViw.setLayoutManager(new LinearLayoutManager(this));
        getVideoByAlbum(vedio_path);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void getVideoByAlbum(String bucketName) {
        try {
            String orderBy = MediaStore.Images.Media.DATE_TAKEN;

//            String searchParams = null;
//            String bucket = bucketName;
//            searchParams = bucket;
            String selection = MediaStore.Video.Media.DATA + " like?";
            String[] selectionArgs = new String[]{"%" + bucketName + "%"};
            String[] PROJECTION_BUCKET = {MediaStore.Video.VideoColumns._ID,
                    MediaStore.Video.VideoColumns.DISPLAY_NAME,
                    MediaStore.Video.VideoColumns.DATE_TAKEN,
                    MediaStore.Video.VideoColumns.DATA,
                    MediaStore.Video.VideoColumns.DURATION,
                    MediaStore.Video.VideoColumns.TITLE};

            Cursor mVideoCursor;
            mVideoCursor = this.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, PROJECTION_BUCKET,
                    selection, selectionArgs, orderBy, null);

            if (mVideoCursor != null) {
                if (mVideoCursor.moveToFirst()) {
                    String _id;
                    String date;
                    String data;
                    String displayName;
                    int duration;
                    String tittle;
                    int idColumn = mVideoCursor
                            .getColumnIndex(MediaStore.Video.Media._ID);
                    int bucketColumn = mVideoCursor
                            .getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);


                    int dateColumn = mVideoCursor
                            .getColumnIndex(MediaStore.Video.Media.DATE_TAKEN);
                    int dataColumn = mVideoCursor.getColumnIndex(MediaStore.Video.Media.DATA);
                    int durationColumn = mVideoCursor.getColumnIndex(MediaStore.Video.Media.DURATION);
                    int tittleColumn = mVideoCursor.getColumnIndex(MediaStore.Video.Media.TITLE);
                    do {
                        date = mVideoCursor.getString(dateColumn);
                        data = mVideoCursor.getString(dataColumn);
                        displayName = mVideoCursor.getString(bucketColumn);
                        duration = mVideoCursor.getInt(durationColumn);
                        tittle = mVideoCursor.getString(tittleColumn);


                        VedioFileModel albumVideo = new VedioFileModel();
                        albumVideo.setmDisplayName(displayName);
                        albumVideo.setmUrl_FilePath(data);
                        albumVideo.setmContentType("video");
                        albumVideo.setmTitle(tittle);
                        albumVideo.setmDuration(duration);
                        videoItemByAlbum.add(albumVideo);

                    } while (mVideoCursor.moveToNext());
// cursorData.addAll(MediaUtils.extractMediaList(mVideoCursor,
// MediaType.PHOTO));
// mediaAdapter.notifyDataSetChanged();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new VideoFileAdapter(videoItemByAlbum, VedioListActivity.this);
        vedioListRecylerViw.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setClickListener(this);

    }


    @Override
    public void onItemClick(View view, int position,  boolean is_bottom_bar_active) {
        Intent intent = new Intent(VedioListActivity.this, VideoPlayerActivity.class);
        intent.putExtra("filepath", videoItemByAlbum.get(position).getmUrl_FilePath());
        startActivity(intent);

    }

    public void onLongItemClick(View view, int position) {
      /*  Intent intent = new Intent(VedioListActivity.this, VideoPlayerActivity.class);
        intent.putExtra("filepath", videoItemByAlbum.get(position).getmUrl_FilePath());
        startActivity(intent);*/

    }

}
