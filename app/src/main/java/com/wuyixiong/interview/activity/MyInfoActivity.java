package com.wuyixiong.interview.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.db.DBManager;
import com.wuyixiong.interview.entity.News;
import com.wuyixiong.interview.entity.User;
import com.wuyixiong.interview.event.InfoEvent;
import com.wuyixiong.interview.event.LoginedEvent;
import com.wuyixiong.interview.utils.UpdateUser;
import com.wuyixiong.interview.view.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class MyInfoActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.cv_icon)
    CircleImageView cvIcon;
    @Bind(R.id.ll_icon)
    LinearLayout llIcon;
    @Bind(R.id.tv_nick_myinfo)
    TextView tvNickMyinfo;
    @Bind(R.id.ll_nick)
    LinearLayout llNick;
    @Bind(R.id.tv_account_myinfo)
    TextView tvAccountMyinfo;
    @Bind(R.id.iv_account_myinfo)
    ImageView ivAccountMyinfo;
    @Bind(R.id.ll_account)
    LinearLayout llAccount;
    @Bind(R.id.tv_phone_myinfo)
    TextView tvPhoneMyinfo;
    @Bind(R.id.ll_phone)
    LinearLayout llPhone;
    @Bind(R.id.tv_sex_myinfo)
    TextView tvSexMyinfo;
    @Bind(R.id.ll_sex)
    LinearLayout llSex;
    @Bind(R.id.tv_job_myinfo)
    TextView tvJobMyinfo;
    @Bind(R.id.ll_job)
    LinearLayout llJob;
    @Bind(R.id.tv_quit)
    TextView tvQuit;
    @Bind(R.id.ll_titlebar)
    LinearLayout llTitlebar;

    private Button btnTake;
    private Button btnPhoto;
    private Button btnCancel;

    private User myUser;
    private Context mContext;
    private String sex;
    private PopupWindow pp;
    private View v;
    private String filePath;
    private UpdateUser updateUser;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        mContext = this;
        updateUser = new UpdateUser();
        //获取用户的信息
        myUser = BmobUser.getCurrentUser(User.class);
        initView();
        filePath = getCacheDir() + File.separator + "headPortrait";
        setListener();
    }

    @Override
    protected void initView() {
        if (myUser.getHeadUrl() != null) {
            ImageLoader.getInstance().displayImage(myUser.getHeadUrl(), cvIcon);
        }
        tvNickMyinfo.setText(myUser.getNickName());

        tvAccountMyinfo.setText(myUser.getUsername());
        if (myUser.isChanged()) {
            ivAccountMyinfo.setVisibility(View.GONE);
        }
        tvPhoneMyinfo.setText(myUser.getMobilePhoneNumber());
        tvSexMyinfo.setText(myUser.getSex());
        if (myUser.getJob() != null) {
            tvJobMyinfo.setText(myUser.getJob());
        }

        //初始化popupWindow
        pp = new PopupWindow(this);
        initPopupWindow();
        btnTake = (Button) v.findViewById(R.id.btn_personalinfo_popup_take);
        btnPhoto = (Button) v.findViewById(R.id.btn_personalinfo_popup_photo);
        btnCancel = (Button) v.findViewById(R.id.btn_personalinfo_popup_cancel);

    }

    @Override
    protected void setListener() {
        btnCancel.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        btnTake.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.ll_icon, R.id.ll_nick, R.id.ll_account, R.id.ll_phone,
            R.id.ll_sex, R.id.ll_job, R.id.tv_quit})
    public void onViewClicked(View view) {
        final View layout = LayoutInflater.from(mContext).inflate(R.layout.dialog_custom, null);
        switch (view.getId()) {
            case R.id.ll_icon:
                if (pp != null && pp.isShowing()) {
                    pp.dismiss();
                } else {
                    pp.showAsDropDown(llTitlebar);
                }
                break;
            case R.id.ll_nick:
                final EditText edit = (EditText) layout.findViewById(R.id.edit_dialog);
                edit.setHint("请输入新昵称");
                new AlertDialog.Builder(mContext).setTitle("修改昵称")
                        .setView(layout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateUser.updateNick(edit.getText().toString().trim());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.ll_account:
                if (!myUser.isChanged()) {
                    final EditText edit1 = (EditText) layout.findViewById(R.id.edit_dialog);
                    new AlertDialog.Builder(mContext).setTitle("提示")
                            .setMessage("账号修改一次后将不能再进行修改，是否确定修改")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    edit1.setHint("请输入新账号");
                                    new AlertDialog.Builder(mContext).setTitle("账号更改一次后将不能再更改")
                                            .setView(layout)
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    updateUser.updateAccount(edit1.getText().toString().trim());
                                                }
                                            })
                                            .setNegativeButton("取消", null)
                                            .show();
                                }
                            })
                            .setNegativeButton("取消", null)
                            .show();
                }
                break;
            case R.id.ll_phone:
                break;
            case R.id.ll_sex:
                sex = "男";
                new AlertDialog.Builder(mContext).setTitle("选择性别")
                        .setSingleChoiceItems(new String[]{"男", "女"}, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0) {
                                    sex = "男";
                                } else {
                                    sex = "女";
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateUser.updateSex(sex);
                            }
                        }).show();
                break;
            case R.id.ll_job:
                final EditText edit2 = (EditText) layout.findViewById(R.id.edit_dialog);
                edit2.setHint("请输入职位");
                new AlertDialog.Builder(mContext).setTitle("修改职位")
                        .setView(layout)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateUser.updateJob(edit2.getText().toString().trim());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.tv_quit:
                BmobUser.logOut();
                EventBus.getDefault().post(new LoginedEvent(false));
                //清除数据库数据
                DBManager.getInstance(this).removeAllQuestion();
                //清除SharedPreference中的数据
                SharedPreferences shared = getSharedPreferences("collectionId", MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.clear().commit();
                SharedPreferences shared1 = getSharedPreferences("interest", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = shared1.edit();
                editor1.clear().commit();

                finish();
                break;
        }
    }

    /**
     * popuoWindow 中按钮的监听
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_personalinfo_popup_take:              //popupwindow中照相的监听
                Intent intent_take = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent_take, 400);
                break;
            case R.id.btn_personalinfo_popup_photo:             //popupwindow中相册的监听
                Intent intent_photo = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent_photo, 500);
                break;
            case R.id.btn_personalinfo_popup_cancel:               //popupwindow中取消的监听
                pp.dismiss();
                break;
        }
    }

    /**
     * 初始化popupWindow
     */
    private void initPopupWindow() {
        v = getLayoutInflater().inflate(R.layout.popup_personalinfo, null);
        pp.setContentView(v);
        pp.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pp.setFocusable(true);
        pp.setTouchable(true);
        pp.setOutsideTouchable(true);
        pp.setBackgroundDrawable(new BitmapDrawable());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 400) {//相机的返回码
                Bitmap b = null;
                Object ob = data.getExtras().get("data");
                if (ob != null && ob instanceof Bitmap) {
                    b = (Bitmap) ob;
                }
                if (data.getData() != null) {//得到uri 如果手机自动存储则为true
                    cutPhoto(data.getData());
                } else {//如果手机不自动存储则为false
                    File f = writeFile(b);
                    Uri uri = Uri.fromFile(f);
                    cutPhoto(uri);
                }

            }
            if (requestCode == 500) {//调用相册的返回码
                cutPhoto(data.getData());
            }
            if (requestCode == 200) {//裁剪的返回码
                if (data != null) {
                    Bitmap bit = data.getParcelableExtra("data");
                    if (bit == null) {
                        Toast.makeText(this, "图片为空", Toast.LENGTH_SHORT).show();
                    } else {
                        File f = writeFile(bit);//将头像写入文件
                        bitmap = bit;
                        showLoadingDialog("上传中", true);
                        updateUser.updateIcon(f);
                    }
                    pp.dismiss();
                }
            }
        }

    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void cutPhoto(Uri uri) {
        //调用系统裁剪
        Intent intent = new Intent("com.android.camera.action.CROP");
        // 设置类型
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪命令
        intent.putExtra("crop", true);
        // X方向上的比例
        intent.putExtra("aspectX", 1);
        // Y方向上的比例
        intent.putExtra("aspectY", 1);
        // 裁剪区的宽
        intent.putExtra("outputX", 500);
        // 裁剪区的高
        intent.putExtra("outputY", 500);
        // 返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 200);
    }

    /**
     * 将头像写入文件
     *
     * @param bitmap 头像图片
     */
    private File writeFile(Bitmap bitmap) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File head = new File(file, "head.jpg");
        try {
            OutputStream output = new FileOutputStream(head);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return head;
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void upLoad(InfoEvent event) {
        //上传结果
        cancelDialog();
        switch (event.getCode()){
            case 0:
                cvIcon.setImageBitmap(bitmap);
                toast("上传成功");
                break;
            case 1:
                if ("nickName".equals(event.getType())){
                    tvNickMyinfo.setText(event.getMessage());
                    EventBus.getDefault().post(new LoginedEvent(true));
                }else if ("account".equals(event.getType())){
                    tvAccountMyinfo.setText(event.getMessage());
                    ivAccountMyinfo.setVisibility(View.GONE);
                    EventBus.getDefault().post(new LoginedEvent(true));
                }else if ("sex".equals(event.getType())){
                    tvSexMyinfo.setText(event.getMessage());
                }else if ("job".equals(event.getType())){
                    tvJobMyinfo.setText(event.getMessage());
                }
                toast("修改成功");
                break;
            case -1:
                toast("修改失败");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            EventBus.getDefault().post(new LoginedEvent(true));
        }
        return super.onKeyDown(keyCode, event);
    }
}
