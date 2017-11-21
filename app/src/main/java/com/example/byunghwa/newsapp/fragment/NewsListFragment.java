package com.example.byunghwa.newsapp.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.byunghwa.newsapp.OnRefreshStarted;
import com.example.byunghwa.newsapp.R;
import com.example.byunghwa.newsapp.adapter.CustomStaggeredGridLayoutManager;
import com.example.byunghwa.newsapp.adapter.NewsListRecyclerViewAdapter;
import com.example.byunghwa.newsapp.data.NewsListLoader;
import com.example.byunghwa.newsapp.model.News;
import com.example.byunghwa.newsapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends Fragment implements NewsListRecyclerViewAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<List<News>>, OnRefreshStarted {

    private static final String TAG = "NewsListFrag";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private NewsListRecyclerViewAdapter mAdapter;

    private List<News> mNewsList;

    public NewsListFragment() {
        // Required empty public constructor
    }

    private void fetchNewsList() {
        if (NetworkUtil.isNetworkAvailable(getContext())) {
            getLoaderManager().getLoader(1).forceLoad();
        } else {
            Toast.makeText(getActivity(), "No network connection.", Toast.LENGTH_SHORT).show();

            if (mNewsList == null || mNewsList.size() == 0) {
                mEmptyView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);// setting this to true will prevent the whole list from refreshing when
        // new items have been added to the list (which prevents list from flashing)

        mAdapter = new NewsListRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        int columnCount = 2;
        CustomStaggeredGridLayoutManager manager = new CustomStaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);

        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_layout);

        /*
         note that when you use a SwipeRefreshLayout, you have to set this OnRefreshListener to it
         or the indicator wouldn't show up
          */
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchNewsList();
            }
        });

        mEmptyView = rootView.findViewById(R.id.tv_empty_view);

        getLoaderManager().initLoader(1, null, this).forceLoad();

        Toolbar toolbar = rootView.findViewById(R.id.toolbar_main);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.app_name));
        }

        setOnClickListener();
        return rootView;
    }

    private void setOnClickListener() {
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int clickedItemPosition) {
        if (mNewsList != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mNewsList.get(clickedItemPosition).getWebURL()));
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsListLoader(getActivity(), new FetchMyDataTaskStartedListener());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        if (data != null) {
            mNewsList = data;
            Log.i(TAG, "news list size: " + data.size());
            mAdapter.swapData((ArrayList<News>) data);
            mSwipeRefreshLayout.setRefreshing(false);
            if (data.size() > 0) {
                mEmptyView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.swapData(null);
    }

    @Override
    public void onTaskStarted() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    // class for showing refresh indicator when the task starts
    public class FetchMyDataTaskStartedListener implements OnRefreshStarted {

        @Override
        public void onTaskStarted() {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }
}
