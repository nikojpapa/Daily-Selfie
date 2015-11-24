package course.examples.dailyselfie;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PicItemFragment
        .OnPicSelectListener, ViewPicFragment.OnLargePicListener {
    private static final String TAG = "ds-main";
    private static final int START_CAMERA = 1;
    private static final int NOTIFICATION_REQUEST= 2;
    protected static final File SELFIE_DIR= new File(Environment.getExternalStorageDirectory()
            +"/daily_selfie");
    private static final long INITIAL_ALARM_DELAY = 1 * 30 * 1000L; //30 secs
    private static final long ALARM_INTERVAL= 1 * 30 * 1000L; //30 secs

    private final PicItemFragment mPicItemFragment = new PicItemFragment();
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.i(TAG, "" + SELFIE_DIR.mkdirs() + ", " + SELFIE_DIR.getAbsolutePath());

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.enableDebugLogging(true);
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.picList_fragment_container, mPicItemFragment);
        fragmentTransaction.commit();

        setRepeatingAlarm();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.camera_button) {

            String name = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss", Locale.US).format(new Date
                    ()).toString();
            File destination = new File(SELFIE_DIR, name + ".jpg");

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(destination));
            startActivityForResult(intent, START_CAMERA);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPicSelect(ListItem pic) {
        ViewPicFragment viewPicFragment= ViewPicFragment.newInstance(pic.getPath());

        FragmentTransaction fragmentTransaction= mFragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.picList_fragment_container, viewPicFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        mFragmentManager.executePendingTransactions();
//        Toast.makeText(this, pic.getName(), Toast.LENGTH_SHORT).show();
    }

    public void onLargePicClick() {
        onBackPressed();
    }

    private void setRepeatingAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent notificationReceiverIntent = new Intent(this,
                AlarmNotificationReceiver.class);

        PendingIntent notificationReceiverPendingIntent= PendingIntent.getBroadcast(this,
                NOTIFICATION_REQUEST,
                notificationReceiverIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis()
                        +INITIAL_ALARM_DELAY,
                ALARM_INTERVAL,
                notificationReceiverPendingIntent);

        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();
    }
}
