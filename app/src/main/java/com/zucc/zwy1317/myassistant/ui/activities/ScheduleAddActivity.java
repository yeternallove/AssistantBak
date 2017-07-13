package com.zucc.zwy1317.myassistant.ui.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.db.AssistantDB;
import com.zucc.zwy1317.myassistant.modle.ScheduleBean;
import com.zucc.zwy1317.myassistant.util.CalendarManager;
import com.zucc.zwy1317.myassistant.util.UserManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleAddActivity extends AppCompatActivity implements View.OnClickListener{

    public static final int SCH_ADD_RESULT_CANCELED= 0;
    public static final int SCH_ADD_SAVE = 1;

    private AssistantDB db;
    private DatePickerDialog mDataPicker;
    private TimePickerDialog mTimePicker;
    private Calendar mCalendarStart,mCalendarEnd;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private ScheduleBean scheduleBean;

    @BindView(R.id.sch_add_toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.sch_add_edt_title)
    EditText edtTitle;
    @BindView(R.id.sch_add_swt_day)
    Switch swtAllDay;
    @BindView(R.id.sch_add_tv_start_day)
    TextView tvStartDay;
    @BindView(R.id.sch_add_tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.sch_add_tv_end_day)
    TextView tvEndDay;
    @BindView(R.id.sch_add_tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.sch_add_tv_repeat)
    TextView tvRepeat;
    @BindView(R.id.sch_add_tv_remind)
    TextView tvRemind;
    @BindView(R.id.sch_add_edt_note)
    TextView edtNote;

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ScheduleAddActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_add);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(SCH_ADD_RESULT_CANCELED);
                finish();
            }
        });

        dateFormat = CalendarManager.getInstance().getGetDateAndWeekFormat();
        timeFormat = CalendarManager.getInstance().getGetTimeFormat();
        mCalendarStart = Calendar.getInstance();
        mCalendarEnd = Calendar.getInstance();

        tvStartDay.setText(dateFormat.format(mCalendarStart.getTime()));
        tvEndDay.setText(dateFormat.format(mCalendarStart.getTime()));

        tvStartTime.setText(timeFormat.format(mCalendarStart.getTime()));
        tvEndTime.setText(timeFormat.format(mCalendarStart.getTime()));

        db = AssistantDB.getInstance(this);
        scheduleBean = new ScheduleBean(UserManager.getInstance(this).getuID());

        btnSave.setOnClickListener(this);
        swtAllDay.setOnClickListener(this);
        tvStartDay.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvEndDay.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        tvRepeat.setOnClickListener(this);
        tvRemind.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                if(mCalendarStart.getTime().after(mCalendarEnd.getTime())){
                    Toast.makeText(ScheduleAddActivity.this,"请选择合适的时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                scheduleBean.setmTitle(edtTitle.getText().toString());
                scheduleBean.setmNote(edtNote.getText().toString());
                scheduleBean.setmStartTime(mCalendarStart);
                scheduleBean.setmEndTime(mCalendarEnd);
                scheduleBean.setmAlarmTime(mCalendarStart);//TODO
//                scheduleBean.getmAlarmColor()
                db.saveSchedule(scheduleBean);
                setResult(SCH_ADD_SAVE);
                finish();
                break;
            case R.id.sch_add_swt_day:
                break;
            case R.id.sch_add_tv_start_day:
                getDatePickerDialog();
                break;
            case R.id.sch_add_tv_start_time:
                getStartTimePickerDialog();
                break;
            case R.id.sch_add_tv_end_day:
                break;
            case R.id.sch_add_tv_end_time:
                getEndTimePickerDialog();
                break;
            case R.id.sch_add_tv_repeat:
                break;
            case R.id.sch_add_tv_remind:
                break;
            default:
                break;
        }
    }

    private void getDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCalendarStart.getTime());
        mDataPicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendarStart.set(year, monthOfYear, dayOfMonth);
                mCalendarEnd.set(year, monthOfYear, dayOfMonth);
                tvStartDay.setText(dateFormat.format(mCalendarStart.getTime()));
                tvEndDay.setText(dateFormat.format(mCalendarStart.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDataPicker.getDatePicker().setMinDate(System.currentTimeMillis());
        mDataPicker.show();
    }

    private void getStartTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCalendarStart.getTime());
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mCalendarStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    mCalendarStart.set(Calendar.MINUTE, minute);
                    tvStartTime.setText(timeFormat.format(mCalendarStart.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        mTimePicker.show();
    }
    private void getEndTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mCalendarEnd.getTime());
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(hourOfDay < mCalendarStart.get(Calendar.HOUR_OF_DAY) || (hourOfDay == mCalendarStart.get(Calendar.HOUR_OF_DAY) && minute < mCalendarStart.get(Calendar.MINUTE))){
                    Toast.makeText(ScheduleAddActivity.this,"请选择合适的时间",Toast.LENGTH_SHORT).show();
                    return;
                }
                mCalendarEnd.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendarEnd.set(Calendar.MINUTE, minute);
                tvEndTime.setText(timeFormat.format(mCalendarEnd.getTime()));
        }
    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        mTimePicker.show();
    }
}
