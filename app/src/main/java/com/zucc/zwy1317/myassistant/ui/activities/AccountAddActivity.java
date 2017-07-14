package com.zucc.zwy1317.myassistant.ui.activities;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zucc.zwy1317.myassistant.R;
import com.zucc.zwy1317.myassistant.db.AssistantDB;
import com.zucc.zwy1317.myassistant.modle.RecordBean;
import com.zucc.zwy1317.myassistant.modle.TypeIconBean;
import com.zucc.zwy1317.myassistant.ui.adapters.TypeIconAdapter;
import com.zucc.zwy1317.myassistant.ui.base.BaseActivity;
import com.zucc.zwy1317.myassistant.ui.base.BaseFragment;
import com.zucc.zwy1317.myassistant.ui.fragments.TypeIconFragment;
import com.zucc.zwy1317.myassistant.util.CalendarManager;
import com.zucc.zwy1317.myassistant.util.LocationHelper;
import com.zucc.zwy1317.myassistant.util.UserManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @description:
 * @author: eternallove
 * @date: 2017/7/11 10:37
 */

public class AccountAddActivity extends BaseActivity implements View.OnClickListener{

    public static final int ACC_ADD_RESULT_CANCELED= 0;
    public static final int ACC_ADD_SAVE = 1;
    public static final int TAKE_PHOTO = 2;
    public static final int CROP_PHOTO = 3;
    public static final int GET_PHOTO = 4;
    private static final String TEST_STR = "拱墅区";

    private AssistantDB db;
    private RecordBean recordBean;

    private DatePickerDialog mDataPicker;
    private TimePickerDialog mTimePicker;
    private PopupWindow mPopupWindow;
    private View mPopupView;
    private TextView tvPopup1;
    private TextView tvPopup2;
    private TextView tvPopup3;

    private Calendar mCalendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    private TypeIconFragment fragment;
    private List<BaseFragment> fragments = new ArrayList<>();
    private String[] tabTiltle;
    private List<TypeIconBean> incomeList;
    private List<TypeIconBean> spendingList;
    private Uri headImgUri;

    private Location location;

    @BindView(R.id.acc_add_vp)
    ViewPager viewPaper;
    @BindView(R.id.acc_add_tabs)
    TabLayout tabLayout;
    @BindView(R.id.acc_add_toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.acc_add_edt_money)
    EditText edtMoney;
    @BindView(R.id.acc_add_img_typeicon)
    ImageView imgTypeIcon;
    @BindView(R.id.acc_add_tv_title)
    TextView tvTitle;
    @BindView(R.id.acc_add_edt_note)
    TextView edtNote;
    @BindView(R.id.sch_add_tv_start_time)
    TextView tvDay;
    @BindView(R.id.acc_add_tv_time)
    TextView tvTime;
    @BindView(R.id.acc_add_tv_location)
    TextView tvLocation;
    @BindView(R.id.acc_add_img_camera)
    ImageView imgCamera;
    @BindView(R.id.acc_add_img_camera_img)
    ImageView imgCAmeraImg;

