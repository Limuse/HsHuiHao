package com.huihao.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.huihao.R;
import com.huihao.activity.Product_details;

import java.util.ArrayList;
import java.util.List;


public class TagGroup extends ViewGroup {
    private final int default_bright_color = Color.rgb(0x49, 0xC1, 0x20);
    private final int default_dim_color = Color.rgb(0xAA, 0xAA, 0xAA);
    private final float default_border_stroke_width;
    private final float default_text_size;
    private final float default_horizontal_spacing;
    private final float default_vertical_spacing;
    private final float default_horizontal_padding;
    private final float default_vertical_padding;

    private boolean isAppendMode;

    private int mBrightColor;

    private int mDimColor;

    private float mBorderStrokeWidth;

    private float mTextSize;

    private int mHorizontalSpacing;

    private int mVerticalSpacing;

    private int mHorizontalPadding;

    private int mVerticalPadding;

    private OnTagChangeListener mOnTagChangeListener;

    public TagGroup(Context context) {
        this(context, null);

    }

    public TagGroup(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.tagGroupStyle);
    }

    public TagGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        default_border_stroke_width = dp2px(0.5f);
        default_text_size = sp2px(13.0f);
        default_horizontal_spacing = dp2px(8.0f);
        default_vertical_spacing = dp2px(4.0f);
        default_horizontal_padding = dp2px(12.0f);
        default_vertical_padding = dp2px(3.0f);
        final TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.TagGroup, defStyleAttr, R.style.TagGroup);
        try {
            isAppendMode = a.getBoolean(R.styleable.TagGroup_isAppendMode,
                    false);
            mBrightColor = a.getColor(R.styleable.TagGroup_brightColor,
                    default_bright_color);
            mDimColor = a.getColor(R.styleable.TagGroup_dimColor,
                    default_dim_color);
            mBorderStrokeWidth = a.getDimension(
                    R.styleable.TagGroup_borderStrokeWidth,
                    default_border_stroke_width);
            mTextSize = a.getDimension(R.styleable.TagGroup_textSize,
                    default_text_size);
            mHorizontalSpacing = (int) a.getDimension(
                    R.styleable.TagGroup_horizontalSpacing,
                    default_horizontal_spacing);
            mVerticalSpacing = (int) a.getDimension(
                    R.styleable.TagGroup_verticalSpacing,
                    default_vertical_spacing);
            mHorizontalPadding = (int) a.getDimension(
                    R.styleable.TagGroup_horizontalPadding,
                    default_horizontal_padding);
            mVerticalPadding = (int) a.getDimension(
                    R.styleable.TagGroup_verticalPadding,
                    default_vertical_padding);
        } finally {
            a.recycle();
        }

        setUpTagGroup();
    }

    protected void setUpTagGroup() {
        if (isAppendMode) {
            appendInputTag();

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitTag();
                }
            });
        }
    }

    public void submitTag() {
        final TagView inputTag = getInputTagView();
        if (inputTag != null && inputTag.isInputAvailable()) {
            inputTag.endInput();

            if (mOnTagChangeListener != null) {
                mOnTagChangeListener.onAppend(TagGroup.this, inputTag.getText()
                        .toString());
            }
            appendInputTag();
        }
    }

    public int getBrightColor() {
        return mBrightColor;
    }

    public void setBrightColor(int brightColor) {
        mBrightColor = brightColor;
        invalidateAllTagsPaint();
        invalidate();
    }

    public int getDimColor() {
        return mDimColor;
    }

    public void setDimColor(int dimColor) {
        mDimColor = dimColor;
        invalidateAllTagsPaint();
        invalidate();
    }

    public float getBorderStrokeWidth() {
        return mBorderStrokeWidth;
    }

    public void setBorderStrokeWidth(float borderStrokeWidth) {
        mBorderStrokeWidth = borderStrokeWidth;
        invalidateAllTagsPaint();
        requestLayout();
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float textSize) {
        mTextSize = textSize;
        invalidateAllTagsPaint();
        requestLayout();
    }

    public int getHorizontalSpacing() {
        return mHorizontalSpacing;
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        mHorizontalSpacing = horizontalSpacing;
        requestLayout();
    }

    public int getVerticalSpacing() {
        return mVerticalSpacing;
    }

    public void setVerticalSpacing(int verticalSpacing) {
        mVerticalSpacing = verticalSpacing;
        requestLayout();
    }

    public int getHorizontalPadding() {
        return mHorizontalPadding;
    }

    public void setHorizontalPadding(int horizontalPadding) {
        mHorizontalPadding = horizontalPadding;
        requestLayout();
    }

    public int getVerticalPadding() {
        return mVerticalPadding;
    }

    public void setVerticalPadding(int verticalPadding) {
        mVerticalPadding = verticalPadding;
        requestLayout();
    }

    protected void invalidateAllTagsPaint() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            TagView tagView = getTagViewAt(i);
            tagView.invalidatePaint();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int row = 0;
        int rowWidth = 0;
        int rowMaxHeight = 0;

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();

            if (child.getVisibility() != GONE) {
                rowWidth += childWidth;
                if (rowWidth > widthSize) {
                    rowWidth = childWidth;
                    height += rowMaxHeight + mVerticalSpacing;
                    rowMaxHeight = childHeight;
                    row++;
                } else {
                    rowMaxHeight = Math.max(rowMaxHeight, childHeight);
                }
                rowWidth += mHorizontalSpacing;
            }
        }
        height += rowMaxHeight;

        height += getPaddingTop() + getPaddingBottom();

        if (row == 0) {
            width = rowWidth;
            width += getPaddingLeft() + getPaddingRight();
        } else {
            width = widthSize;
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize
                : width, heightMode == MeasureSpec.EXACTLY ? heightSize
                : height);
    }

    @SuppressWarnings("unused")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentLeft = getPaddingLeft();
        final int parentRight = r - l - getPaddingRight();
        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();

        int childLeft = parentLeft;
        int childTop = parentTop;

        int rowMaxHeight = 0;

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            final int width = child.getMeasuredWidth();
            final int height = child.getMeasuredHeight();

            if (child.getVisibility() != GONE) {
                if (childLeft + width > parentRight) {
                    childLeft = parentLeft;
                    childTop += rowMaxHeight + mVerticalSpacing;
                    rowMaxHeight = height;
                } else {
                    rowMaxHeight = Math.max(rowMaxHeight, height);
                }
                child.layout(childLeft, childTop, childLeft + width, childTop
                        + height);

                childLeft += width + mHorizontalSpacing;
            }
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.tags = getTags();
        ss.checkedPosition = getCheckedTagIndex();
        if (getInputTagView() != null) {
            ss.input = getInputTagView().getText().toString();
        }
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        setTags(ss.tags);
        TagView checkedTagView = getTagViewAt(ss.checkedPosition);
        if (checkedTagView != null) {
            checkedTagView.setChecked(true);
        }
        if (getInputTagView() != null) {
            getInputTagView().setText(ss.input);
        }
    }

    protected TagView getInputTagView() {
        if (isAppendMode) {
            final int inputTagIndex = getChildCount() - 1;
            final TagView inputTag = getTagViewAt(inputTagIndex);
            if (inputTag != null && inputTag.mState == TagView.STATE_INPUT) {
                return inputTag;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public String getInputTag() {
        final TagView inputTagView = getInputTagView();
        if (inputTagView != null) {
            return inputTagView.getText().toString();
        }
        return null;
    }

    protected TagView getLastNormalTagView() {
        final int lastNormalTagIndex = isAppendMode ? getChildCount() - 2
                : getChildCount() - 1;
        TagView lastNormalTagView = getTagViewAt(lastNormalTagIndex);
        return lastNormalTagView;
    }

    public String[] getTags() {
        final int count = getChildCount();
        final List<String> tagList = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            final TagView tagView = getTagViewAt(i);
            if (tagView.mState == TagView.STATE_NORMAL) {
                tagList.add(tagView.getText().toString());
            }
        }

        return tagList.toArray(new String[]{});
    }

    public String[] getCheckTags() {
        final int count = getChildCount();
        final List<String> tagList = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            final TagView tagView = getTagViewAt(i);
            if (tagView.isChecked) {
                tagList.add(tagView.getText().toString());
            }
        }

        return tagList.toArray(new String[]{});
    }

    public TagView getTagViewAt(int index) {
        return (TagView) getChildAt(index);
    }

    protected TagView getCheckedTagView() {
        final int checkedTagIndex = getCheckedTagIndex();
        if (checkedTagIndex != -1) { // exists
            return getTagViewAt(checkedTagIndex);
        }
        return null;
    }

    protected int getCheckedTagIndex() {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final TagView tagView = getTagViewAt(i);
            if (tagView.isChecked) {
                return i;
            }
        }
        return -1;
    }

    protected Boolean isExceed() {
        int check = 0;
        for (int i = 0; i < getChildCount(); i++) {
            final TagView tagView = getTagViewAt(i);
            if (tagView.isChecked) {
                check++;
            }
        }
        if (check > 2) {
            return false;
        } else {
            return true;
        }
    }

    public void setOnTagChangeListener(OnTagChangeListener l) {
        mOnTagChangeListener = l;
    }

    protected void appendInputTag() {
        appendInputTag(null);
    }

    protected void appendInputTag(String tag) {
        TagView lastTag = getInputTagView();
        if (lastTag != null) {
            throw new IllegalStateException(
                    "Already has a INPUT state tag in group. "
                            + "You must call endInput() before you append new one.");
        }

        TagView tagView = new TagView(getContext(), TagView.STATE_INPUT, tag);
        tagView.setOnClickListener(new OnTagClickListener());
        addView(tagView);
    }

    public void setTags(List<String> tagList) {
        setTags(tagList.toArray(new String[]{}));
    }

    public void setTags(String... tags) {
        removeAllViews();
        for (final String tag : tags) {
            appendTag(tag);
        }
    }

    protected void appendTag(CharSequence tag) {
        if (isAppendMode) {
            final int appendIndex = getChildCount();
            final TagView tagView = new TagView(getContext(),
                    TagView.STATE_NORMAL, tag);
            tagView.setOnClickListener(new OnTagClickListener());
            addView(tagView, appendIndex);
        } else {
            final TagView tagView = new TagView(getContext(),
                    TagView.STATE_NORMAL, tag);
            addView(tagView);
        }
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    public float sp2px(float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public interface OnTagChangeListener {
        void onAppend(TagGroup tagGroup, String tag);

        void onDelete(TagGroup tagGroup, String tag);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    static class SavedState extends BaseSavedState {
        int tagCount;
        String[] tags;
        int checkedPosition;
        String input;

        public SavedState(Parcel source) {
            super(source);
            tagCount = source.readInt();
            tags = new String[tagCount];
            source.readStringArray(tags);
            checkedPosition = source.readInt();
            input = source.readString();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            tagCount = tags.length;
            dest.writeInt(tagCount);
            dest.writeStringArray(tags);
            dest.writeInt(checkedPosition);
            dest.writeString(input);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    class OnTagClickListener implements OnClickListener {
        public void onClick(View v) {
            TagView clickedTagView = (TagView) v;
            if (clickedTagView.mState == TagView.STATE_INPUT) {
            } else {
                if (clickedTagView.isChecked) {
                } else {
                    for (int i = 0; i < getChildCount(); i++) {
                        TagView tagView = getTagViewAt(i);
                        if (tagView.isChecked) {
                            tagView.setChecked(false);
                        }
                    }
                    Product_details.context.setTagInfo(indexOfChild(clickedTagView));
                    clickedTagView.setChecked(true);
                }
            }
        }
    }

    public class TagView extends Button {
        public static final int STATE_NORMAL = 1;
        public static final int STATE_INPUT = 2;
        private int mState;

        private boolean isChecked = false;

        private Paint mPaint;

        private Paint mMarkPaint;

        private RectF mLeftCornerRectF;

        private RectF mRightCornerRectF;

        private RectF mHorizontalBlankFillRectF;

        private RectF mVerticalBlankFillRectF;

        private RectF mCheckedMarkDrawBound;

        private int mCheckedMarkOffset;

        private Path mBorderPath;

        private PathEffect mPathEffect;

        public TagView(final Context context, int state, CharSequence text) {
            super(context);
            this.setBackgroundColor(Color.parseColor("#00000000"));
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mMarkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mMarkPaint.setColor(Color.WHITE);
            mMarkPaint.setStrokeWidth(4);

            mLeftCornerRectF = new RectF();
            mRightCornerRectF = new RectF();

            mHorizontalBlankFillRectF = new RectF();
            mVerticalBlankFillRectF = new RectF();

            mCheckedMarkDrawBound = new RectF();
            mCheckedMarkOffset = 3;

            mBorderPath = new Path();
            mPathEffect = new DashPathEffect(new float[]{10, 5}, 0);

            setPadding(mHorizontalPadding, mVerticalPadding,
                    mHorizontalPadding, mVerticalPadding);
            setLayoutParams(new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    (int) dp2px(32f)));

            setGravity(Gravity.CENTER);
            setText(text);
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);


            mState = state;

            setClickable(isAppendMode);
            invalidatePaint();
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
            setPadding(mHorizontalPadding, mVerticalPadding,
                    mHorizontalPadding, mVerticalPadding);
            invalidatePaint();
        }

        public void endInput() {
            setFocusable(false);
            setFocusableInTouchMode(false);
            setHint(null);
            setMovementMethod(null);

            mState = STATE_NORMAL;
            invalidatePaint();
            requestLayout();
        }

        @Override
        protected boolean getDefaultEditable() {
            return true;
        }

        public boolean isInputAvailable() {
            return getText() != null && getText().length() > 0;
        }

        private void invalidatePaint() {
            if (mState == STATE_NORMAL) {
                if (isChecked) {
                    mPaint.setStyle(Paint.Style.FILL);
                    mPaint.setColor(mBrightColor);
                    mPaint.setPathEffect(null);
                    setTextColor(Color.WHITE);
                } else {
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeWidth(mBorderStrokeWidth);
                    mPaint.setColor(Color.parseColor("#CCCCCC"));
                    mPaint.setPathEffect(null);
                    setTextColor(Color.parseColor("#333333"));
                }

            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (isChecked) {
                canvas.drawArc(mLeftCornerRectF, -180, 90, true, mPaint);
                canvas.drawArc(mLeftCornerRectF, -270, 90, true, mPaint);
                canvas.drawArc(mRightCornerRectF, -90, 90, true, mPaint);
                canvas.drawArc(mRightCornerRectF, 0, 90, true, mPaint);
                canvas.drawRect(mHorizontalBlankFillRectF, mPaint);
                canvas.drawRect(mVerticalBlankFillRectF, mPaint);
                canvas.save();
                canvas.restore();
            } else {
                canvas.drawPath(mBorderPath, mPaint);
            }
            super.onDraw(canvas);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            int left = (int) mBorderStrokeWidth;
            int top = (int) mBorderStrokeWidth;
            int right = (int) (left + w - mBorderStrokeWidth * 2);
            int bottom = (int) (top + h - mBorderStrokeWidth * 2);

            int d = bottom - top;

            mLeftCornerRectF.set(left, top, left + d, top + d);
            mRightCornerRectF.set(right - d, top, right, top + d);

            mBorderPath.reset();
            mBorderPath.addArc(mLeftCornerRectF, -180, 90);
            mBorderPath.addArc(mLeftCornerRectF, -270, 90);
            mBorderPath.addArc(mRightCornerRectF, -90, 90);
            mBorderPath.addArc(mRightCornerRectF, 0, 90);

            int l = (int) (d / 2.0f);
            mBorderPath.moveTo(left + l, top);
            mBorderPath.lineTo(right - l, top);

            mBorderPath.moveTo(left + l, bottom);
            mBorderPath.lineTo(right - l, bottom);

            mBorderPath.moveTo(left, top + l);
            mBorderPath.lineTo(left, bottom - l);

            mBorderPath.moveTo(right, top + l);
            mBorderPath.lineTo(right, bottom - l);

            mHorizontalBlankFillRectF.set(left, top + l, right, bottom - l);
            mVerticalBlankFillRectF.set(left + l, top, right - l, bottom);

            int m = (int) (h / 2.5f);
            h = bottom - top;
            mCheckedMarkDrawBound.set(right - m - mHorizontalPadding
                    + mCheckedMarkOffset, top + h / 2 - m / 2, right
                    - mHorizontalPadding + mCheckedMarkOffset, bottom - h / 2
                    + m / 2);
            if (isChecked) {
                setPadding(mHorizontalPadding, mVerticalPadding,
                        mHorizontalPadding, mVerticalPadding);
            }
        }
    }
}