package com.lwj.telegram_intro;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {
  private ViewPager viewPager;
  private ImageView topImage1;
  private ImageView topImage2;
  private ViewGroup bottomPages;
  private int lastPage = 0;
  private int[] icons;
  private int[] titles;
  private int[] messages;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    viewPager = (ViewPager) findViewById(R.id.intro_view_pager);
    topImage1 = (ImageView) findViewById(R.id.icon_image1);
    topImage2 = (ImageView) findViewById(R.id.icon_image2);
    bottomPages = (ViewGroup) findViewById(R.id.bottom_pages);
    TextView startMessagingButton = (TextView) findViewById(R.id.start_messaging_button);
    startMessagingButton.setText("StartMessaging".toUpperCase());
    icons = new int[] {
        R.mipmap.intro1, R.mipmap.intro2, R.mipmap.intro3, R.mipmap.intro4, R.mipmap.intro5,
        R.mipmap.intro6, R.mipmap.intro7
    };
    titles = new int[] {
        R.string.Page1Title, R.string.Page2Title, R.string.Page3Title, R.string.Page4Title,
        R.string.Page5Title, R.string.Page6Title, R.string.Page7Title
    };
    messages = new int[] {
        R.string.Page1Message, R.string.Page2Message, R.string.Page3Message, R.string.Page4Message,
        R.string.Page5Message, R.string.Page6Message, R.string.Page7Message
    };

    topImage2.setVisibility(View.GONE);
    viewPager.setAdapter(new IntroAdapter());
    //设置 ViewPager 页面之间的间距
    viewPager.setPageMargin(0);
    //设置 ViewPager 预加载的数量
    viewPager.setOffscreenPageLimit(1);
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override public void onPageSelected(int position) {

      }

      @Override public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE || state == ViewPager.SCROLL_STATE_SETTLING) {
          if (lastPage != viewPager.getCurrentItem()) {
            lastPage = viewPager.getCurrentItem();

            final ImageView fadeoutImage;
            final ImageView fadeinImage;
            if (topImage1.getVisibility() == View.VISIBLE) {
              fadeoutImage = topImage1;
              fadeinImage = topImage2;
            } else {
              fadeoutImage = topImage2;
              fadeinImage = topImage1;
            }

            fadeinImage.bringToFront();
            fadeinImage.setImageResource(icons[lastPage]);
            fadeinImage.clearAnimation();
            fadeoutImage.clearAnimation();

            Animation outAnimation =
                AnimationUtils.loadAnimation(IntroActivity.this, R.anim.icon_anim_fade_out);
            outAnimation.setAnimationListener(new Animation.AnimationListener() {
              @Override public void onAnimationStart(Animation animation) {
                fadeoutImage.setVisibility(View.GONE);
              }

              @Override public void onAnimationEnd(Animation animation) {

              }

              @Override public void onAnimationRepeat(Animation animation) {

              }
            });

            Animation inAnimation =
                AnimationUtils.loadAnimation(IntroActivity.this, R.anim.icon_anim_fade_in);
            inAnimation.setAnimationListener(new Animation.AnimationListener() {
              @Override public void onAnimationStart(Animation animation) {
                fadeinImage.setVisibility(View.VISIBLE);
              }

              @Override public void onAnimationEnd(Animation animation) {

              }

              @Override public void onAnimationRepeat(Animation animation) {

              }
            });

            fadeinImage.startAnimation(inAnimation);
            fadeoutImage.startAnimation(outAnimation);
          }
        }
      }
    });
  }

  public class IntroAdapter extends PagerAdapter {

    /**
     * Return the number of views available.
     */
    @Override public int getCount() {
      return 7;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
      View view = View.inflate(container.getContext(), R.layout.intro_view_layout, null);
      TextView headerTextView = (TextView) view.findViewById(R.id.header_text);
      TextView messageTextView = (TextView) view.findViewById(R.id.message_text);
      container.addView(view, 0);
      headerTextView.setText(getString(titles[position]));
      messageTextView.setText(getString(messages[position]));
      return view;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View) object);
    }

    @Override public void setPrimaryItem(ViewGroup container, int position, Object object) {
      super.setPrimaryItem(container, position, object);
      int count = bottomPages.getChildCount();
      for (int a = 0; a < count; a++) {
        View child = bottomPages.getChildAt(a);
        if (a == position) {
          child.setBackgroundColor(0xff2ca5e0);
        } else {
          child.setBackgroundColor(0xffbbbbbb);
        }
      }
    }

    @Override public boolean isViewFromObject(View view, Object object) {
      return view.equals(object);
    }

    @Override public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override public Parcelable saveState() {
      return null;
    }

    @Override public void unregisterDataSetObserver(DataSetObserver observer) {
      if (observer != null) {
        super.unregisterDataSetObserver(observer);
      }
    }
  }
}
