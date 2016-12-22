package cn.gavinliu.android.lib.scale;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import cn.gavinliu.android.lib.scale.helper.ScaleLayoutHelper;

/**
 * Created by GavinLiu on 2015-08-10
 */
public class ScaleRelativeLayout extends RelativeLayout {

    private ScaleLayoutHelper mHelper;

    public ScaleRelativeLayout(Context context) {
        this(context, null);
    }

    public ScaleRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            mHelper = ScaleLayoutHelper.create(this, attrs);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(this.getContext(), attrs, isInEditMode());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            this.mHelper.adjustHost(widthMeasureSpec, heightMeasureSpec);
            this.mHelper.adjustChildren(widthMeasureSpec, heightMeasureSpec);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (this.mHelper.handleMeasuredStateTooSmall()) {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        this.mHelper.restoreOriginalParams();
    }

    public static class LayoutParams extends android.widget.RelativeLayout.LayoutParams implements ScaleLayoutHelper.ScaleLayoutParams {
        private ScaleLayoutHelper.ScaleLayoutInfo mPercentLayoutInfo;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.mPercentLayoutInfo = ScaleLayoutHelper.getScaleLayoutInfo(c, attrs);
        }

        private LayoutParams(Context c, AttributeSet attrs, boolean isInEditMode) {
            super(c, attrs);
            if (!isInEditMode) {
                this.mPercentLayoutInfo = ScaleLayoutHelper.getScaleLayoutInfo(c, attrs);
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        @Override
        public ScaleLayoutHelper.ScaleLayoutInfo getScaleLayoutInfo() {
            return this.mPercentLayoutInfo;
        }

        @Override
        protected void setBaseAttributes(TypedArray a, int widthAttr, int heightAttr) {
            ScaleLayoutHelper.fetchWidthAndHeight(this, a, widthAttr, heightAttr);
        }

        @Override
        public void setMargins(int left, int top, int right, int bottom) {
            super.setMargins(left, top, right, bottom);
            if (mPercentLayoutInfo != null) {
                mPercentLayoutInfo.setMargins(left, top, right, bottom);
            }
        }
    }
}
