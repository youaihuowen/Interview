package com.wuyixiong.interview.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.GridAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.event.PublishEvent;
import com.wuyixiong.interview.event.ShareEvent;
import com.wuyixiong.interview.utils.Publication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity extends BaseActivity {


    @Bind(R.id.edit_share)
    EditText editShare;
    @Bind(R.id.btnMuilt)
    Button btnMuilt;
    @Bind(R.id.ll_type)
    LinearLayout llType;
    @Bind(R.id.edit_company)
    EditText editCompany;
    @Bind(R.id.tv_stype)
    TextView tvStype;
    private Context mContext;
    private ArrayList<String> imagePaths = null;

    private ImageCaptureManager captureManager; // 相机拍照处理类
    private static final int REQUEST_CAMERA_CODE = 11;
    private static final int REQUEST_PREVIEW_CODE = 22;

    private GridAdapter gridAdapter;
    private GridView gridView;
    private int selected = 0;
    private String[] types = new String[]{"Android", "ios", "Html", "C/C++", "PHP", "SQL", "Linux", "Python"};
    private String company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        mContext = this;
        initView();
        gridAdapter = new GridAdapter(this);
        gridView.setAdapter(gridAdapter);


    }

    @Override
    protected void initView() {
        gridView = (GridView) findViewById(R.id.gridView);
        tvStype.setText(types[selected]);

    }

    @Override
    protected void setListener() {


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    toast("选择照片");
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    loadAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    toast("预览");

                    break;
                // 调用相机拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if (captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        ArrayList<String> paths = new ArrayList<>();
                        paths.add(captureManager.getCurrentPhotoPath());
                        loadAdpater(paths);
                    }
                    break;

            }
        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        imagePaths.clear();
        imagePaths.addAll(paths);
        gridAdapter.setListUrls(imagePaths);
        gridAdapter.notifyDataSetChanged();

    }


    @OnClick({R.id.btnMuilt, R.id.ll_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMuilt:
                showLoadingDialog("上传中",true);
                company = editCompany.getText().toString().trim();
                if (company.length()<2){
                    company = "未知";
                }
                Publication.getInstance().publishpic(imagePaths);
                break;
            case R.id.ll_type:
                selectDialog();
                break;
        }
    }

    /**
     * 显示选择类型的Dialog
     */
    int temp = 0;
    private void selectDialog() {
        new AlertDialog.Builder(mContext).setTitle("选择类型")
                .setSingleChoiceItems(types, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        temp = i;
                    }
                })
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selected = temp;
                        tvStype.setText(types[selected]);
                    }
                }).show();
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

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEvent(ShareEvent event) {
        if (event.isGoShare()) {
            PhotoPickerIntent intent = new PhotoPickerIntent(mContext);
            intent.setSelectModel(SelectModel.MULTI); //
            intent.setShowCarema(true); // 是否显示拍照
            intent.setMaxTotal(4); // 最多选择照片数量，默认为6
            intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
            startActivityForResult(intent, REQUEST_CAMERA_CODE);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void publish(PublishEvent event) {
        if (event.getSucess() == 0) {
            if (event.getList() == null){
                Publication.getInstance().publishMsg(types[selected],editShare.getText().toString().trim(),
                        company,null);
            }else {
                Publication.getInstance().publishMsg(types[selected],editShare.getText().toString().trim(),
                        company,event.getList());
            }
        } else if (event.getSucess() == 1) {
            cancelDialog();
                toast("上传成功");
        } else if (event.getSucess() == -1) {
            toast("上传失败");
        }
    }

}
