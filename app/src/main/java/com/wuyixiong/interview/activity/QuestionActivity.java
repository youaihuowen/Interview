package com.wuyixiong.interview.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.AnswerAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.db.DBManager;
import com.wuyixiong.interview.entity.Question;
import com.wuyixiong.interview.entity.QuestionList;
import com.wuyixiong.interview.event.CollectEvent;
import com.wuyixiong.interview.event.IsCollection;
import com.wuyixiong.interview.event.SendError;
import com.wuyixiong.interview.utils.CollectionOperate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QuestionActivity extends BaseActivity {

    @Bind(R.id.tv_back)
    TextView back;
    @Bind(R.id.tv_number)
    TextView number;
    @Bind(R.id.tv_like)
    TextView like;
    @Bind(R.id.vp_answer)
    ViewPager vpAnswer;

    private AnswerAdapter adapter;
    private ArrayList<Question> list = new ArrayList<>();
    private ArrayList<String> id ;//所收藏的试题的id
    private QuestionList ql;
    private int posotion1 = 0;
    private int size;
    private SharedPreferences shared;
    private String questionCollectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);
        ql = (QuestionList) getIntent().getSerializableExtra("questionList");
        posotion1 = ql.getPosition();
        list = ql.getList();
        size = list.size();
        //设置总条数
        number.setText((posotion1 + 1) + "/" + size);

        shared = getSharedPreferences("collectionId", MODE_PRIVATE);
        questionCollectionId = shared.getString("questionCollectionId", "null");

        //查询所有试题收藏的id
        id = DBManager.getInstance(this).queryQuestionId(list.get(0).getType());

        //设置viewpager
        adapter = new AnswerAdapter(this);
        adapter.setList(list);
        vpAnswer.setAdapter(adapter);
        vpAnswer.setCurrentItem(posotion1);

        //第一个显示时判断是否收藏
        for (int i = 0;i<id.size();i++){
            if (id.get(i).equals(list.get(posotion1).getObjectId())){
                like.setText("取消");
                break;
            }
            if (i == id.size()-1){
                like.setText("收藏");
            }
        }
        setListener();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        vpAnswer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                posotion1 = position;
                number.setText((position + 1) + "/" + size);
                for (int i = 0;i<id.size();i++){
                    if (id.get(i).equals(list.get(position).getObjectId())){
                        like.setText("取消");
                        break;
                    }
                    if (i == id.size()-1){
                        like.setText("收藏");
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.tv_back, R.id.tv_like})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_like:
                if (like.getText().toString().equals("收藏")) {
                    CollectionOperate.getInstance().addQuestionCollection(this, questionCollectionId,
                            list.get(posotion1));
                } else {
                    CollectionOperate.getInstance().removeQuestionCollection(this, questionCollectionId,
                            list.get(posotion1));

                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void success(CollectEvent event) {
        //收藏成功
        if (event.isCollected()) {
            toast("收藏成功");
            id.add(list.get(posotion1).getObjectId());
            like.setText("取消");

        } else {
            toast("取消成功");
            id.remove(list.get(posotion1).getObjectId());
            like.setText("收藏");
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void failed(SendError event) {
        //收藏失败或取消失败
        if (event.getErrorId() == 2) {
            toast(event.getErrorText());
        } else if (event.getErrorId() == 3) {
            toast(event.getErrorText());
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void checked(IsCollection event) {
        //判断是否已经收藏
        if (event.isCollected()) {
            like.setText("取消");
        } else {
            like.setText("收藏");
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
}
