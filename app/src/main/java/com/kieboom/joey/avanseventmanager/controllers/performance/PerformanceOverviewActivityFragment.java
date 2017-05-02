package com.kieboom.joey.avanseventmanager.controllers.performance;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kieboom.joey.avanseventmanager.R;
import com.kieboom.joey.avanseventmanager.models.Performance;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

/**
 * A placeholder fragment containing a simple view.
 */
public class PerformanceOverviewActivityFragment extends Fragment {

    @BindView(R.id.performance_overview_viewpager) ViewPager viewPager;
    @BindView(R.id.performance_overview_next_btn) Button nextBtn;
    @BindView(R.id.performance_overview_previous_btn) Button previousBtn;

    @BindView(R.id.placeholder_frame) LinearLayout placeholderFrame;
    @BindView(R.id.viewpager_frame) FrameLayout viewpagerFrame;

    protected ViewPagerAdapter adapter;
    protected List<Performance> performances;

    public PerformanceOverviewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_performance_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        performances = SQLite.select().from(Performance.class).queryList();

        if (performances.size() > 0) {

            placeholderFrame.setVisibility(GONE);
            viewpagerFrame.setVisibility(View.VISIBLE);

            setupViewPager(viewPager);
        } else {

            placeholderFrame.setVisibility(View.VISIBLE);
            viewpagerFrame.setVisibility(View.GONE);
        }

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewPager.setCurrentItem(Math.min(viewPager.getCurrentItem() - 1, 0));
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        // hier voegt die alle fragment toe als tab pagina's
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        for (Performance performance : performances) {

            PerformanceFragment fragment = new PerformanceFragment();
            fragment.addPerformance(performance);
            adapter.addFragment(fragment);
        }

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment) {

            fragmentList.add(fragment);
        }

        public List<Fragment> getAllFragments()
        {
            return new ArrayList<>(fragmentList);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

}
