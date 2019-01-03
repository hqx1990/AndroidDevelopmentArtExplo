package com.bestvike.androiddevelopmentartexploration.paging.pagingLibrary;

import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.arch.paging.TiledDataSource;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Paging分页加载框架的使用
 */
public class PagingLibraryActivity extends BaseActivity {
    private PagedList<DataBean> mPagedList;
    private MyDataSource mDataSource;

    private RecyclerView mRecyclerView;
    private PagedListAdapter mAdapter;

    private LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asynclist_activity);
        mDataSource = new MyDataSource();
        makePageList();

        mRecyclerView = findViewById(R.id.recyclerView);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setList(mPagedList);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastPos;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                lastPos = mLayoutManager.findLastVisibleItemPosition();

                mPagedList.loadAround(lastPos);//触发Android Paging的加载事务逻辑。
            }
        });

    }

    @Override
    protected void destroyPresenter() {

    }

    private void makePageList() {
        PagedList.Config mPagedListConfig = new PagedList.Config.Builder()
                .setPageSize(3) //分页数据的数量。在后面的DataSource之loadRange中，count即为每次加载的这个设定值。
                .setPrefetchDistance(5) //初始化时候，预取数据数量。
                .setEnablePlaceholders(false)
                .build();

        mPagedList = new PagedList.Builder()
                .setConfig(mPagedListConfig)
                .setDataSource(mDataSource)
                .setMainThreadExecutor(new BackgroundThreadTask()) //初始化阶段启用
                .setBackgroundThreadExecutor(new MainThreadTask()) //初始化阶段启动
                .build();
    }

    private class BackgroundThreadTask implements Executor {
        public BackgroundThreadTask() {
            this.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("BackgroundThreadTask", "run");
                }
            });
        }

        @Override
        public void execute(@NonNull Runnable runnable) {
            runnable.run();
        }
    }

    private class MainThreadTask implements Executor {
        public MainThreadTask() {
            this.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("MainThreadTask", "run");
                }
            });
        }

        @Override
        public void execute(@NonNull Runnable runnable) {
            runnable.run();
        }
    }

    private class MyDataSource extends TiledDataSource<DataBean> {

        @Override
        public int countItems() {
            return TiledDataSource.COUNT_UNDEFINED;
        }

        /**
         * 注意，这里需要后台线程化。
         *
         * @param startPosition
         * @param count
         * @return
         */
        @Override
        public List<DataBean> loadRange(int startPosition, int count) {
            Log.d("MyDataSource", "loadRange:" + startPosition + "," + count);
            List<DataBean> list = loadData(startPosition, count);
            return list;
        }
    }

    /**
     * 假设这里需要做一些后台线程的数据加载任务。
     *
     * @param startPosition
     * @param count
     * @return
     */
    private List<DataBean> loadData(int startPosition, int count) {
        List<DataBean> list = new ArrayList();

        for (int i = 0; i < count; i++) {
            DataBean data = new DataBean();
            data.id = startPosition + i;
            data.content = "zhangphil@" + data.id;
            list.add(data);
        }

        return list;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;
        public TextView text2;

        public MyViewHolder(View itemView) {
            super(itemView);

            text1 = itemView.findViewById(android.R.id.text1);
            text1.setTextColor(Color.RED);

            text2 = itemView.findViewById(android.R.id.text2);
            text2.setTextColor(Color.BLUE);
        }
    }

    private class MyAdapter extends PagedListAdapter<DataBean, MyViewHolder> {
        public MyAdapter() {
            super(mDiffCallback);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(android.R.layout.simple_list_item_2, null);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            DataBean data = mPagedList.get(position);
            holder.text1.setText(String.valueOf(position));
            holder.text2.setText(String.valueOf(data.content));
        }
    }

    private DiffCallback<DataBean> mDiffCallback = new DiffCallback<DataBean>() {

        @Override
        public boolean areItemsTheSame(@NonNull DataBean oldItem, @NonNull DataBean newItem) {
            Log.d("DiffCallback", "areItemsTheSame");
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull DataBean oldItem, @NonNull DataBean newItem) {
            Log.d("DiffCallback", "areContentsTheSame");
            return TextUtils.equals(oldItem.content, newItem.content);
        }
    };

    private class DataBean {
        public int id;
        public String content;
    }


}

