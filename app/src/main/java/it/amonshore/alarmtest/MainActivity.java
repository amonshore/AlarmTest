package it.amonshore.alarmtest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NumberPicker numberPicker = (NumberPicker)findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);
        numberPicker.setValue(5);

        final Button btnAdd1 = (Button)findViewById(R.id.btnAdd1);
        final Button btnAdd2 = (Button)findViewById(R.id.btnAdd2);
        final Button btnAdd3 = (Button)findViewById(R.id.btnAdd3);
        final Button btnDel1 = (Button)findViewById(R.id.btnDel1);
        final Button btnDel2 = (Button)findViewById(R.id.btnDel2);
        final Button btnDel3 = (Button)findViewById(R.id.btnDel3);

        btnAdd1.setTag(1);
        btnAdd2.setTag(2);
        btnAdd3.setTag(3);
        btnDel1.setTag(1);
        btnDel2.setTag(2);
        btnDel3.setTag(3);

        View.OnClickListener btnAddOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNewAlarm((int)v.getTag(), numberPicker.getValue(), TimeUnit.SECONDS);
            }
        };

        View.OnClickListener btnDelOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAlarm((int)v.getTag());
            }
        };

        btnAdd1.setOnClickListener(btnAddOnClickListener);
        btnAdd2.setOnClickListener(btnAddOnClickListener);
        btnAdd3.setOnClickListener(btnAddOnClickListener);
        btnDel1.setOnClickListener(btnDelOnClickListener);
        btnDel2.setOnClickListener(btnDelOnClickListener);
        btnDel3.setOnClickListener(btnDelOnClickListener);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setNewAlarm(int tag, long delay, TimeUnit unit) {
        long tm = System.currentTimeMillis() + unit.toMillis(delay);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        intentAlarm.putExtra(AlarmReceiver.EXTRA_TAG, "New alarm #" + tag);
        alarmManager.set(AlarmManager.RTC, tm,
                PendingIntent.getBroadcast(this, tag, intentAlarm, PendingIntent.FLAG_ONE_SHOT));
        Toast.makeText(this, "set alarm: " + tag, Toast.LENGTH_SHORT).show();
    }

    protected void removeAlarm(int tag) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intentAlarm = new Intent(this, AlarmReceiver.class);
        alarmManager.cancel(PendingIntent.getBroadcast(this, tag, intentAlarm, PendingIntent.FLAG_ONE_SHOT));
        Toast.makeText(this, "remove alarm: " + tag, Toast.LENGTH_SHORT).show();
    }
}
