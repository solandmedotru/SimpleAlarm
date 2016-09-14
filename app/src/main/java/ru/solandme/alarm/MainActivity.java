package ru.solandme.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button alarmOn, alarmOff;
    TextView updateTxtTime;
    TimePicker timePicker;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmOn = (Button) findViewById(R.id.alarm_on);
        alarmOff = (Button) findViewById(R.id.alarm_off);
        updateTxtTime = (TextView) findViewById(R.id.update_txt_time);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        final Calendar calendar = Calendar.getInstance();

        final Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

        alarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();

                String strHour = String.valueOf(hour);
                String strMin = String.valueOf(min);

                if (hour < 10) strHour = "0" + hour;
                if (min < 10) strMin = "0" + min;


                setUpdateTxt(getString(R.string.alarm_set_on) + " " + strHour + ":" + strMin);

                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        alarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpdateTxt(getString(R.string.alarm_set_off));
            }
        });
    }

    private void setUpdateTxt(String message) {
        updateTxtTime.setText(message);
    }
}
