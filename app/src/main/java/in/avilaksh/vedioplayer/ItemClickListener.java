package in.avilaksh.vedioplayer;

import android.view.View;

public interface ItemClickListener {
    void onItemClick(View view, int position , boolean is_bottom_bar_active);
    void onLongItemClick(View view, int position );
}
