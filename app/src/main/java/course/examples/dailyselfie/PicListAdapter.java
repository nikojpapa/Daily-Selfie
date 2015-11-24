package course.examples.dailyselfie;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikojpapa on 11/21/15.
 */
public class PicListAdapter extends BaseAdapter {

    private final List<ListItem> mItems = new ArrayList<ListItem>();
    private final Context mContext;

    private static final String TAG = "ds-adapter";

    public PicListAdapter(Context context) {

        mContext = context;

    }

    public void add(ListItem item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear() {

        mItems.clear();
        notifyDataSetChanged();

    }

    public void loadNewImages() {
        File[] images= MainActivity.SELFIE_DIR.listFiles();
        List<File> alreadyHere= new ArrayList<>(mItems.size());

        for (ListItem item : mItems) {
            alreadyHere.add(item.getPic());
        }

        for (File img : images) {
            if (!alreadyHere.contains(img)) {
//                Log.i(TAG, img.getName());
                add(new ListItem(img));
            }
        }
    }

    @Override
    public String toString() {
        String output= "";
        for (ListItem item : mItems) {
            output+= item.getName()+"; ";
        }
        return output;
    }

    @Override
    public int getCount() {

        return mItems.size();

    }

    @Override
    public ListItem getItem(int pos) {

        return mItems.get(pos);

    }

    @Override
    public long getItemId(int pos) {

        return pos;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ListItem listItem= getItem(position);

        RelativeLayout itemLayout;
        if (convertView==null) {
            LayoutInflater inflater= LayoutInflater.from(mContext);
            itemLayout= (RelativeLayout) inflater.inflate(R.layout.list_item, parent, false);
        } else {
            itemLayout= (RelativeLayout) convertView;
        }

        final TextView picName= (TextView) itemLayout.findViewById(R.id.picName);
        picName.setText(listItem.getName());

        final ImageView thumbnail= (ImageView) itemLayout.findViewById(R.id.thumbnail);
        thumbnail.setImageURI(Uri.fromFile(listItem.getPic()));

        return itemLayout;
    }
}
