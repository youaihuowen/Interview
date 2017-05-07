package com.wuyixiong.interview.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.wuyixiong.interview.R;
import com.wuyixiong.interview.adapter.NewsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recycle;
    private NewsAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    /**
     * 初始化控件
     * @param view
     */
    private void initView(View view){

        recycle = (RecyclerView) view.findViewById(R.id.recycle_home);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(manager);

        adapter = new NewsAdapter(getActivity());
        recycle.setAdapter(adapter);


    }

}
