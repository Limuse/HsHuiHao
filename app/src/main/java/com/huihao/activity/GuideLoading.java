package com.huihao.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.huihao.R;
import com.huihao.common.DepthPageTransformer;
import com.leo.base.activity.LActivity;
import com.leo.base.util.LSharePreference;

public class GuideLoading extends LActivity {
	private Button start_future;
	private ViewPager viewPager;
	private int[] imageIds = new int[] { R.mipmap.guide_one,
			R.mipmap.guide_two, R.mipmap.guide_three, R.mipmap.guide_four };
	private List<ImageView> imageList = new ArrayList<ImageView>();

	@Override
	protected void onLCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_guide_loading);
		InitView();
		initData();
		initViewPage();
		initClick();
	}

	private void InitView() {
		start_future = (Button) findViewById(R.id.start_future);
		start_future.setVisibility(View.GONE);
		viewPager = (ViewPager) findViewById(R.id.viewpage);
	}

	private void initData() {
		for (int i = 0; i < imageIds.length; i++) {
			ImageView imageView = new ImageView(getApplicationContext());
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setImageResource(imageIds[i]);
			imageList.add(imageView);
		}
	}

	private void initViewPage() {
		viewPager.setPageTransformer(true, new DepthPageTransformer());
		viewPager.setAdapter(new PagerAdapter() {

			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(imageList.get(position));
				return imageList.get(position);
			}

			public void destroyItem(ViewGroup container, int position,
					Object object) {

				container.removeView(imageList.get(position));
			}

			public int getCount() {
				return imageList.size();
			}
		});
	}

	private void initClick() {
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			public void onPageSelected(int position) {
				if (position == 3) {
					start_future.setVisibility(View.VISIBLE);
				} else {
					start_future.setVisibility(View.GONE);
				}
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	public void start(View v) {
		LSharePreference.getInstance(this).setBoolean("first_pref", false);
		getJumpIntent(HomeMain.class);
	}

	private void getJumpIntent(Class<?> cls) {
		Intent intent = new Intent(this, cls);
		this.startActivity(intent);
		this.finish();
	}
}