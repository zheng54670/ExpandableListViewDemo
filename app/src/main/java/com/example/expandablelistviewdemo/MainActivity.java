package com.example.expandablelistviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.expandablelistviewdemo.adapter.ChapterAdapter;
import com.example.expandablelistviewdemo.bean.Chapter;
import com.example.expandablelistviewdemo.bean.ChapterLab;
import com.example.expandablelistviewdemo.biz.ChapterBiz;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView mExpandableListView;
    private BaseExpandableListAdapter mAdapter;
    private List<Chapter> mDatas = new ArrayList<>();
    private static final String TAG = "MainActivity";

    private ChapterBiz chapterBiz = new ChapterBiz();

    private Button mBtnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initEvents();

        loadData(true);
    }

    private void loadData(boolean useCache) {
        chapterBiz.loadData(this, new ChapterBiz.CallBack() {
            @Override
            public void onSuccess(List<Chapter> chapterList) {
                mDatas.clear();
                mDatas.addAll(chapterList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception ex) {

                ex.printStackTrace();
            }
        }, useCache);
    }

    private void initEvents() {

        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(false);
            }
        });
        /*
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d(TAG, "onChildClick: groupPosition" + groupPosition + ",childPosition" + childPosition);
                return false;
            }
        });
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d(TAG, "onGroupClick: " + groupPosition);

                return false;
            }
        });

        mExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.d(TAG, "onGroupCollapse: " + groupPosition);
            }
        });

        mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.d(TAG, "onGroupExpand: " + groupPosition);
            }
        });
        */
    }

    private void initViews() {

        mBtnRefresh = findViewById(R.id.id_btn_refresh);
        mExpandableListView = findViewById(R.id.expandable_ListView);
        mDatas.clear();
//        mDatas.addAll(ChapterLab.generateMockData());
        mAdapter = new ChapterAdapter(this, mDatas);
        mExpandableListView.setAdapter(mAdapter);
    }
}
