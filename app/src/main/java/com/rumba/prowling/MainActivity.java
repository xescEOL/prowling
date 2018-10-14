package com.rumba.prowling;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;

public class MainActivity extends FragmentActivity {

    /* Tab identifiers */
    private final String TAB_A = "Match";
    private final String TAB_B = "Location";
    private final String TAB_C = "Chat";
    private final String TAB_D = "Settings";

    private String selectedTab;

    private static TabHost mTabHost;

    private MatchActivity fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment1 = new MatchActivity();
        fragment2 = new LocationActivity();
        fragment3 = new ChatActivity();
        fragment4 = new ConfActivity();



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
        spec.setIndicator(createTabView(TAB_A, R.drawable.tabwidget_match));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(TAB_B);

        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }

        });

        spec.setIndicator(createTabView(TAB_B, R.drawable.tabwidget_location));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(TAB_C);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }
        });
        spec.setIndicator(createTabView(TAB_C, R.drawable.tabwidget_chat));

        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(TAB_D);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(android.R.id.tabcontent);
            }
        });
        spec.setIndicator(createTabView(TAB_D, R.drawable.tabwidget_conf));
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

    public static void tabfresh() {
        mTabHost.setCurrentTab(0);
    }

    public static void tabfresh_sal() {
        mTabHost.setCurrentTab(1);
    }
}