package com.seok.seok.wowsup;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.seok.seok.wowsup.fragments.fragchat.ChatFragment;
import com.seok.seok.wowsup.fragments.fragglobal.GlobalFragment;
import com.seok.seok.wowsup.fragments.fragprofile.ProfileFragment;
import com.seok.seok.wowsup.fragments.fragstory.StoryFragment;
import com.seok.seok.wowsup.utilities.GlobalWowSup;
import com.seok.seok.wowsup.utilities.MainTabPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_ntb)
    NavigationTabBar navigationTabBar;
    @BindView(R.id.main_vp)
    ViewPager viewPager;
    private MainTabPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, GlobalWowSup.getInstance().getUserWidth() + "    " + GlobalWowSup.getInstance().getUserHeight(), Toast.LENGTH_SHORT).show();
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
        viewPagerAdapter = new MainTabPagerAdapter(getSupportFragmentManager());
        initPages();
        viewPager.setAdapter(viewPagerAdapter);
        initNavigationBar();
    }

    private void initPages() {
        viewPagerAdapter.addFragment(ProfileFragment.newInstance());
        viewPagerAdapter.addFragment(ChatFragment.newInstance());
        viewPagerAdapter.addFragment(StoryFragment.newInstance());
        viewPagerAdapter.addFragment(GlobalFragment.newInstance());
    }

    private void initNavigationBar() {

        int activeColor = getResources().getColor(R.color.wowSupTabSelect);
        int inactiveColor = getResources().getColor(R.color.wowSupTabNonSelect);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.me_non_click), getResources().getColor(R.color.wowSupDark)).title("ME").build());
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.btn_talk1), getResources().getColor(R.color.wowSupDark)).title("CHAT").build());
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.hashtags_non_click), getResources().getColor(R.color.wowSupDark)).title("TAGs").build());
        models.add(new NavigationTabBar.Model.Builder(getResources().getDrawable(R.drawable.logo_sup_noclolor), getResources().getColor(R.color.wowSupDark)).title("GLOBAL").build());
        navigationTabBar.setActiveColor(activeColor);
        navigationTabBar.setInactiveColor(inactiveColor);
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setBadgeSize(10);
        navigationTabBar.setTitleSize(10);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {

            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {

            }
        });

        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
        navigationTabBar.bringToFront();
    }
}