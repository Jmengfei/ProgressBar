package com.example.jmf.testdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends Activity {


    TextView tvPrecent;
    ProgressBar pbProgressbar;
    Button btnStart;

    //假设的总进度，最多为100，可自行调整
    private int status = 100;
    //当前进度
    private int currentStatue;
    /**
     * 当前位置
     */
    private float currentPosition;

    //得到屏幕的总宽度
    private int width;
    private float scrollDistance;
    private int tvWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPrecent = (TextView) findViewById(R.id.progress_precent);
        pbProgressbar = (ProgressBar) findViewById(R.id.pb_progressbar);
        btnStart = (Button) findViewById(R.id.btn_start);

        // 得到progressBar控件的宽度
        ViewTreeObserver vto2 = pbProgressbar.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pbProgressbar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                width = pbProgressbar.getWidth();
            }
        });

        //初始化监听
        initListener();

    }

    private void initListener() {
        //开始按钮的点击事件
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentStatue = 0;
                currentPosition = 0;
                pbProgressbar.setProgress(0);
                //效果的实现
                initAchieve();
            }
        });
    }

    private void initAchieve() {
        //开启分线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                //每一段要移动的距离
                scrollDistance = (float) ((1.0 / pbProgressbar.getMax()) * width);
                for (int i = 0; i < status; i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 控制进度条的增长进度
                            pbProgressbar.incrementProgressBy(1);
                            currentStatue++;
                            tvPrecent.setText(currentStatue + "%");
                            // 得到字体的宽度
                            tvWidth = tvPrecent.getWidth();
                            currentPosition += scrollDistance;
                            //做一个平移动画的效果
                            if (tvWidth + currentPosition <= width - tvPrecent.getPaddingRight()) {
                                tvPrecent.setTranslationX(currentPosition);
                            }
                        }
                    });
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
