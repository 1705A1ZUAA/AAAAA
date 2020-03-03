package com.wd.health.fragment;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.wd.health.R;
import com.wd.health.app.App;
import com.wd.health.base.BaseFragment;
import com.wd.health.shipin.ToastUtils;
import com.wd.health.shipin.bean.ChaXunShiPin_DataBean;
import com.wd.health.shipin.bean.ChaXunShiPin_ResutBean;
import com.wd.health.shipin.bean.CollectBean;
import com.wd.health.shipin.bean.Commentbean;
import com.wd.health.shipin.bean.CurtainView;
import com.wd.health.shipin.bean.VideoCommentList;
import com.wd.health.shipin.bean.Video_TablayoutDataBean;
import com.wd.health.shipin.bean.Video_TablayoutResultBean;
import com.wd.health.shipin.bean.Videobuybean;
import com.wd.health.shipin.concroct.Video_CategoryListContract;
import com.wd.health.shipin.present.Video_CategoryListPresent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import LayoutManagerGroup.src.main.java.com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import LayoutManagerGroup.src.main.java.com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyFragment extends BaseFragment<Video_CategoryListPresent> implements Video_CategoryListContract.Video_CategoryView {
    private static final String TAG = "ViewPagerActivity";
    @BindView(R.id.recycler)
    RecyclerView mrecycler;
    @BindView(R.id.line_bottom)
    VideoView lineBottom;
    @BindView(R.id.bottom)
    LinearLayout bottom;
    Unbinder unbinder;
    @BindView(R.id.curtainview)
    CurtainView curtainview;

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private ViewPagerLayoutManager mLayoutManager;
    public ArrayList<String> list = new ArrayList();
    public ArrayList<String> imaglist = new ArrayList();
    public ArrayList<RadioButton> rbtlist = new ArrayList();
    int querenzhi;
    int flag = 0;
    private String shearUrl;
    private ChaXunShiPin_DataBean bean;
    private List<ChaXunShiPin_DataBean> result;
    private MyAdapter myAdapter;
    private RadioButton rbt_one;
    private RadioButton rbt_two;
    private RadioButton rbt_three;
    private RadioButton rbt_four;
    private RadioButton rbt_five;
    private RadioButton rbt_six;
    private ImageView img_curtain_rope;
    private View itemView;
    private VideoView videoView;
    //private SeekBar seekBar;
    private SeekBar seekBar;
    //关于进度条每次更新需要更新ui
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {

        public void run() {
            if (videoView.isPlaying()) {
                int current = videoView.getCurrentPosition();
                seekBar.setProgress(current);
                Log.d("hao0", current + "");

            }

            handler.postDelayed(runnable, 500);
        }
    };
    private ImageView collect_button;
    private int id;


    @Override
    protected Video_CategoryListPresent providePresenter() {
        return new Video_CategoryListPresent();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.my_fragment;
    }

    //点击上面的视频条目的成功的方法
    @Override
    public void succeed(Video_TablayoutResultBean video_tablayoutResultBean) {
        List<Video_TablayoutDataBean> result = video_tablayoutResultBean.getResult();
        int size = result.size();
        Log.d("sizesss", size + "");

        for (int j = 0; j < result.size(); j++) {
            Video_TablayoutDataBean video_tablayoutDataBean = result.get(j);
            HashMap<String, Object> map = new HashMap<>();
            int ids = video_tablayoutDataBean.getId();
            map.put("page", 1);
            map.put("count", 10);
            map.put("categoryId", ids);
            mPresenter.chaXun(map);
            querenzhi = 1;
        }
        for (int i = 0; i < result.size(); i++) {
            Video_TablayoutDataBean video_tablayoutDataBean = result.get(i);
            String name = video_tablayoutDataBean.getName();

            RadioButton radioButton = rbtlist.get(i);
            radioButton.setText(name);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.clear();
                    int id = video_tablayoutDataBean.getId();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("page", 1);
                    map.put("count", 10);
                    map.put("categoryId", id);
                    mPresenter.chaXun(map);
                    Log.d("id是", id + "");
                    querenzhi = 2;
                }
            });
        }
    }

    @Override
    public void failure(Throwable throwable) {

    }

    //查询视频成功的方法
    @Override
    public void onChaXunShiPinSuccess(ChaXunShiPin_ResutBean chaXunShiPin_resutBean) {
        Log.d("是否成功", chaXunShiPin_resutBean.getMessage());
        Log.d("aaa", "bbb");
        List<ChaXunShiPin_DataBean> result = chaXunShiPin_resutBean.getResult();

        if (querenzhi == 1) {
            this.result = chaXunShiPin_resutBean.getResult();
            for (int i = 0; i < this.result.size(); i++) {
                bean = this.result.get(i);
                shearUrl = bean.getShearUrl();
                String abstracts = bean.getAbstracts();
                int whetherBuy = bean.getWhetherBuy();
                Log.d("789456", whetherBuy + "");
                int whetherCollection = bean.getWhetherCollection();
                int id = bean.getId();
                Log.d("ccc", id + "");
                //SharedPreferences config= getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
                Log.d("whe", whetherCollection + "");
                Log.d("whes", bean.getId() + "");
                //list.add(shearUrl);
                list.add(shearUrl);


            }
            Log.d("456", list.size() + "");

            ArrayList<String> list = this.list;
            myAdapter.setSlist(this.list);
            myAdapter.notifyDataSetChanged();
            mrecycler.setAdapter(mAdapter);


        } else if (querenzhi == 2) {
            this.result = chaXunShiPin_resutBean.getResult();
            for (int i = 0; i < this.result.size(); i++) {
                bean = this.result.get(i);
                shearUrl = bean.getOriginalUrl();
                Log.d("5555555", shearUrl);
                id = bean.getId();

                //String originalUrl = bean.getOriginalUrl();
                //list.add(shearUrl);
                list.add(shearUrl);
            }

            Log.d("456", list.size() + "");
            ArrayList<String> list = this.list;
            myAdapter.setSlist(this.list);
            myAdapter.notifyDataSetChanged();
            mrecycler.setAdapter(mAdapter);

        }
    }

    @Override
    protected void initData() {
        super.initData();
        //EventBus.getDefault().register(this);

        mPresenter.categoryListShow();
        initListener();
        rbtlist.add(rbt_one);
        rbtlist.add(rbt_two);
        rbtlist.add(rbt_three);
        rbtlist.add(rbt_four);
        rbtlist.add(rbt_five);
        rbtlist.add(rbt_six);
        //点击进行视频收藏
    }

    @Override
    protected void initView() {
        super.initView();
        rbt_one = (RadioButton) curtainview.findViewById(R.id.rbt_one);
        rbt_two = (RadioButton) curtainview.findViewById(R.id.rbt_two);
        rbt_three = (RadioButton) curtainview.findViewById(R.id.rbt_three);
        rbt_four = (RadioButton) curtainview.findViewById(R.id.rbt_four);
        rbt_five = (RadioButton) curtainview.findViewById(R.id.rbt_five);
        rbt_six = (RadioButton) curtainview.findViewById(R.id.rbt_six);

        img_curtain_rope = (ImageView) curtainview.findViewById(R.id.img_curtain_rope);
        myAdapter = new MyAdapter();
        mLayoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.VERTICAL);
        mAdapter = new MyAdapter();
        mrecycler.setLayoutManager(mLayoutManager);
    }


    //监听位置
    private void initListener() {
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onInitComplete() {
                Log.e(TAG, "onInitComplete");
                playVideo(0);
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG, "释放位置:" + position + " 下一页:" + isNext);
                int index = 0;
                if (isNext) {
                    index = 0;
                } else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onPageSelected(int position, boolean isBottom) {
                Log.e(TAG, "选中位置:" + position + "  是否是滑动到底部:" + isBottom);
                playVideo(0);
            }
        });

    }

    //去执行视频播放  暂停  播放的方法
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void playVideo(int position) {
        View itemViews = mrecycler.getChildAt(0);
        videoView = (VideoView) itemViews.findViewById(R.id.video_view);
        final ImageView imgPlay = itemViews.findViewById(R.id.img_play);
        final ImageView imgThumb = itemViews.findViewById(R.id.img_thumb);
        final RelativeLayout rootView = itemViews.findViewById(R.id.root_view);
        //seekBar = (SeekBar) itemViews.findViewById(R.id.seekBar);
//        seekBar = (SeekBar) itemViews.findViewById(R.id.seekBar);
//        collect_button = (ImageView) itemViews.findViewById(R.id.collect_button);

        /*  seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);*/
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        //视频开始播放
        videoView.start();



      /*  if (videoView.isPlaying()){
            handler.postDelayed(runnable, 0);
            seekBar.setMax(videoView.getDuration());
        }else {
            ToastUtils.showShort(App.getAppContext(),"视频不在播放中");
        }*/
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mediaPlayer[0] = mp;
                mp.setLooping(true);
                imgThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        //点击视频的点击播放的按钮
        imgPlay.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;

            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    imgPlay.animate().alpha(1f).start();
                    videoView.pause();
                    isPlaying = false;
                } else {
                    imgPlay.animate().alpha(0f).start();
                    videoView.start();
                    int duration = videoView.getDuration();
                    Log.d("mimi", duration + "");
                    int max = seekBar.getMax();
                    Log.d("max", max + "");
                    //开始播放时去发送
                    handler.postDelayed(runnable, 0);
                    seekBar.setMax(videoView.getDuration());

                    isPlaying = true;
                }
            }
        });


       /*     handler.postDelayed(runnable, 0);
            seekBar.setMax(videoView.getDuration());*/
        //播放已经成功的方法
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ToastUtils.showLong(App.getAppContext(), "播放已经完成");
            }
        });


