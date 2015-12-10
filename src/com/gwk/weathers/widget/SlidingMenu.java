package com.gwk.weathers.widget;

import com.gwk.weathers.app.R;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {
	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;

	private int mScreenWidth;// 屏幕宽 度
	// 单位是dp
	private int mMenuRightPad = 100;// menu与右侧的距离
	// --只调用一次
	private boolean once = false;
	// menu的宽度
	private int mMenuWidth;
	private boolean isOpen;

	/**
	 * 一个参数的构造方法
	 * 
	 * @param context
	 */
	public SlidingMenu(Context context) {
		this(context, null);

	}

	/**
	 * 未使用自定义属性时，调用此构造方法
	 * 
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs) {

		this(context, attrs, 0);

	}

	/**
	 * 当使用了自定义属性时，会调用此构造方法
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// 获取我们定义的属性
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SlidingMenu);
		// 得到R.styleable.SlidingMenu中资源的个数
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			// 获得a中属性
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_rightPadding:
				mMenuRightPad = a.getDimensionPixelSize(attr, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
								context.getResources().getDisplayMetrics()));

				break;
			default:
				break;

			}
		}

		// 释放资源
		a.recycle();

		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		// outMetrics可以获取屏幕参数
		DisplayMetrics outMetrics = new DisplayMetrics();
		// 通过这个方法可以给outMetrics赋屏幕有关的值
		wm.getDefaultDisplay().getMetrics(outMetrics);
		// 得到屏幕宽度
		mScreenWidth = outMetrics.widthPixels;

		// 将50dp转换为像素值px,并直接赋值给menuRightPad
		// mMenuRightPad = (int) TypedValue.applyDimension(
		// TypedValue.COMPLEX_UNIT_DIP, 100, context.getResources()
		// .getDisplayMetrics());

	}

	/**
	 * 设置子View的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (!once) {
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			// 设置menu的宽度（屏幕宽度-menu与右侧的距离）
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
					- mMenuRightPad;
			mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 给layout设置位置 通过设置偏移量，将原本在开头的menu放到左侧隐藏去
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		this.scrollTo(mMenuWidth, 0);

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			// 隐藏的区域
			int scrollX = getScrollX();
			// 如果隐藏在左边部分大于菜单的1/2，则将菜单移动到左边去
			if (scrollX > mMenuWidth / 2) {

				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			} else {
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 打开菜单
	 */
	public void openMenu() {
		if (isOpen) {
			return;
		}

		this.smoothScrollTo(0, 0);
		isOpen = true;

	}

	// 关闭菜单
	public void closeMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false;

	}

	/**
	 * 切换菜单
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();

		}

	}

	/**
	 * 当滚动发生时
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		// 调用属性动画，设置TranslationX
		// l是当前horizonScrollView隐藏的X值
		ViewHelper.setTranslationX(mMenu, l);

	}

}



