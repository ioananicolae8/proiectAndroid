package com.example.licenta.smartdoctor.fragments;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.AlarmClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.licenta.R;

import java.util.Calendar;


public class SeteazaReminderFragment extends Fragment {

    private Button time_btn;
    private Button alarm_btn;
    private TimePickerDialog timePicker;
    private TextView time_view;
    private EditText et_notite;

    private static final int NOTIFICATION_ID = 0;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seteaza_reminder, container, false);

        time_btn = view.findViewById(R.id.btn_time);
        alarm_btn = view.findViewById(R.id.btn_alarm);
        time_view = view.findViewById(R.id.txtv_time);
        et_notite = view.findViewById(R.id.et_notite);
        fragmentManager = getFragmentManager();

        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int sHour, int sMinute) {
                                String selectedHour;
                                if (sHour < 10)
                                    selectedHour = "0" + sHour;
                                else
                                    selectedHour = "" + sHour;
                                String selectedMinute;
                                if (sMinute < 10)
                                    selectedMinute = "0" + sMinute;
                                else
                                    selectedMinute = "" + sMinute;
                                time_view.setText("Selected time: "+ selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });

        alarm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();

                calendar.setTimeInMillis(System.currentTimeMillis());

                int hour = 0, minute = 0;

                try {
                    hour = Integer.parseInt(time_view.getText().toString().substring(15, 17));
                    minute = Integer.parseInt(time_view.getText().toString().substring(18));
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Nu uita să alegi ora și data", Toast.LENGTH_SHORT).show();
                    return;
                }

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
                intent.putExtra(AlarmClock.EXTRA_MINUTES, minute);
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, et_notite.getText().toString());
                intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                startActivity(intent);

                Toast.makeText(getContext(), "Alarmă creată!", Toast.LENGTH_LONG).show();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }
}