    public static void actionStart(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AccountAddActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_add);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ACC_ADD_RESULT_CANCELED);
                finish();
            }
        });

        db = AssistantDB.getInstance(this);
        recordBean = new RecordBean(UserManager.getInstance(this).getuID());

        incomeList = db.loadTypeIconAll(TypeIconBean.TYPE_INCOME);
        spendingList = db.loadTypeIconAll(TypeIconBean.TYPE_SPENDING);
        fragment = new TypeIconFragment(spendingList,new TypeIconAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                imgTypeIcon.setImageResource(spendingList.get(position).getIcon());
                tvTitle.setText(spendingList.get(position).getTitle());
                recordBean.setType(TypeIconBean.TYPE_SPENDING);
            }
        });
        fragments.add(fragment);
        fragment = new TypeIconFragment(incomeList,new TypeIconAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                imgTypeIcon.setImageResource(incomeList.get(position).getIcon());
                tvTitle.setText(incomeList.get(position).getTitle());
                recordBean.setType(TypeIconBean.TYPE_INCOME);
            }
        });
        fragments.add(fragment);
        tabTiltle = new String[]{this.getResources().getString(R.string.tv_account_spending),this.getResources().getString(R.string.tv_account_income)};

        tabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        tabLayout.addTab(tabLayout.newTab().setText(tabTiltle[0]));//添加tab选项卡
        tabLayout.addTab(tabLayout.newTab().setText(tabTiltle[1]));

        MyPagerAdapter myAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPaper.setAdapter(myAdapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPaper);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(myAdapter);//给Tabs设置适配器

        dateFormat = CalendarManager.getInstance().getGetDateAndWeekFormat();
        timeFormat = CalendarManager.getInstance().getGetTimeFormat();
        mCalendar = Calendar.getInstance();
        tvDay.setText(dateFormat.format(mCalendar.getTime()));
        tvTime.setText(timeFormat.format(mCalendar.getTime()));
        imgTypeIcon.setImageResource(new TypeIconBean().getIcon());
        tvTitle.setText(new TypeIconBean().getTitle());
        Location location = LocationHelper.GetLocation(this, new LocationHelper.MyLocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if(location != null) {
                    tvLocation.setText(String.format("经度 :%.4f 纬度 :%.4f %s", location.getLongitude(), location.getLatitude(),TEST_STR));
                }
            }
        });
        if(location != null) {
            tvLocation.setText(String.format("经度 :%.4f 纬度 :%.4f %s", location.getLongitude(), location.getLatitude(),TEST_STR));
        }

        //         android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        mPopupView = getLayoutInflater().inflate(R.layout.view_popupwindow, null);
        tvPopup1 = (TextView) mPopupView.findViewById(R.id.popup_1);
        tvPopup2 = (TextView) mPopupView.findViewById(R.id.popup_2);
        tvPopup3 = (TextView) mPopupView.findViewById(R.id.popup_3);
        mPopupWindow = new PopupWindow(mPopupView, MATCH_PARENT, WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        mPopupWindow.getContentView().setFocusableInTouchMode(true);
        mPopupWindow.getContentView().setFocusable(true);
        mPopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (mPopupWindow != null && mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        tvPopup1.setOnClickListener(this);
        tvPopup2.setOnClickListener(this);
        tvPopup3.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        tvDay.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        imgCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                String strMoney = edtMoney.getText().toString();
                double money;
                if(strMoney.equals("")||( money = Double.parseDouble(strMoney)) == 0){
                    Toast.makeText(this,this.getResources().getString(R.string.acc_add_save_failed),Toast.LENGTH_SHORT).show();
                    return;
                }
                recordBean.setmAmount(money);
                recordBean.setTitle(tvTitle.getText().toString());
                recordBean.setmNote(edtNote.getText().toString());
                recordBean.setmTime(mCalendar);
                db.saveRecord(recordBean);
                setResult(ACC_ADD_SAVE);
                finish();
                break;
            case R.id.sch_add_tv_start_time:
                getDatePickerDialog();
                break;
            case R.id.acc_add_tv_time:
                getTimePickerDialog();
                break;
            case R.id.acc_add_tv_location:
                if(location != null) {
                    Toast.makeText(this, "已更新位置", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "位置获取失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.acc_add_img_camera:
                mPopupWindow.showAsDropDown(imgCamera);
                break;
            case R.id.popup_1:
                takePhoto();
                mPopupWindow.dismiss();
                break;
            case R.id.popup_2:
                getPhoto();
                mPopupWindow.dismiss();
                break;
            case R.id.popup_3:
                mPopupWindow.dismiss();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_PHOTO:
                if (resultCode == RESULT_OK) {
                    crop(data.getData());
                }
                break;
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    crop(headImgUri);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap cropbitmap = data.getParcelableExtra("data");
                    imgCAmeraImg.setImageBitmap(cropbitmap);
                }
                break;
            default:
                break;
        }
    }
    private void getDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mDataPicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                mCalendar.set(year, monthOfYear, dayOfMonth);
                tvDay.setText(dateFormat.format(mCalendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDataPicker.getDatePicker().setMaxDate(System.currentTimeMillis());
        mDataPicker.show();
    }

    private void getTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                tvTime.setText(timeFormat.format(mCalendar.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        mTimePicker.show();
    }
    private void takePhoto() {
        //创建File对象，用于存储拍照后的图片
        //将此图片存储于SD卡的根目录下
        File outputImage = new File(Environment.getExternalStorageDirectory(),
                "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将File对象转换成Uri对象
        //Uri表标识着图片的地址
        headImgUri = Uri.fromFile(outputImage);
        //隐式调用照相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        //拍下的照片会被输出到output_image.jpg中去
        intent.putExtra(MediaStore.EXTRA_OUTPUT, headImgUri);
        //此处是使用的startActivityForResult（）
        //因此在拍照完后悔有结果返回到onActivityResult（）中去，返回值即为<span style="font-size: 13.3333px; font-family: Arial, Helvetica, sans-serif;">CUT_PICTURE</span>
        //onActivityResult（）中主要是实现图片裁剪

        startActivityForResult(intent, TAKE_PHOTO);
    }

    private void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GET_PHOTO);
    }

    private void crop(Uri uri) {

        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);// 输出是X方向的比例
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小,不能太大500程序崩溃
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);

        // 图片格式
        /* intent.putExtra("outputFormat", "JPEG"); */
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:返回uri，false：不返回uri

        // 同一个地址下 裁剪的图片覆盖拍照的图片
        intent.putExtra(MediaStore.EXTRA_OUTPUT, headImgUri);

        startActivityForResult(intent, CROP_PHOTO);
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTiltle[position];
        }
    }


}
