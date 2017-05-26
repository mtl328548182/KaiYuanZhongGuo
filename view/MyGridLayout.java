package com.jiyun.kaiyuanzhongguo.view;

import java.util.List;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import com.jiyun.kaiyuanzhongguo.R;

public class MyGridLayout extends GridLayout {
	// ����ק��View
	private View mDragedView;
	private String tvName;


	// ��XML���������ÿؼ���style���Ե�ʱ�����
	public MyGridLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	// ��XML���������ÿؼ���ʱ�����
	public MyGridLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	// �ڴ�������new�����ʱ�����
	public MyGridLayout(Context context) {
		this(context, null);
	}

	// ��ʼ����ǰGridLayout����Ŀ������ ��Ŀ����
	private void init() {
		setColumnCount(4);
		setLayoutTransition(new LayoutTransition());
	}

	// ������ṩ���������GridLayout��Ŀ�ķ���
	public void setItems(List<String> list) {
		for (String strItem : list) {
			addItem(strItem);
		}
	}

	private int mMargin = 5;
	private boolean mDragAble;

	// ��GridLayout���������Ŀ
	private void addItem(String strItem) {
		final TextView tv = new TextView(getContext());
		LayoutParams lp = new LayoutParams();
		lp.width = getResources().getDisplayMetrics().widthPixels / 4 - mMargin
				* 2;
		lp.height = LayoutParams.WRAP_CONTENT;
		lp.setMargins(mMargin, mMargin, mMargin, mMargin);

		tv.setLayoutParams(lp);
		tv.setGravity(Gravity.CENTER);
		tv.setPadding(mMargin, mMargin, mMargin, mMargin);
		tv.setText(strItem);
		tv.setBackgroundResource(R.drawable.tv_item_select);
		MyGridLayout.this.addView(tv);
		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mDragAble){

				}else {
					tvName = tv.getText().toString();
					MyGridLayout.this.removeView(tv);
				}
			}
		});
		// ������ק
		if (mDragAble) {
			tv.setOnLongClickListener(ocl);
			// ������ק
		} else {
			tv.setOnLongClickListener(null);
		}

	}

	@Override
	public void removeView(View view) {
		super.removeView(view);
	}

	// TextView(MyGridLayout����Ŀ)�ĳ����¼�
	private OnLongClickListener ocl = new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			mDragedView = v;
			v.startDrag(null, new DragShadowBuilder(v), null, 0);
			v.setEnabled(false);
			return false;
		}
	};

	// ������ṩ�Ƿ�����ק�ķ���
	public void setDragAble(boolean isDrage) {
		this.mDragAble = isDrage;
		if (mDragAble) {
			// ������ק�¼�
			this.setOnDragListener(odl);
		} else {
			// ��������ק�¼�
			this.setOnDragListener(null);
		}
	}

	// ��ק������
	private OnDragListener odl = new OnDragListener() {

		@Override
		public boolean onDrag(View arg0, DragEvent event) {

			switch (event.getAction()) {
			// ��ʼ��ק
			case DragEvent.ACTION_DRAG_STARTED:
				initRect();
				break;

			// ʵʱ������ק�¼�
			case DragEvent.ACTION_DRAG_LOCATION:
				int index = getIntouchIndex(event);
				if (index > -1 && mDragedView != null
						&& mDragedView != getChildAt(index)) {
					removeView(mDragedView);
					addView(mDragedView, index);
				}
				break;

			// ֹͣ��ק
			case DragEvent.ACTION_DRAG_ENDED:
				if (mDragedView != null) {
					mDragedView.setEnabled(true);
				}

				break;
			}
			return true;
		}

	};

	private Rect[] mRectArr;

	// �����е���Ŀ����װ�ɾ���Ȼ���������
	private void initRect() {
		int childViewCount = getChildCount();
		mRectArr = new Rect[childViewCount];
		for (int i = 0; i < childViewCount; i++) {
			View childView = getChildAt(i);
			Rect rect = new Rect(childView.getLeft(), childView.getTop(),
					childView.getRight(), childView.getBottom());
			mRectArr[i] = rect;
		}

	}

	// ʵʱ������ק�ĵ��Ƿ���뵽��ĳһ���ӿؼ���Χ��
	private int getIntouchIndex(DragEvent event) {
		for (int i = 0; i < mRectArr.length; i++) {
			if (mRectArr[i].contains((int) event.getX(), (int) event.getY())) {
				return i;
			}
		}
		return -1;
	}
}
