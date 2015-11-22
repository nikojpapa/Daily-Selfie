package course.examples.dailyselfie;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;

/**
 * Created by nikojpapa on 11/21/15.
 */
public class ListItem {
    private static final String TAG= "ds-listitem";
    private String mName;
    private File mPicture;

    ListItem(File img) {
        this.mName= img.getName();
        this.mPicture= img;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName= name;
    }

    public File getPic() {
        return mPicture;
    }

    public void setPic(File picture) {
        mPicture= picture;
    }
}