//        ImageView collect_buttons = (ImageView) itemViews.findViewById(R.id.collect_button);
//        collect_buttons.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                switch (flag) {
//
//                    case 0:
//
//                        v.setActivated(false);
//
//                        flag = 1;
//
//                        break;
//
//                    case 1:
//
//                        v.setActivated(true);
//
//                        flag = 0;
//                        //mPresenter.colleCtion(id );
//
//                        break;
//
//                }
//
//            }
//
//        });
//
//
    }


    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        // 当进度条停止修改的时候触发
        //final VideoView videoView = itemView.findViewById(R.id.video_view);
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();
            if (videoView.isPlaying()) {
                // 设置当前播放的位置
                videoView.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }
    };

    //每次滑动视频需要执行的方法
    @TargetApi(Build.VERSION_CODES.O)
    private void releaseVideo(int index) {
        itemView = mrecycler.getChildAt(index);
        if (itemView != null) {
            final VideoView videoViews = itemView.findViewById(R.id.video_view);
            final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
            final ImageView imgPlay = (ImageView) itemView.findViewById(R.id.img_play);
            //videoViews.pause();
            videoViews.stopPlayback();

            //seekBar.setMax(0);
            imgThumb.animate().alpha(1).start();
            imgPlay.animate().alpha(5f).start();
            //imgPlay.setBackgroundColor(Color.RED);
        }


    }

    //点击了home键需要暂停
    @Override
    public void onPause() {
        super.onPause();
        View itemViews = mrecycler.getChildAt(0);
        ImageView imgPlay = (ImageView) itemViews.findViewById(R.id.img_play);
        videoView.pause();
        imgPlay.animate().alpha(1f).start();
    }

    @Override
    public void onChaXunShiPinFailure(Throwable e) {
        Log.d("1111111111111", "失败");
    }


    //视频收藏的成功方法
    @Override
    public void collectionSucceed(CollectBean collectBean) {
        Log.d("shipin", collectBean.getMessage());
    }

    @Override
    public void collectionFailure(Throwable throwable) {

    }

    @Override
    public void commentSucceed(Commentbean comment) {

    }

    @Override
    public void commentFailure(Throwable throwable) {

    }

    @Override
    public void videobuySucceed(Videobuybean videobuybean) {

    }

    @Override
    public void videobuyFailure(Throwable throwable) {

    }

    @Override
    public void commentListSucceed(VideoCommentList videoCommentList) {

    }

    @Override
    public void commentListFailure(Throwable throwable) {

    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        //private int[] imgs = {R.mipmap.img_video_1,R.mipmap.img_video_2};
        //private int[] videos = {R.raw.video_1,R.raw.video_2};
        public ArrayList<String> slist = new ArrayList<>();

        public void setSlist(ArrayList<String> slist) {
            this.slist = slist;
        }

        public MyAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //holder.img_thumb.setImageResource(imgs[position%2]);

            ArrayList<String> dlist = MyFragment.this.list;
            Log.d("123", dlist.size() + "");
            Log.d("489", slist.size() + "");
            if (dlist.size() != 0) {
                //holder.img_thumb.setImageBitmap();
                holder.videoView.setVideoURI(Uri.parse(dlist.get(position)));
                //holder.videoView.setVideoURI(Uri.parse(dlist.get(position % 3)));
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView img_thumb;
            VideoView videoView;
            ImageView img_play;
            RelativeLayout rootView;

            public ViewHolder(View itemView) {
                super(itemView);
                img_thumb = itemView.findViewById(R.id.img_thumb);
                videoView = itemView.findViewById(R.id.video_view);
                img_play = itemView.findViewById(R.id.img_play);
                rootView = itemView.findViewById(R.id.root_view);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
