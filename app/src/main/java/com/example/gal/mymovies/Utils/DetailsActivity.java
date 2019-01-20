package com.example.gal.mymovies.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gal.mymovies.Entities.MovieContent;
import com.example.gal.mymovies.MovieDetailsFragment;
import com.example.gal.mymovies.R;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM_POSITION = "init-position-data";
    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager viewPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        viewPager = findViewById(R.id.pager);
        viewPagerAdapter = new DetailsActivity.ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        int startPosition = getIntent().getIntExtra(EXTRA_ITEM_POSITION, 0);
        viewPager.setCurrentItem(startPosition);
    }

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MovieDetailsFragment.newInstance(MovieContent.MOVIES.get(position));
            }

        @Override
        public int getCount() {
            return MovieContent.MOVIES.size();
        }
    }
}
