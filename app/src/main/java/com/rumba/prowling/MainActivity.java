package com.rumba.prowling;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

    /* Tab identifiers */
    private final String TAB_A = "Match";
    private final String TAB_B = "Location";
    private final String TAB_C = "Chat";
    private final String TAB_D = "Settings";

    private String selectedTab;

    private static TabHost mTabHost;

    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;

    private boolean isTouch = false;
    public Integer xCordTouch = null;
    public Integer yCordTouch = null;
    public float transp = 0;
    public int tipoMatch = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("MainActivity class - activity_main");

        fragment1 = new ConfActivity();
        fragment2 = new PChatActivity();
        fragment3 = new ChatActivity();

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(listener);
        mTabHost.setup();

        initializeTab();
    }

// TABS

    public void initializeTab() {
        TabHost.TabSpec spec = mTabHost.newTabSpec(TAB_A);
        mTabHost.setCurrentTab(0);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }
        });
        spec.setIndicator(createTabView(TAB_A, R.drawable.tabwidget_profile));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(TAB_B);

        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }

        });

        spec.setIndicator(createTabView(TAB_B, R.drawable.tabwidget_chat));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(TAB_C);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }
        });
        spec.setIndicator(createTabView(TAB_C, R.drawable.tabwidget_chats));

        mTabHost.addTab(spec);

        mTabHost.setCurrentTab(0);
    }

    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
        public void onTabChanged(String tabId) {
            /* Set current tab.. */
            if (tabId.equals(TAB_A)) {
                pushFragments(tabId, fragment1);
                selectedTab = TAB_A;
            } else if (tabId.equals(TAB_B)) {
                pushFragments(tabId, fragment2);
                selectedTab = TAB_B;
            } else if (tabId.equals(TAB_C)) {
                pushFragments(tabId, fragment3);
                selectedTab = TAB_C;
            } else if (tabId.equals(TAB_D)) {
                pushFragments(tabId, fragment4);
                selectedTab = TAB_D;
            }
        }
    };


    public void pushFragments(String tag, Fragment fragment) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(android.R.id.tabcontent, fragment);
        ft.commit();
    }


    private View createTabView(final String tabText, final int id) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabicon, null);
        ImageView imageViewTabIcon = (ImageView) view
                .findViewById(R.id.imgIcon);
        imageViewTabIcon.setImageDrawable(getResources().getDrawable(id));

        return view;
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (selectedTab.equals(TAB_A)) {
            LinearLayout linear = (LinearLayout) findViewById(R.id.linearLayoutMatch);
            ImageView matchIMG = (ImageView) findViewById(R.id.imgMatchTransp);
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
            wm.getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            int screenHeight = dm.heightPixels;

            int X = (int) event.getX();
            int Y = (int) event.getY();
            int eventaction = event.getAction();

            switch (eventaction) {
                case MotionEvent.ACTION_DOWN:
                    xCordTouch = X;
                    yCordTouch = Y;
                    isTouch = true;
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (xCordTouch < X && tipoMatch != 2 && (X - xCordTouch) > ((screenHeight - Y) - yCordTouch)) {
                        tipoMatch = 1;
                        transp = ((float) X / (float) screenWidth) * 200;
                        String hex = Integer.toHexString((int) transp - 55);
                        if (transp > 160) {
                            matchIMG.setImageResource(R.drawable.match_agree_nofx);
                            matchIMG.setVisibility(View.VISIBLE);
                        } else {
                            matchIMG.setVisibility(View.INVISIBLE);
                        }
                        try {
                            linear.setBackgroundColor(Color.parseColor("#" + hex + "4CC700"));
                        } catch (Exception e) {
                            linear.setBackgroundColor(Color.parseColor("#00000000"));
                        }
                    } else if (xCordTouch > X && tipoMatch != 1 && (X - xCordTouch) < ((screenHeight - Y) - yCordTouch)) {
                        tipoMatch = 2;
                        transp = (1 - ((float) X / (float) screenWidth)) * 200;
                        String hex = Integer.toHexString((int) transp - 55);
                        if (transp > 150) {
                            matchIMG.setImageResource(R.drawable.match_noagree_nofx);
                            matchIMG.setVisibility(View.VISIBLE);
                        } else {
                            matchIMG.setVisibility(View.INVISIBLE);
                        }
                        try {
                            linear.setBackgroundColor(Color.parseColor("#" + hex + "C60000"));
                        } catch (Exception e) {
                            linear.setBackgroundColor(Color.parseColor("#00000000"));
                        }
                    } else if (xCordTouch > X && tipoMatch == 1) {
                        linear.setBackgroundColor(Color.parseColor("#00000000"));
                    } else if (xCordTouch < X && tipoMatch == 2) {
                        linear.setBackgroundColor(Color.parseColor("#00000000"));
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    linear.setBackgroundColor(Color.parseColor("#00000000"));
                    tipoMatch = 0;
                    matchIMG.setVisibility(View.INVISIBLE);
                    if (transp > 150) {
                        if (xCordTouch > X && tipoMatch != 1 && (X - xCordTouch) < ((screenHeight - Y) - yCordTouch)) {
                            ImageView buttonDislike = (ImageView) findViewById(R.id.imgButtonDislike);
                            buttonDislike.performClick();
                        } else {
                            ImageView buttonLike = (ImageView) findViewById(R.id.imgButtonLike);
                            buttonLike.performClick();
                        }
                        //Toast.makeText(MainActivity.this, "LEFT "+X, Toast.LENGTH_SHORT).show();
                    } else {
                        //Toast.makeText(MainActivity.this, "RIGHT "+X, Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
            return true;
        }*/
}