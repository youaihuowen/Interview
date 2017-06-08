package com.wuyixiong.interview.activity;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.CommentAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.Comment;
import com.wuyixiong.interview.entity.Message;
import com.wuyixiong.interview.entity.User;
import com.wuyixiong.interview.event.CommentEvent;
import com.wuyixiong.interview.utils.Publication;
import com.wuyixiong.interview.utils.Query;
import com.wuyixiong.interview.view.CircleImageView;
import com.wuyixiong.interview.view.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class MessageDetailActivity extends BaseActivity {

    @Bind(R.id.cv_icon)
    CircleImageView cvIcon;
    @Bind(R.id.tv_nick_nime)
    TextView tvNickNime;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.iv_pic1)
    ImageView ivPic1;
    @Bind(R.id.iv_pic2)
    ImageView ivPic2;
    @Bind(R.id.iv_pic3)
    ImageView ivPic3;
    @Bind(R.id.iv_pic4)
    ImageView ivPic4;
    @Bind(R.id.lv_comment)
    MyListView lvComment;
    @Bind(R.id.edit_comment)
    EditText editComment;
    @Bind(R.id.fl_comment)
    FrameLayout flComment;
    @Bind(R.id.iv_send)
    ImageView ivSend;
    @Bind(R.id.tv_num)
    TextView tvNum;

    private ListView listView;

    private Message message;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        message = (Message) getIntent().getSerializableExtra("message");
        if (message != null) {
            initView();
            setData();
            setListener();
        }
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.lv_comment);
        adapter = new CommentAdapter(this);
        adapter.setListView(listView);
        listView.setAdapter(adapter);

        ImageLoader.getInstance().displayImage(message.getAuthor().getHeadUrl(), cvIcon);
        tvNickNime.setText(message.getAuthor().getNickName());
        tvDate.setText(message.getUpdatedAt().substring(0, 10));
        tvCompany.setText(message.getCompany());
        tvContent.setText(message.getContents());
        switch (message.getPicType()) {
            case 0:
                break;
            case 1:
                ivPic1.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(message.getPic1(), ivPic1);
                break;
            case 2:
                ivPic1.setVisibility(View.VISIBLE);
                ivPic2.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(message.getPic1(), ivPic1);
                ImageLoader.getInstance().displayImage(message.getPic2(), ivPic2);
                break;
            case 3:
                ivPic1.setVisibility(View.VISIBLE);
                ivPic2.setVisibility(View.VISIBLE);
                ivPic3.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(message.getPic1(), ivPic1);
                ImageLoader.getInstance().displayImage(message.getPic2(), ivPic2);
                ImageLoader.getInstance().displayImage(message.getPic3(), ivPic3);
                break;
            case 4:
                ivPic1.setVisibility(View.VISIBLE);
                ivPic2.setVisibility(View.VISIBLE);
                ivPic3.setVisibility(View.VISIBLE);
                ivPic4.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(message.getPic1(), ivPic1);
                ImageLoader.getInstance().displayImage(message.getPic2(), ivPic2);
                ImageLoader.getInstance().displayImage(message.getPic3(), ivPic3);
                ImageLoader.getInstance().displayImage(message.getPic4(), ivPic4);
                break;
            default:
        }

    }

    private void setData() {
        showLoadingDialog("加载中",true);
        Query.getInstance().queryComment(message.getObjectId(),0);
    }

    @Override
    protected void setListener() {

    }

    @OnClick({R.id.iv_pic1, R.id.iv_pic2, R.id.iv_pic3, R.id.iv_pic4, R.id.fl_comment, R.id.iv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_pic1:
                break;
            case R.id.iv_pic2:
                break;
            case R.id.iv_pic3:
                break;
            case R.id.iv_pic4:
                break;
            case R.id.fl_comment:
                break;
            case R.id.iv_send:
                String text = editComment.getText().toString().trim();
                if (text.length() > 0){
                    showLoadingDialog("发送中",true);
                    Publication.getInstance().publishComment(message.getObjectId(), text);
                }
                else
                    toast("评论不能为空");
                //关闭软键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                }
                break;
        }
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
    public void onFinishQuery(CommentEvent event) {
        //查询完成  是否添加评论
        if (event.getType() == 0) {
            adapter.setData(event.getList());
            adapter.notifyDataSetChanged();
            tvNum.setText(event.getList().size() + "");
            cancelDialog();
        }
        if (event.getIsAdd() == 1){
            toast("添加成功");
        }
        if (!event.isAddComment()) {
            toast("添加失败");
        }
        if (event.getIsAdd() == -1){
            toast("加载评论失败，请刷新重试");
        }
    }
}
