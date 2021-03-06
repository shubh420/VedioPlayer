package in.avilaksh.vedioplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {
    private Cursor cursor;
    /*
     * Column index for the Thumbnails Image IDs.
     */
    private int columnIndex;
    private static final String TAG = "RecyclerViewExample";


    private RecyclerView mRecyclerView;

    private VideoFolderAdapter adapter;
    String type = "";
    private ArrayList<VideoItem> vedioFolder = new ArrayList<VideoItem>();
    private ArrayList<VedioFileModel> videoItemByAlbum = new ArrayList<VedioFileModel>();
    int count = 0;
    LinearLayout ll_fr_bottom_bar;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll_fr_bottom_bar =(LinearLayout)findViewById(R.id.id_fr_ll_bottm_bar );
        //ListView listView = (ListView) findViewById(R.id.listview);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        if (isReadStoragePermissionGranted()){
//            getVideoAlbum();
//
//
//            type = "video";
//            Log.d(TAG, "onCreate: " + adapter.getItemCount());
//            int totalFoldersHave = vedioFolder.size();
//            Log.d(TAG, "onCreate: " + totalFoldersHave);
//        }
//        else {
//            isReadStoragePermissionGranted();
//        }

        isReadStoragePermissionGranted();


        apply_functioning_of_bottom_bar();

    }

    private void apply_functioning_of_bottom_bar() {
        ImageButton  delete = (ImageButton)findViewById(R.id.id_fr_button_fr_delete_folder);

        //below code is for finding the parent folder name from its sub file name wich is in getdata() of the object
        File file = new File("/storage/emulated/0/Video/Hyderabad.mp4");
        file = new File(file.getAbsolutePath());
        String dir = file.getParent();
Log.e("@@@@@@@@@@@@@@@@@@", dir);


       // File file = new File("/storage/0E49-34BF/n");




     /*   if (file.exists()) {
            String deleteCmd = "rm -r " + "/storage/0E49-34BF/n";
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (IOException e) { }
        }*/

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public int getCount(int position) {
        String path = vedioFolder.get(position).getDISPLAY_NAME();
        int size = getVideoByAlbum(path).size();
        Log.d(TAG, "getCount: videocontains" + size);
        return size;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public List getVideoByAlbum(String bucketName) {
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
//        adapter = new VideoFileAdapter(videoItemByAlbum, VedioListActivity.this);
//        vedioListRecylerViw.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        adapter.setClickListener(this);
        return videoItemByAlbum;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public List getVideoAlbum() {
        String[] PROJECTION_BUCKET_1 = {"DISTINCT " +
                MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Video.VideoColumns.BUCKET_ID,
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Images.ImageColumns.DATE_TAKEN};

        String BUCKET_ORDER_BY = "MAX(datetaken)DESC";
        String BUCKET_GROUP_BY = "1)GROUP BY 1,(2";


        Uri video = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        Cursor cur = this.getContentResolver().query(video, PROJECTION_BUCKET_1,
                BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);

        if (cur != null)
            if (cur.moveToFirst()) {
                String bucket;
                String date;
                String data;
                long bucketId;
                int count;

                int bucketColumn = cur
                        .getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

                int dateColumn = cur
                        .getColumnIndex(MediaStore.Images.Media.DATE_TAKEN);
                int dataColumn = cur.getColumnIndex(MediaStore.Images.Media.DATA);


                int bucketIdColumn = cur
                        .getColumnIndex(MediaStore.Images.Media.BUCKET_ID);
                // int bucketCountcolumn=cur.getColumnIndex(MediaStore.Video.VideoColumns.SIZE);

                do {
// Get the field valuesA

                    bucket = cur.getString(bucketColumn);
                    date = cur.getString(dateColumn);
                    data = cur.getString(dataColumn);
                    bucketId = cur.getInt(bucketIdColumn);

                    if (bucket != null && bucket.length() > 0) {


/* videoItem.set_ID(cursor.getString(0));
videoItem.setARTIST(cursor.getString(1));
videoItem.setTITLE(cursor.getString(2));
videoItem.setDATA(cursor.getString(3));
videoItem.setDISPLAY_NAME(cursor.getString(4));
videoItem.setDURATION(cursor.getString(5));
*/
                        VideoItem videoItem = new VideoItem();
                        videoItem.set_ID(cur.getString(0));
                        videoItem.setDISPLAY_NAME(bucket);
                        videoItem.setDATA(data);
                        videoItem.setFILETYPE("folder");
                        videoItem.setBUCKET_ID(bucketId);
                        videoItem.setDATE_TAKEN(date);

//++++++++++++++++++++below code till the next line  is for finding the number of videos of a folder++++++++++++++++++
                        int sub_vid_count=0;
                        try {
                            String orderBy = MediaStore.Images.Media.DATE_TAKEN;

//            String searchParams = null;
//            String bucket = bucketName;
//            searchParams = bucket;
                            String selection = MediaStore.Video.Media.DATA + " like?";
                            String[] selectionArgs = new String[]{"%" + bucket + "%"};
                            String[] PROJECTION_BUCKET_2 = {MediaStore.Video.VideoColumns._ID,
                                    MediaStore.Video.VideoColumns.DISPLAY_NAME,
                                    MediaStore.Video.VideoColumns.DATE_TAKEN,
                                    MediaStore.Video.VideoColumns.DATA,
                                    MediaStore.Video.VideoColumns.DURATION,
                                    MediaStore.Video.VideoColumns.TITLE};

                            Cursor mVideoCursor;
                            mVideoCursor = this.getContentResolver().query(
                                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, PROJECTION_BUCKET_2,
                                    selection, selectionArgs, orderBy, null);

                            if (mVideoCursor != null) {
                                if (mVideoCursor.moveToFirst()) {
                                    String _id;
                                    String date_2;
                                    String data_2;
                                    String displayName;
                                    int duration;
                                    String tittle;
                                    int idColumn = mVideoCursor
                                            .getColumnIndex(MediaStore.Video.Media._ID);
                                    int bucketColumn_2 = mVideoCursor
                                            .getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);


                                    int dateColumn_2 = mVideoCursor
                                            .getColumnIndex(MediaStore.Video.Media.DATE_TAKEN);
                                    int dataColumn_2 = mVideoCursor.getColumnIndex(MediaStore.Video.Media.DATA);
                                    int durationColumn = mVideoCursor.getColumnIndex(MediaStore.Video.Media.DURATION);
                                    int tittleColumn = mVideoCursor.getColumnIndex(MediaStore.Video.Media.TITLE);
                                    do {
                                        date_2 = mVideoCursor.getString(dateColumn_2);
                                        data_2 = mVideoCursor.getString(dataColumn_2);
                                        displayName = mVideoCursor.getString(bucketColumn_2);
                                        duration = mVideoCursor.getInt(durationColumn);
                                        tittle = mVideoCursor.getString(tittleColumn);


                                        VedioFileModel albumVideo = new VedioFileModel();
                                        albumVideo.setmDisplayName(displayName);
                                        albumVideo.setmUrl_FilePath(data_2);
                                        albumVideo.setmContentType("video");
                                        albumVideo.setmTitle(tittle);
                                        albumVideo.setmDuration(duration);
                                        videoItemByAlbum.add(albumVideo);

                                        sub_vid_count++;
                                    } while (mVideoCursor.moveToNext());
// cursorData.addAll(MediaUtils.extractMediaList(mVideoCursor,
// MediaType.PHOTO));
// mediaAdapter.notifyDataSetChanged();

                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

 //-----------------------------------------------------------------------
                        videoItem.setVIDEO_COUNT(sub_vid_count);

                        vedioFolder.add(videoItem);

                    }


                } while (cur.moveToNext());
            }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }
        adapter = new VideoFolderAdapter(vedioFolder, MainActivity.this);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setClickListener(this);

        return vedioFolder;
    }

    boolean is_any_item_just_long_clicked =false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(View view, int position , boolean is_bottom_bar_active) {

        if(is_bottom_bar_active == true) {
            ll_fr_bottom_bar.setVisibility(View.GONE);

        }else {
            String path = vedioFolder.get(position).getDISPLAY_NAME();
            Intent intent = new Intent(MainActivity.this, VedioListActivity.class);
            intent.putExtra("FolderPath", path);
            startActivity(intent);
        }

    }

    @Override
    public void onLongItemClick(View view, int position) {
ll_fr_bottom_bar.setVisibility(View.VISIBLE);

is_any_item_just_long_clicked = true;
    }

    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted1");
                getVideoAlbum();


                type = "video";
                Log.d(TAG, "onCreate: " + adapter.getItemCount());
                int totalFoldersHave = vedioFolder.size();
                Log.d(TAG, "onCreate: " + totalFoldersHave);

                return true;
            } else {

                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1");
            return true;
        }
    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2");
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                Log.d(TAG, "External storage2");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    //resume tasks needing this permission
                    //downloadPdfFile();

                } else {
                    //progress.dismiss();
                }
                break;

            case 3:
                Log.d(TAG, "External storage1");
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    //resume tasks needing this permission
                    //SharePdfFile();
                    getVideoAlbum();


                    type = "video";
                    Log.d(TAG, "onCreate: " + adapter.getItemCount());
                    int totalFoldersHave = vedioFolder.size();
                    Log.d(TAG, "onCreate: " + totalFoldersHave);
                } else {
                    //progress.dismiss();
                }
                break;
        }
    }


}
