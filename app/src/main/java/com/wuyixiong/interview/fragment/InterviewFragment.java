package com.wuyixiong.interview.fragment;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.wuyixiong.interview.R;
import com.wuyixiong.interview.activity.MainActivity;
import com.wuyixiong.interview.activity.QuestionActivity;
import com.wuyixiong.interview.activity.RecommendActivity;
import com.wuyixiong.interview.adapter.QuestionAdapter;
import com.wuyixiong.interview.adapter.QuestionTypeAdapter;
import com.wuyixiong.interview.base.BaseActivity;
import com.wuyixiong.interview.entity.Question;
import com.wuyixiong.interview.entity.QuestionList;
import com.wuyixiong.interview.view.HorizontalListView;
import com.wuyixiong.interview.view.MyListView;
import com.wuyixiong.interview.view.MyScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class InterviewFragment extends Fragment implements AdapterView.OnItemClickListener,
        MyScrollView.OnScrollListener {


    @Bind(R.id.ll_suspension1)
    FrameLayout suspension1;
    @Bind(R.id.iv_head_interview)
    ImageView ivHead;
    @Bind(R.id.ll_explorer_hot)
    LinearLayout explorerHot;
    @Bind(R.id.ll_explorer_post)
    LinearLayout explorerPost;
    @Bind(R.id.ll_explorer_collection)
    LinearLayout explorerCollection;
    @Bind(R.id.ll_explorer_offline)
    LinearLayout explorerOffline;
    @Bind(R.id.fl_suspension2)
    FrameLayout suspension2;
    @Bind(R.id.part_head)
    LinearLayout partHead;

    private HorizontalListView horlist1;
    private HorizontalListView horlist2;
    private MyScrollView sv;
    private MyListView listView;

    private ArrayList<String> typeList;
    ArrayList<Question> questionList;

    private QuestionTypeAdapter typeAdapter;
    private QuestionAdapter questionAdapter;

    private Question question = new Question();
    private int height;


    public InterviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_interview, container, false);
        ButterKnife.bind(this, v);
        initView(v);
        setData();
        setListView();
        setListener();
        //获得高度
        ViewTreeObserver vto = partHead.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                height = partHead.getHeight();
                partHead.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        return v;
    }


    public void initView(View v) {
        sv = (MyScrollView) v.findViewById(R.id.sv_interview);
        listView = (MyListView) v.findViewById(R.id.lv_question);
        horlist1 = (HorizontalListView) v.findViewById(R.id.horlist1);
        horlist2 = (HorizontalListView) v.findViewById(R.id.horlist2);
    }

    public void setListener() {
        horlist1.setOnItemClickListener(this);
        horlist2.setOnItemClickListener(this);

        //为了解决切换fragment后scrollview不置顶的问题
        ivHead.setFocusableInTouchMode(true);
        ivHead.setFocusable(true);
        ivHead.requestFocus();

        sv.setOnScrollListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), QuestionActivity.class);
                intent.putExtra("questionList",new QuestionList(questionList,i));
                startActivity(intent);
            }
        });
    }

    public void setData() {
        String types[] = {"Android", "ios", "Html", "C/C++", "PHP", "SQL", "Linux", "Python"};
        typeList = new ArrayList<String>(Arrays.asList(types));
        //从网络获取类型数据
//        Type questionType = new Type();
//        BmobQuery<Type> query = new BmobQuery<>();
//        query.addWhereNotEqualTo("typeName","aaa");
//        query.findObjects(new FindListener<Type>() {
//            @Override
//            public void done(List<Type> list, BmobException e) {
//                if (e == null){
//                    Toast.makeText(getActivity(),"成功"+list.size(),Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getActivity(),"失败",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        queryQuestion("Android");
    }

    /**
     * 设置横向和纵向的listview
     */
    private void setListView() {
        typeAdapter = new QuestionTypeAdapter(getContext(), typeList);
        horlist1.setAdapter(typeAdapter);
        horlist2.setAdapter(typeAdapter);
        questionAdapter = new QuestionAdapter(getContext());
        listView.setAdapter(questionAdapter);
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toast.makeText(getContext(),"---------",Toast.LENGTH_SHORT).show();
        ButterKnife.unbind(this);
    }


    @OnClick({R.id.iv_head_interview, R.id.ll_explorer_hot, R.id.ll_explorer_post,
            R.id.ll_explorer_collection, R.id.ll_explorer_offline})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head_interview:
                break;
            case R.id.ll_explorer_hot:
                Intent intent0 = new Intent(getActivity(), RecommendActivity.class);
                intent0.putExtra("recommendtype","recommend");
                startActivity(intent0);
                break;
            case R.id.ll_explorer_post:
                Intent intent1 = new Intent(getActivity(), RecommendActivity.class);
                intent1.putExtra("recommendtype","skill");
                startActivity(intent1);
                break;
            case R.id.ll_explorer_collection:
                Intent intent2 = new Intent(getActivity(), RecommendActivity.class);
                intent2.putExtra("recommendtype","company");
                startActivity(intent2);
                break;
            case R.id.ll_explorer_offline:
                Intent intent3 = new Intent(getActivity(), RecommendActivity.class);
                intent3.putExtra("recommendtype","offline");
                startActivity(intent3);
                break;
        }
    }

    /**
     * 类型listview的点击监听
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        typeAdapter.setSelected(i);
        queryQuestion(typeList.get(i));
        typeAdapter.notifyDataSetChanged();
    }


    /**
     * 查询面试题
     *
     * @param s
     */
    private void queryQuestion(String s) {
        BmobQuery<Question> query = new BmobQuery<>();
        query.addWhereEqualTo("type", s);
        if (MainActivity.havaNetwork){
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }else {
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);
        }
        ((BaseActivity)getActivity()).showLoadingDialog("加载中",true);
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> list, BmobException e) {
                if (e == null) {
                    questionList = (ArrayList<Question>) list;
                    questionAdapter.setData(questionList);
                    questionAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
                }
                ((BaseActivity)getActivity()).cancelDialog();

            }
        });
    }

    /**
     * 用于设置悬浮效果
     * @param scrollY
     */
    @Override
    public void onScroll(int scrollY) {
        if (scrollY >= height){
            suspension2.setVisibility(View.GONE);
            suspension1.setVisibility(View.VISIBLE);
        }else {
            suspension1.setVisibility(View.GONE);
            suspension2.setVisibility(View.VISIBLE);
        }
    }
}
